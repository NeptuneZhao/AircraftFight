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

		if (this.locationX <= 0 || this.locationX >= Program.WIDTH)
			this.setVanish();

		if ( (this.speedY > 0 && this.locationY >= Program.HEIGHT) || this.locationY <= 0)
			this.setVanish();
	}
}
