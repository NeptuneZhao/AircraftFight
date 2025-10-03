package com.halfcooler.flying.warplane;

import com.halfcooler.flying.bullet.*;

import java.util.List;

public class WarplanePlus extends Warplane
{
	public WarplanePlus(int x, int y, int speedX, int speedY, int health)
	{
		super(x, y, speedX, speedY, health);
	}

	@Override
	public List<Bullet> GetShots()
	{
		return List.copyOf(BulletEnemy.ScatterInstance(this));
	}

	@Override
	public int GetScore()
	{
		return 66;
	}
}
