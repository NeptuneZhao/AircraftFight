package com.halfcooler.flying.bullet;

import com.halfcooler.flying.warplane.WarplaneBoss;
import com.halfcooler.flying.warplane.WarplaneElite;
import com.halfcooler.flying.warplane.WarplanePlus;
import com.halfcooler.game.statistics.Status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/// <summary>
/// 有人问我，为什么要分敌人子弹和玩家子弹？<br>
/// 因为导入图片的时候要分开，真坏啊。
/// </summary>
/// @see com.halfcooler.flying.bullet.Bullet
public class BulletEnemy extends Bullet
{
	private BulletEnemy(int locX, int locY, int speedX, int speedY, int power)
	{
		super(locX, locY, speedX, speedY, power);
	}

	public static List<BulletEnemy> DirectInstance(WarplaneElite elite)
	{
		return Collections.singletonList(new BulletEnemy(elite.GetX(), elite.GetY(), 0, elite.GetSpeedY() + 5, Status.GetDamage(elite)));
	}

	public static List<BulletEnemy> ScatterInstance(WarplanePlus plus)
	{
		return List.of(
			new BulletEnemy(plus.GetX() - 30, plus.GetY(), 0, plus.GetSpeedY() + 5, Status.GetDamage(plus)),
			new BulletEnemy(plus.GetX(), plus.GetY(), 0, plus.GetSpeedY() + 5, Status.GetDamage(plus)),
			new BulletEnemy(plus.GetX() + 30, plus.GetY(), 0, plus.GetSpeedY() + 5, Status.GetDamage(plus)));
	}

	/// 20 个子弹... 就没中间了...<br>
	/// 这是 19 个
	public static List<BulletEnemy> RingInstance(WarplaneBoss boss)
	{
		var list = new ArrayList<BulletEnemy>();
		for (int i = -9; i <= 9; i++)
		{
			list.add(new BulletEnemy(
				boss.GetX() + (int)(180 * Math.sin(i * Math.PI / 18)),
				boss.GetY() + (int)(180 * Math.cos(i * Math.PI / 18)),
				0,
				boss.GetSpeedY() + 5,
				Status.GetDamage(boss)));
		}
		return list;
	}
}
