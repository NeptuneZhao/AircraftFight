package com.halfcooler.flying.bullet;

import com.halfcooler.flying.Flying;
import com.halfcooler.game.statistics.Resources;

/// @see com.halfcooler.flying.Flying
public abstract class Bullet extends Flying
{
	private final int power;

	public int GetPower()
	{
		return this.power;
	}

	public Bullet(int locX, int locY, int speedX, int speedY, int power)
	{
		super(locX, locY, speedX, speedY);
		this.power = power;
	}

	@Override
	public void GoForward()
	{
		super.GoForward();

		if (this.locationX <= 0 || this.locationX >= Resources.WIDTH)
			this.SetVanish();

		if ( (this.speedY > 0 && this.locationY >= Resources.HEIGHT) || this.locationY <= 0)
			this.SetVanish();
	}

}
