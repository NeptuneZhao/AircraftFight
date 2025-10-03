package com.halfcooler.flying.bullet;

import com.halfcooler.Program;
import com.halfcooler.flying.*;

/// <summary>
/// 你好，欢迎来的抽象的子弹 <br>
/// 子弹是飞行物的一种 <br>
/// 其实，加血道具是伤害为负的子弹 <br>
/// </summary>
/// @see com.halfcooler.flying.Flying
public abstract class Bullet extends Flying
{
	private final int power;

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
	public void GoForward()
	{
		super.GoForward();

		if (this.locationX <= 0 || this.locationX >= Program.WIDTH)
			this.SetVanish();

		if ( (this.speedY > 0 && this.locationY >= Program.HEIGHT) || this.locationY <= 0)
			this.SetVanish();
	}

}
