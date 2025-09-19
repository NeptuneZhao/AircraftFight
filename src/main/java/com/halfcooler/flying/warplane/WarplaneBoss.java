package com.halfcooler.flying.warplane;

import com.halfcooler.Program;
import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.prop.PropGenerator;

import java.util.List;

public class WarplaneBoss extends Warplane
{

	public WarplaneBoss(int x, int y, int speedX, int speedY, int health)
	{
		super(x, y, speedX, speedY, health);
	}

	@Override
	public List<Bullet> getShots()
	{
		return List.of();
	}

	@Override
	public int getScore()
	{
		return 200;
	}

	@Override
	public void goForward()
	{

	}

	@Override
	public void setVanish()
	{
		super.setVanish();
		PropGenerator.GenerateProp(this);
	}
}
