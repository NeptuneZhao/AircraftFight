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

	private final int timeInterval = 15;
	private final WarplaneHero warplaneHero;
	private final List<Warplane> allEnemies;
	private final List<Bullet> allEnemyBullets;
	private final List<Bullet> heroBullets;
	private final List<Prop> props;

	private final int maxEnemies = 8;
	private final int cycleDuration;
	private int score = 0, time = 0;
	private int cycleTime = 0;

	private boolean gameOver = false;

	public Game(int difficulty)
	{
		// final
		switch (difficulty)
		{
			case 0 -> cycleDuration = 600;
			case 1 -> cycleDuration = 300;
			case 2 -> cycleDuration = 100;
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

	public static Game StartGame(int difficulty, boolean musicOn)
	{
		MusicThread.IsMusicOn = musicOn;
		MusicThread.MusicOn(MusicThread.BackgroundMusicInstance);
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

	/// 碰撞检测 <br>
	/// 产生道具
	private void crashEvent()
	{
		// 子弹打自己
		for (Bullet bullet : allEnemyBullets)
		{
			if (bullet.GetNotFlying())
				continue;

			if (warplaneHero.GetNotFlying())
				return;

			if (warplaneHero.IsCrash(bullet))
			{
				warplaneHero.ChangeHealth(-bullet.getPower());
				bullet.SetVanish();
			}
		}

		// 子弹打敌机
		for (Bullet bullet : heroBullets)
		{
			if (bullet.GetNotFlying())
				continue;

			for (Warplane enemy : allEnemies)
			{
				if (enemy.GetNotFlying())
					continue;

				if (enemy.IsCrash(bullet))
				{
					enemy.ChangeHealth(-bullet.getPower());
					bullet.SetVanish();

					if (enemy.GetNotFlying())
					{
						score += enemy.getScore();
						// 敌机死了, 有概率掉落道具
						Prop prop = Prop.GenerateProp(enemy);
						if (prop != null)
							props.add(prop);
					}
				}

				// 敌机撞自己, GAME OVER
				if (warplaneHero.GetNotFlying())
					return;

				if (warplaneHero.IsCrash(enemy) || enemy.IsCrash(warplaneHero))
				{
					warplaneHero.SetVanish();
					enemy.SetVanish();
				}
			}

			// 我吃撞到的道具
			for (Prop prop : props)
			{
				if (prop.GetNotFlying())
					continue;

				if (warplaneHero.IsCrash(prop))
				{
					prop.TakeEffect(warplaneHero, allEnemies, allEnemyBullets);
					prop.SetVanish();
				}
			}
		}

	}

	/// 游戏的主循环
	public void Loop()
	{
		Runnable gameTask = () ->
		{
			/*  插桩测试
				System.out.printf("Hero Position: (%d, %d), 1st Enemy Position: (%d, %d), isCrash: %s%n",
			 	this.warplaneHero.getX(), this.warplaneHero.getY(),
				this.allEnemies.isEmpty() ? -1 : this.allEnemies.getFirst().getX(),
				this.allEnemies.isEmpty() ? -1 : this.allEnemies.getFirst().getY(),
				this.allEnemies.isEmpty() ? -1 : this.warplaneHero.isCrash(this.allEnemies.getFirst()) ? 1 : 0);
			*/

			this.time += this.timeInterval;

			if (timeCycled())
			{
				// 产生敌机
				if (this.allEnemies.size() < this.maxEnemies)
				{
					this.allEnemies.add(new WarplaneEnemy(
						(int) (Math.random() * (Program.WIDTH - ImageManager.EnemyImg.getWidth())), // x
						(int) (Math.random() * Program.HEIGHT / 20), // y
						0, // speedX
						Math.random() < 0.01 ? 50 : 5, // speedY
						30)); // health
				}
				// 射击
				for (Warplane enemy : this.allEnemies)
					this.allEnemyBullets.addAll(enemy.GetShots());

				this.heroBullets.addAll(this.warplaneHero.GetShots());
			}

			// 子弹移动
			for (Bullet bullet: this.heroBullets)
				bullet.GoForward();
			for (Bullet bullet: this.allEnemyBullets)
				bullet.GoForward();

			// 敌机移动
			for (Warplane enemy : this.allEnemies)
				enemy.GoForward();

			// 道具移动
			for (Prop prop : this.props)
				prop.GoForward();

			// 撞击检测
			crashEvent();

			// 后处理
			this.allEnemyBullets.removeIf(Flying::GetNotFlying);
			this.heroBullets.removeIf(Flying::GetNotFlying);
			this.allEnemies.removeIf(Flying::GetNotFlying);
			this.props.removeIf(Flying::GetNotFlying);

			repaint();

			if (this.warplaneHero.GetNotFlying() || this.warplaneHero.getHealth() <= 0)
			{
				this.scheduler.shutdown();
				gameOver = true;
				System.out.println("Game Over");

				MusicThread.MusicOff(MusicThread.BackgroundMusicInstance);
				MusicThread.MusicOn(MusicThread.GameOverMusicInstance);
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
			BufferedImage image = flying.GetImage();
			assert image != null : flyingList.getClass().getName() + " image is null.";
			g.drawImage(image, flying.GetX() - image.getWidth() / 2, flying.GetY() - image.getHeight() / 2, null);
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
