package com.halfcooler.game;

import com.halfcooler.Program;
import com.halfcooler.flying.Flying;
import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.prop.Prop;
import com.halfcooler.flying.warplane.Warplane;
import com.halfcooler.flying.warplane.WarplaneBoss;
import com.halfcooler.flying.warplane.WarplaneHero;
import com.halfcooler.game.statistics.Interval;
import com.halfcooler.game.statistics.Score;
import com.halfcooler.music.MusicPlayer;
import com.halfcooler.utils.ImageManager;
import com.halfcooler.utils.MouseController;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.halfcooler.game.GameUtilities.Write;

public class Game extends JPanel
{
	// public final RecordImplement Recorder;
	private int backgroundTop = 0;
	private final ScheduledExecutorService gameLoopScheduler, renderScheduler;

	private final WarplaneHero warplaneHero = WarplaneHero.Instance;
	private WarplaneBoss boss = null;
	private final List<Warplane> allEnemies;
	private final List<Bullet> allEnemyBullets;
	private final List<Bullet> heroBullets;
	private final List<Prop> props;

	private int cycleTime, cycleEnemyBulletTime;

	private int score = 0, time = 0;

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
		g.drawImage(ImageManager.BackgroundImg, 0, this.backgroundTop - Program.HEIGHT, Program.WIDTH, Program.HEIGHT, null);
		g.drawImage(ImageManager.BackgroundImg, 0, this.backgroundTop, Program.WIDTH, Program.HEIGHT, null);
		this.backgroundTop = this.backgroundTop == Program.HEIGHT ? 0 : this.backgroundTop + 1;

		// 逻辑: 先画子弹和道具, 后画飞机
		synchronized (this.allEnemyBullets)
		{
			GameUtilities.DrawImage(g, this.allEnemyBullets);
		}
		synchronized (this.heroBullets)
		{
			GameUtilities.DrawImage(g, this.heroBullets);
		}
		synchronized (this.props)
		{
			GameUtilities.DrawImage(g, this.props);
		}
		synchronized (this.allEnemies)
		{
			GameUtilities.DrawImage(g, this.allEnemies);
		}

		GameUtilities.DrawImage(g, List.of(this.warplaneHero));

		paintScoreHealth(g);
	}

	private void paintScoreHealth(Graphics g)
	{
		Font f = new Font("Segoe UI", Font.ITALIC, 20);
		Write(g, "Score: " + this.score, Color.WHITE, f, 10, 25);
		Write(g, "Health: " + this.warplaneHero.GetHealth(), Color.WHITE, f, 10, 45);
	}

	public Game(int difficulty, int fps)
	{
		this.ScoreP = new Score(difficulty);
		this.IntervalP = new Interval(fps, difficulty);

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

		MouseController.SetControl(this, warplaneHero);
	}

	public static Game StartGame(int difficulty, boolean musicOn, int fps)
	{
		MusicPlayer.PlayBgm(musicOn);
		return new Game(difficulty, fps);
	}

	/// 游戏的主循环<br>
	/// 我们所向披靡!
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
				if (timeCycled(1))
					for (Warplane enemy : this.allEnemies) this.allEnemyBullets.addAll(enemy.GetShots());

				this.heroBullets.addAll(this.warplaneHero.GetShots());
			}

			// 记住, 你所做的一切都是为了那该死的测试
			// 如果有 partial, 我愿意这样做
			// C# 神力, 小子
			this.moveEvent();
			this.crashEvent();
			this.postRemoveEvent();
			this.gameOverEvent();
		};
		this.gameLoopScheduler.scheduleWithFixedDelay(gameTask, IntervalP.TimeInterval, IntervalP.TimeInterval, TimeUnit.MILLISECONDS);
		this.renderScheduler.scheduleWithFixedDelay(this::repaint, IntervalP.FpsInterval, IntervalP.FpsInterval, TimeUnit.MILLISECONDS);
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

	/// 碰撞检测 <br>
	/// 产生道具 <br>
	/// Boss 死亡事件
	private void crashEvent()
	{
		// 子弹打自己
		for (Bullet bullet : allEnemyBullets)
		{
			if (bullet.IsDead())
				continue;

			if (warplaneHero.IsDead())
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
			if (bullet.IsDead())
				continue;

			for (Warplane enemy : allEnemies)
			{
				if (enemy.IsDead())
					continue;

				if (enemy.IsCrash(bullet))
				{
					enemy.ChangeHealth(-bullet.getPower());
					bullet.SetVanish();

					if (enemy.IsDead())
					{
						if (enemy instanceof WarplaneBoss)
						{
							WarplaneBoss.BossDeathEvent();
						}
						int scores = ScoreP.GetScore(enemy, allEnemies.size());
						score += scores;
						WarplaneBoss.LastScore += scores;
						props.addAll(Prop.GenerateProp(enemy));
					}
				}

				// 敌机撞自己, GAME OVER
				if (warplaneHero.IsDead())
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
				if (prop.IsDead())
					continue;

				if (warplaneHero.IsCrash(prop))
				{
					prop.TakeEffect(warplaneHero, allEnemies, allEnemyBullets);
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
		if (this.warplaneHero.IsDead() || this.warplaneHero.GetHealth() <= 0)
		{
			this.gameLoopScheduler.shutdown();
			this.renderScheduler.shutdown();
			MusicPlayer.PlayGameOver();
			synchronized (Program.MainLock)
			{
				Program.MainLock.notify();
			}
		}
	}

}
