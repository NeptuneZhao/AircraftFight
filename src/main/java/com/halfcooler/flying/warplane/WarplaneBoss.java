package com.halfcooler.flying.warplane;

import com.halfcooler.flying.bullet.Bullet;

import java.util.List;

/// Not completed
public class WarplaneBoss extends Warplane
{

	public WarplaneBoss(int x, int y, int speedX, int speedY, int health)
	{
		super(x, y, speedX, speedY, health);
	}

	@Override
	public List<Bullet> GetShots()
	{
		return List.of();
	}

	@Override
	public int GetScore()
	{
		return 200;
	}

	@Override
	public void GoForward()
	{

	}
}
