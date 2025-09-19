package com.halfcooler.flying.bullet;

import com.halfcooler.Program;
import com.halfcooler.flying.Flying;

public abstract class Bullet extends Flying
{
	private int power = 10;

	public int getPower()
	{
		return this.power;
	}

	public Bullet(int locX, int locY, int speedX, int speedY, int power)
	{
		super(locX, locY, speedX, speedY);
		this.power = power;
	}

	@Override
	public void goForward()
	{
		super.goForward();

		if (locationX <= 0 || locationX >= Program.WIDTH)
			this.setVanish();

		if ( (speedY > 0 && locationY >= Program.HEIGHT) || locationY <= 0)
			this.setVanish();
	}
}
