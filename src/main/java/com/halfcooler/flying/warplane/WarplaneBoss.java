package com.halfcooler.flying.warplane;

import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.bullet.BulletEnemy;
import com.halfcooler.game.statistics.Resources;
import com.halfcooler.game.utils.ImageManager;
import com.halfcooler.music.MusicPlayer;

import java.util.List;

public class WarplaneBoss extends Warplane
{

	public static int LastInterval = 0, LastScore = 0;
	private static boolean generateFlag = false, isAlive = false;

	private WarplaneBoss(int x, int y)
	{
		super(x, y, 0, 5);
	}

	@Override
	public List<? extends Bullet> GetShots()
	{
		return Math.random() < 0.2 ? List.of() : List.copyOf(BulletEnemy.RingInstance(this));
	}

	@Override
	public void GoForward()
	{
		int speedX = (int) (25 * Math.sin(2 * Math.random() * Math.PI));
		this.locationX += !(this.locationX <= ImageManager.BossImg.getWidth() || this.locationX >= Resources.WIDTH - ImageManager.BossImg.getWidth()) ? speedX : -speedX;
	}

	/// 需要连续不断挂机 24.855 天才能把 Game.time 搞爆<br>
	public static WarplaneBoss GenerateBoss()
	{
		if (!generateFlag && (LastInterval > 1000 * 90 || LastScore > 1500))
		{
			MusicPlayer.PlayBossMusic();
			generateFlag = true;
		}

		// 达到目标，生成，打死之前不再生成和记录
		// Boss 独立占用
		if (generateFlag && !isAlive)
		{
			isAlive = true;
			return new WarplaneBoss(Resources.WIDTH / 2, (int) (Math.random() * Resources.HEIGHT / 20) + 90);
		}
		// 游戏主循环那边添加 null 检查
		else return null;
	}

	/*  注意, Boss 事件只负责生成、死亡。
	 *  移动、发射子弹、判断碰撞、生成道具、加分等逻辑均在游戏主循环中处理。
	 *  此处考虑的逻辑: 计数器、音乐
	 */

	/// 游戏主循环那边，监测死亡<br>
	public static void BossDeathEvent()
	{
		// 重置计数器状态
		LastInterval = LastScore = 0;
		generateFlag = isAlive = false;

		MusicPlayer.PlayBossKilledMusic();
	}
}
