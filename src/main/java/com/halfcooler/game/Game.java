package com.halfcooler.game;

import com.halfcooler.Program;
import com.halfcooler.flying.Flying;
import com.halfcooler.utils.MouseController;
import com.halfcooler.flying.bullet.*;
import com.halfcooler.flying.prop.*;
import com.halfcooler.flying.warplane.*;
import com.halfcooler.music.MusicThread;
import com.halfcooler.utils.ImageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game extends JPanel
{
	// public final RecordImplement Recorder;
	private int backgroundTop = 0;
	private final ScheduledExecutorService scheduler;

	private final int timeInterval = 16;
	private final WarplaneHero warplaneHero;
	private final List<Warplane> allEnemies;
	private final List<Bullet> allEnemyBullets;
	private final List<Bullet> heroBullets;
	private final List<Prop> props;

	private final int maxEnemies = 5;
	private final int cycleDuration;
	private int score = 0, time = 0;
	private int cycleTime = 0;

	private boolean gameOver = false;

	public Game(int difficulty)
	{
		switch (difficulty)
		{
			case 0 -> cycleDuration = 800;
			case 1 -> cycleDuration = 600;
			case 2 -> cycleDuration = 30;
			default -> throw new IllegalArgumentException("Invalid difficulty");
		}

		warplaneHero = new WarplaneHero(Program.WIDTH / 2, Program.HEIGHT - ImageManager.HeroImg.getHeight(), 0, 0, 100);
		System.out.println("Hero plane created.");
		allEnemies = new LinkedList<>();
		heroBullets = new LinkedList<>();
		allEnemyBullets = new LinkedList<>();
		props = new LinkedList<>();

		// this.scheduler = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());
		this.scheduler = Executors.newSingleThreadScheduledExecutor(runnable ->
		{
			System.out.println("Thread started.");
			Thread t = new Thread(runnable);
			t.setDaemon(true);
			return t;
		});

		MouseController.SetControl(this, warplaneHero);
	}

	public static Game StartGame(int difficulty, boolean isMusic)
	{
		MusicThread.musicOn(isMusic);
		return new Game(difficulty);
	}

	private boolean timeCycled()
	{
		this.cycleTime += this.timeInterval;
		if (this.cycleTime > this.cycleDuration)
		{
			this.cycleTime %= this.cycleDuration;
			return true;
		}
		return false;
	}

	// HAS BUG
	private void crashEvent()
	{
		// 子弹打自己
		for (Bullet bullet : allEnemyBullets)
		{
			if (!bullet.getFlying())
				continue;

			if (!warplaneHero.getFlying())
				return;

			if (warplaneHero.isCrash(bullet))
			{
				warplaneHero.ChangeHealth(-bullet.getPower());
				bullet.setVanish();
			}
		}

		// 子弹打敌机
		for (Bullet bullet : heroBullets)
		{
			if (!bullet.getFlying())
				continue;

			for (Warplane enemy : allEnemies)
			{
				if (!enemy.getFlying())
					continue;

				if (enemy.isCrash(bullet))
				{
					enemy.ChangeHealth(-bullet.getPower());
					bullet.setVanish();

					if (!enemy.getFlying())
					{
						score += enemy.getScore();
						// 敌机死了, 有概率掉落道具
					}
				}

				// 敌机撞自己, GAME OVER
				if (!warplaneHero.getFlying())
					return;

				if (warplaneHero.isCrash(enemy) || enemy.isCrash(warplaneHero))
				{
					warplaneHero.setVanish();
					enemy.setVanish();
				}
			}

			// 我吃道具
			for (Prop prop : props)
			{
				if (!prop.getFlying())
					continue;

				if (warplaneHero.isCrash(prop))
				{
					props.remove(prop);
					prop.takeEffect(warplaneHero, allEnemies, allEnemyBullets);
				}
			}
		}

	}

	/// 游戏的主循环.
	public void Loop()
	{
		Runnable gameTask = () ->
		{
			System.out.printf("Hero Position: (%d, %d), 1st Enemy Position: (%d, %d), isCrash: %s%n",
				this.warplaneHero.getX(), this.warplaneHero.getY(),
				this.allEnemies.isEmpty() ? -1 : this.allEnemies.getFirst().getX(),
				this.allEnemies.isEmpty() ? -1 : this.allEnemies.getFirst().getY(),
				this.allEnemies.isEmpty() ? -1 : this.warplaneHero.isCrash(this.allEnemies.getFirst()) ? 1 : 0
			);
			this.time += this.timeInterval;

			if (timeCycled())
			{
				// System.out.println("Cycle!");
				// 产生敌机
				if (this.allEnemies.size() < this.maxEnemies)
				{
					this.allEnemies.add(new WarplaneEnemy(
						(int) (Math.random() * (Program.WIDTH - ImageManager.EnemyImg.getWidth())),
						(int) (Math.random() * Program.HEIGHT / 20), 0, 5, 30));

				}
				// 射击
				for (Warplane enemy : this.allEnemies)
					this.allEnemyBullets.addAll(enemy.getShots());

				this.heroBullets.addAll(this.warplaneHero.getShots());
			}

			// 子弹移动
			for (Bullet bullet: this.heroBullets)
				bullet.goForward();
			for (Bullet bullet: this.allEnemyBullets)
				bullet.goForward();
				// 飞机移动
			for (Warplane enemy : this.allEnemies)
				enemy.goForward();

			// 撞击检测
			crashEvent();

			// 后处理
			this.allEnemyBullets.removeIf(Flying::getNotFlying);
			this.heroBullets.removeIf(Flying::getNotFlying);
			this.allEnemies.removeIf(Flying::getNotFlying);
			this.props.removeIf(Flying::getNotFlying);

			repaint();

			if (!this.warplaneHero.getFlying() || this.warplaneHero.getHealth() <= 0)
			{
				this.scheduler.shutdown();
				gameOver = true;
				System.out.println("Game Over");
			}
		};
		this.scheduler.scheduleWithFixedDelay(gameTask, this.timeInterval, this.timeInterval, TimeUnit.MILLISECONDS);
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

		g.drawImage(ImageManager.BackgroundImg, 0, this.backgroundTop - Program.HEIGHT, null);
		g.drawImage(ImageManager.BackgroundImg, 0, this.backgroundTop, null);
		this.backgroundTop = this.backgroundTop == Program.HEIGHT ? 0 : this.backgroundTop + 1;

		// 逻辑: 先画子弹和道具, 后画飞机
		PaintImageWithPositionRevised(g, this.allEnemyBullets);
		PaintImageWithPositionRevised(g, this.heroBullets);
		PaintImageWithPositionRevised(g, this.props);

		PaintImageWithPositionRevised(g, this.allEnemies);
		PaintImageWithPositionRevised(g, List.of(this.warplaneHero));

		PaintScoreAndHealth(g);
	}

	private void PaintImageWithPositionRevised(Graphics g, List<? extends Flying> flyingList)
	{
		if (flyingList.isEmpty())
			return;

		for (Flying flying : flyingList)
		{
			BufferedImage image = flying.getImage();
			assert image != null : flyingList.getClass().getName() + " image is null.";
			g.drawImage(image, flying.getX() - image.getWidth() / 2, flying.getY() - image.getHeight() / 2, null);
		}
	}

	private void PaintScoreAndHealth(Graphics g)
	{
		int x = 10, y = 25;
		g.setColor(Color.WHITE);
		g.setFont(new Font("Segoe UI", Font.ITALIC, 20));
		g.drawString("Score: " + this.score, x, y);
		g.drawString("Health: " + this.warplaneHero.getHealth(), x, y + 20);
	}

}
