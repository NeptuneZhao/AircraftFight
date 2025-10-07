package com.halfcooler.flying.warplane;

import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.bullet.type.BulletTypePlus;

import java.util.List;

public class WarplanePlus extends Warplane
{
	public WarplanePlus(int x, int y, int speedX, int speedY)
	{
		super(x, y, speedX, speedY);
	}

	@Override
	public List<? extends Bullet> GetShots()
	{
		return new BulletTypePlus().Shoots(this);
	}
}
