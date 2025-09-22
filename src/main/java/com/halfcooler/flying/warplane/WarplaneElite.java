package com.halfcooler.flying.warplane;

import com.halfcooler.Program;
import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.bullet.BulletEnemy;

import java.util.LinkedList;
import java.util.List;

public class WarplaneElite extends Warplane
{
	public WarplaneElite(int x, int y, int speedX, int speedY, int health)
	{
		super(x, y, speedX, speedY, health);
	}

	@Override
	public void goForward()
	{
		super.goForward();
		if (locationY >= Program.HEIGHT)
			setVanish();
	}

	// 实验 1:2 - 精英敌机按设定周期直射子弹
	// 20250919
	// 周期交给 Game
	@Override
	public List<Bullet> getShots()
	{
		List<Bullet> shots = new LinkedList<>();

		// TODO 设置精英机发射次数 & 伤害
		// 构造子弹绝对得用外部类, 你就信我 20250919
		for (int i = 0; i < 1; i++)
			shots.add( new BulletEnemy(this.getX(), this.getY(), 0, this.getSpeedY(), 30) );

		return shots;
	}

	@Override
	public int getScore()
	{
		return 30;
	}
}
