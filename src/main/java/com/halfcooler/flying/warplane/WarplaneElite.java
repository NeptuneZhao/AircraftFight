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

		Bullet bullet;

		// TODO 设置精英机发射次数 & 伤害
		// 构造子弹绝对得用外部类, 你就信我 20250919
		for (int i = 0; i < 999; i++)
		{
			bullet = new BulletEnemy(this.getX(), this.getY(), 0, this.getSpeedY(), 30);
			shots.add(bullet);
		}
		return shots;
	}

	@Override
	public void setVanish()
	{
		super.setVanish();
		// 实验 1:3 - 精英敌机坠毁后随机产生某种向下飞行的道具（或不产生）
		// 20250919
		// 产生概率
		double rand = Math.random();
		if (rand < 0.2)
		{

		}
	}
}
