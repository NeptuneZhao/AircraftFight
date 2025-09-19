package com.halfcooler.flying.warplane;

import java.util.LinkedList;
import java.util.List;
import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.bullet.BulletHero;

public class WarplaneHero extends Warplane
{
	public WarplaneHero(int x, int y, int speedX, int speedY, int health)
	{
		super(x, y, speedX, speedY, health);
	}

	@Override
	public void goForward()
	{
		// 鼠标控制
	}

	@Override
	public List<Bullet> getShots()
	{
		List<Bullet> shots = new LinkedList<>();

		// 英雄机: 就射一次
		shots.add(new BulletHero(this.getX(), this.getY() - 2, 0, this.getSpeedY() - 5, 30));

		return shots;
	}
}
