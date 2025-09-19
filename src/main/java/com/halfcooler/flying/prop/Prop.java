package com.halfcooler.flying.prop;

import com.halfcooler.flying.warplane.*;
public abstract class Prop
{
	private int x, y, speedX, speedY;

	/// 由掉落的飞机构造
	public Prop(Warplane plane)
	{
		x = plane.getX();
		y = plane.getY();
		speedX = plane.getSpeedX();
		speedY = plane.getSpeedY();
	}
}
