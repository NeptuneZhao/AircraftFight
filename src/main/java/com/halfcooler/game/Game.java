package com.halfcooler.game;

import com.halfcooler.Program;
import com.halfcooler.flying.Flying;
import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.prop.Prop;
import com.halfcooler.flying.warplane.Warplane;
import com.halfcooler.flying.warplane.WarplaneHero;
import com.halfcooler.music.MusicPlayer;
import com.halfcooler.utils.ImageManager;
import com.halfcooler.utils.MouseController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game extends JPanel
{
	// public final RecordImplement Recorder;
	private int backgroundTop = 0;
	private final ScheduledExecutorService gameLoopScheduler, renderScheduler;

	private final int timeInterval;
	private final WarplaneHero warplaneHero = WarplaneHero.Instance;
	private final List<Warplane> allEnemies;
	private final List<Bullet> allEnemyBullets;
	private final List<Bullet> heroBullets;
	private final List<Prop> props;

	private final int maxEnemies = 5;
	private final int cycleDuration;
	private int score = 0, time = 0;
	private int cycleTime = 0;

	private boolean gameOver = false;

	public Game(int difficulty, int fps)
	{
		// final
		switch (difficulty)
		{
			case 0 -> cycleDuration = 750;
			case 1 -> cycleDuration = 600;
			case 2 -> cycleDuration = 300;
			default -> throw new IllegalArgumentException("Invalid difficulty");
		}

		allEnemies = new LinkedList<>();
		heroBullets = new LinkedList<>();
		allEnemyBullets = new LinkedList<>();
		props = new LinkedList<>();

		this.gameLoopScheduler = this.renderScheduler = Executors.newSingleThreadScheduledExecutor(runnable ->
		{
			Thread t = new Thread(runnable);
			t.setDaemon(true);
			return t;
		});

		int fpsInterval = 1000 / fps;

		/* 常用 fps 设置
		 * > 90fps -> 11ms (20ms)
		 *   60fps -> 16ms (25ms)
		 *   45fps -> 22ms (30ms)
		 */
		this.timeInterval = switch (fpsInterval)
		{
			case 16 -> 25;
			case 22 -> 30;
			default -> 20;
		};

		MouseController.SetControl(this, warplaneHero);
	}

	public static Game StartGame(int difficulty, boolean musicOn, int fps)
	{
		MusicPlayer.PlayBgm(musicOn);
		return new Game(difficulty, fps);
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
						score += enemy.GetScore();
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
					this.allEnemies.add(Warplane.GenerateWarplane());
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

			if (this.warplaneHero.GetNotFlying() || this.warplaneHero.GetHealth() <= 0)
			{
				this.gameLoopScheduler.shutdown();
				this.renderScheduler.shutdown();
				gameOver = true;
				System.out.println("Game Over");

				MusicPlayer.PlayGameOver();
			}
		};
		this.gameLoopScheduler.scheduleWithFixedDelay(gameTask, this.timeInterval, this.timeInterval, TimeUnit.MILLISECONDS);

		int fpsInterval = 1000 / 90;
		this.renderScheduler.scheduleWithFixedDelay(this::repaint, fpsInterval, fpsInterval, TimeUnit.MILLISECONDS);
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

		g.drawImage(ImageManager.BackgroundImg, 0, this.backgroundTop - Program.HEIGHT, null);
		g.drawImage(ImageManager.BackgroundImg, 0, this.backgroundTop, null);
		this.backgroundTop = this.backgroundTop == Program.HEIGHT ? 0 : this.backgroundTop + 1;

		// 逻辑: 先画子弹和道具, 后画飞机
		synchronized (this.allEnemyBullets)
		{
			paintImage(g, new ArrayList<>(this.allEnemyBullets));
		}
		synchronized (this.heroBullets)
		{
			paintImage(g, new ArrayList<>(this.heroBullets));
		}
		synchronized (this.props)
		{
			paintImage(g, new ArrayList<>(this.props));
		}
		synchronized (this.allEnemies)
		{
			paintImage(g, new ArrayList<>(this.allEnemies));
		}

		paintImage(g, List.of(this.warplaneHero));

		PaintScoreAndHealth(g);
	}

	private void paintImage(Graphics g, List<? extends Flying> flyingList)
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
		g.drawString("Health: " + this.warplaneHero.GetHealth(), x, y + 20);
	}

}
