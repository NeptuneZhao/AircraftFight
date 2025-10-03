package com.halfcooler.flying.bullet;

import com.halfcooler.flying.warplane.*;

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
		return Collections.singletonList(new BulletEnemy(elite.GetX(), elite.GetY(), 0, elite.GetSpeedY() + 5, 30));
	}

	public static List<BulletEnemy> ScatterInstance(WarplanePlus plus)
	{
		return List.of(
			new BulletEnemy(plus.GetX() - 15, plus.GetY(), 0, plus.GetSpeedY() + 5, 30),
			new BulletEnemy(plus.GetX(), plus.GetY(), 0, plus.GetSpeedY() + 5, 30),
			new BulletEnemy(plus.GetX() + 15, plus.GetY(), 0, plus.GetSpeedY() + 5, 30));
	}
}
