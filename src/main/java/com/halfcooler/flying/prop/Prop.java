package com.halfcooler.flying.prop;

import java.util.List;

import com.halfcooler.Program;
import com.halfcooler.flying.Flying;
import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.warplane.*;

public abstract class Prop extends Flying
{
	private int x, y, speedX, speedY;

	/// 由掉落的飞机构造
	public Prop(Warplane plane)
	{
		this.x = plane.getX();
		this.y = plane.getY();
		this.speedX = plane.getSpeedX();
		this.speedY = plane.getSpeedY();
	}

	@Override
	public void goForward()
	{
		super.goForward();

		if (locationY >= Program.HEIGHT)
			this.setVanish();
	}

	public abstract void takeEffect(WarplaneHero hero, List<Warplane> enemies, List<Bullet> bullets);

}
