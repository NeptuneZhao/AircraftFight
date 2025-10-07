package com.halfcooler.flying.warplane;

import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.bullet.type.BulletTypePlus;

import java.util.List;

public class WarplanePlus extends Warplane
{
	public WarplanePlus(int x, int y, int speedX, int speedY, int health)
	{
		super(x, y, speedX, speedY, health);
	}

	@Override
	public List<? extends Bullet> GetShots()
	{
		return new BulletTypePlus().Shoots(this);
	}
}
