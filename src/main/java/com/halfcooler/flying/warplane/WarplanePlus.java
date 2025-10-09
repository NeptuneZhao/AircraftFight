package com.halfcooler.flying.warplane;

import com.halfcooler.Program;
import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.bullet.type.BulletTypePlus;
import com.halfcooler.utils.ImageManager;

import java.util.List;

public class WarplanePlus extends Warplane
{
	public WarplanePlus(int x, int y, int time, int score)
	{
		super(x, y, 0, Program.GameInstance.IntervalP.GetSpeedY(WarplanePlus.class, time, score));
	}

	@Override
	public void GoForward()
	{
		int speedX = (int) (25 * Math.sin(2 * Math.random() * Math.PI));
		this.locationX += !(this.locationX <= ImageManager.PlusImg.getWidth() || this.locationX >= Program.WIDTH - ImageManager.PlusImg.getWidth()) ? speedX : -speedX;

		this.locationY += this.speedY;

		if (locationY >= Program.HEIGHT)
			this.SetVanish();
	}

	@Override
	public List<? extends Bullet> GetShots()
	{
		return new BulletTypePlus().Shoots(this);
	}
}
