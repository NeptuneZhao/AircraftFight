package com.halfcooler.game;

import com.halfcooler.flying.Flying;
import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.prop.Prop;
import com.halfcooler.flying.warplane.*;
import com.halfcooler.game.statistics.Interval;
import com.halfcooler.game.statistics.Resources;
import com.halfcooler.game.statistics.Score;
import com.halfcooler.game.utils.MouseController;
import com.halfcooler.game.utils.SwingUtils;
import com.halfcooler.music.MusicPlayer;

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
	private int backgroundTop = 0;
	private final ScheduledExecutorService gameLoopScheduler, renderScheduler;
	
	private WarplaneBoss boss = null;
	private final BufferedImage backgroundImg;
	private final List<Warplane> allEnemies;
	private final List<Bullet> allEnemyBullets;
	private final List<Bullet> heroBullets;
	private final List<Prop> props;

	private int cycleTime, cycleEnemyBulletTime;

	private int score = 0;
	private int time = 0;
	private float storeScore = 0f;

	public final Score ScoreP;
	public final Interval IntervalP;

	public int GetScore()
	{
		return score;
	}

	public int GetMillisecond()
	{
		return time;
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawImage(this.backgroundImg, 0, this.backgroundTop - Resources.HEIGHT, Resources.WIDTH, Resources.HEIGHT, null);
		g.drawImage(this.backgroundImg, 0, this.backgroundTop, Resources.WIDTH, Resources.HEIGHT, null);
		this.backgroundTop = this.backgroundTop == Resources.HEIGHT ? 0 : this.backgroundTop + 1;

		// 逻辑: 先画子弹和道具, 后画飞机
		synchronized (this.allEnemyBullets)
		{
			SwingUtils.DrawImage(g, this.allEnemyBullets);
		}
		synchronized (this.heroBullets)
		{
			SwingUtils.DrawImage(g, this.heroBullets);
		}
		synchronized (this.props)
		{
			SwingUtils.DrawImage(g, this.props);
		}
		synchronized (this.allEnemies)
		{
			SwingUtils.DrawImage(g, this.allEnemies);
		}

		SwingUtils.DrawImage(g, List.of(WarplaneHero.Instance));
		paintScoreHealth(g);
	}

	private void paintScoreHealth(Graphics g)
	{
		Font f = new Font("Consolas", Font.PLAIN, 20);
		SwingUtils.Write(g, String.format("Score: %.2f", this.storeScore), Color.WHITE, f, 10, 25);
		SwingUtils.Write(g, "Health: " + WarplaneHero.Instance.GetHealth(), Color.WHITE, f, 10, 45);
	}

	public Game(int difficulty, int fps)
	{
		WarplaneHero.Instance.Difficulty = difficulty;

		this.ScoreP = new Score(difficulty);
		this.IntervalP = new Interval(fps, difficulty);

		this.backgroundImg = new Background(difficulty).backgroundImg();

		this.allEnemies = new LinkedList<>();
		this.heroBullets = new LinkedList<>();
		this.allEnemyBullets = new LinkedList<>();
		this.props = new LinkedList<>();

		this.gameLoopScheduler = this.renderScheduler = Executors.newSingleThreadScheduledExecutor(runnable ->
		{
			Thread t = new Thread(runnable);
			t.setDaemon(true);
			return t;
		});

		MouseController.SetControl(this, WarplaneHero.Instance);
	}

	public static Game StartGame(int difficulty, boolean musicOn, int fps)
	{
		MusicPlayer.PlayBgm(musicOn);
		return new Game(difficulty, fps);
	}

	public void Loop()
	{
		Runnable gameTask = () ->
		{
			this.time += IntervalP.TimeInterval;
			WarplaneBoss.LastInterval += IntervalP.TimeInterval;

			// Boss 事件
			// 生
			boss = WarplaneBoss.GenerateBoss();
			if (boss != null)
				this.allEnemies.add(boss);

			if (timeCycled(0))
			{
				// 产生敌机
				if (this.allEnemies.size() < IntervalP.GetMaxEnemies(this.time, this.score))
					this.allEnemies.add(Warplane.GenerateWarplane(this.time, this.score));
				// 射击
				if (timeCycled(1)) for (Warplane enemy : this.allEnemies) this.allEnemyBullets.addAll(enemy.GetShots());

				this.heroBullets.addAll(WarplaneHero.Instance.GetShots());
				MusicPlayer.PlayBulletMusic();
			}

			this.moveEvent();
			this.crashEvent();
			this.postRemoveEvent();
			this.gameOverEvent();
		};

		this.gameLoopScheduler.scheduleWithFixedDelay(gameTask, this.IntervalP.TimeInterval, this.IntervalP.TimeInterval, TimeUnit.MILLISECONDS);
		this.renderScheduler.scheduleWithFixedDelay(this::repaint, this.IntervalP.FpsInterval, this.IntervalP.FpsInterval, TimeUnit.MILLISECONDS);
	}

	/// 0 是产生循环<br>
	/// 1 是射击循环
	private boolean timeCycled(int type)
	{
		this.cycleTime += IntervalP.TimeInterval;
		this.cycleEnemyBulletTime += IntervalP.TimeInterval;

		int cycleDuration = IntervalP.CycleDuration;
		int cycleEnemyDuration = IntervalP.GetCycleEnemyDuration(this.time, this.score);
		switch (type)
		{
			case 0 ->
			{
				if (this.cycleTime > cycleDuration)
				{
					this.cycleTime %= cycleDuration;
					return true;
				}
			}
			case 1 ->
			{
				if (this.cycleEnemyBulletTime > cycleEnemyDuration)
				{
					this.cycleEnemyBulletTime %= cycleEnemyDuration;
					return true;
				}
			}
			default -> throw new IllegalArgumentException("Invalid type");
		}
		return false;
	}

	/// 碰撞检测、产生道具、Boss 死亡事件
	private void crashEvent()
	{
		// 子弹打自己
		for (Bullet bullet : allEnemyBullets)
		{
			if (bullet.IsDead())
				continue;

			if (WarplaneHero.Instance.IsDead())
				return;

			if (WarplaneHero.Instance.IsCrash(bullet))
			{
				MusicPlayer.PlayBulletHitMusic();
				WarplaneHero.Instance.ChangeHealth(-bullet.GetPower());
				bullet.SetVanish();
			}
		}

		// 子弹打敌机
		for (Bullet bullet : heroBullets)
		{
			if (bullet.IsDead())
				continue;

			for (Warplane enemy : allEnemies)
			{
				if (enemy.IsDead())
					continue;

				if (enemy.IsCrash(bullet))
				{
					MusicPlayer.PlayBulletHitMusic();
					enemy.ChangeHealth(-bullet.GetPower());
					bullet.SetVanish();

					if (enemy.IsDead())
					{
						WarplaneHero.Instance.Total++;
						switch (enemy)
						{
							case WarplaneEnemy ignored -> WarplaneHero.Instance.Enemy++;
							case WarplaneElite ignored -> WarplaneHero.Instance.Elite++;
							case WarplanePlus ignored -> WarplaneHero.Instance.Plus++;
							case WarplaneBoss ignored ->
							{
								WarplaneHero.Instance.Boss++;
								WarplaneBoss.BossDeathEvent();
							}
							default -> throw new IllegalArgumentException("Invalid type");
						}

						float scores = ScoreP.GetScore(enemy, allEnemies.size());
						storeScore += scores;
						score += (int) scores;
						WarplaneBoss.LastScore += (int) scores;
						props.addAll(Prop.GenerateProp(enemy));
					}
				}

				// 敌机撞自己, GAME OVER
				if (WarplaneHero.Instance.IsDead())
					return;

				if (WarplaneHero.Instance.IsCrash(enemy) || enemy.IsCrash(WarplaneHero.Instance))
				{
					WarplaneHero.Instance.SetVanish();
					enemy.SetVanish();
				}
			}

			// 我吃撞到的道具
			for (Prop prop : props)
			{
				if (prop.IsDead())
					continue;

				if (WarplaneHero.Instance.IsCrash(prop))
				{
					prop.TakeEffect(WarplaneHero.Instance, allEnemies, allEnemyBullets);
					prop.SetVanish();
				}
			}
		}
	}

	private void moveEvent()
	{
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
	}

	private void postRemoveEvent()
	{
		this.allEnemyBullets.removeIf(Flying::IsDead);
		this.heroBullets.removeIf(Flying::IsDead);
		this.allEnemies.removeIf(Flying::IsDead);
		this.props.removeIf(Flying::IsDead);
	}

	private void gameOverEvent()
	{
		if (WarplaneHero.Instance.IsDead() || WarplaneHero.Instance.GetHealth() <= 0)
		{
			WarplaneHero.Instance.Score = this.storeScore;
			WarplaneHero.Instance.Time = this.time;

			this.gameLoopScheduler.shutdown();
			this.renderScheduler.shutdown();
			MusicPlayer.PlayGameOver();
			synchronized (Resources.MainLock)
			{
				Resources.MainLock.notify();
			}
		}
	}

}
