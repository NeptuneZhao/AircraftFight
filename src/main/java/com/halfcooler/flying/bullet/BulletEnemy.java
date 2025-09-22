package com.halfcooler.flying.bullet;

/// <summary>
/// 有人问我，为什么要分敌人子弹和玩家子弹？<br>
/// 因为导入图片的时候要分开，真坏啊。
/// </summary>
/// @see com.halfcooler.flying.bullet.Bullet
public class BulletEnemy extends Bullet
{
	public BulletEnemy(int locX, int locY, int speedX, int speedY, int power)
	{
		super(locX, locY, speedX, speedY, power);
	}
}
