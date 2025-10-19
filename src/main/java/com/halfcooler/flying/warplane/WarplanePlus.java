package com.halfcooler.flying.warplane;

import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.bullet.BulletEnemy;
import com.halfcooler.game.statistics.Resources;
import com.halfcooler.game.utils.ImageManager;

import java.util.List;

public class WarplanePlus extends Warplane
{
	public WarplanePlus(int x, int y, int time, int score)
	{
		super(x, y, 0, Resources.GameInstance.IntervalP.GetSpeedY(WarplanePlus.class, time, score));
	}

	@Override
	public void GoForward()
	{
		int speedX = (int) (25 * Math.sin(2 * Math.random() * Math.PI));
		this.locationX += !(this.locationX <= ImageManager.PlusImg.getWidth() || this.locationX >= Resources.WIDTH - ImageManager.PlusImg.getWidth()) ? speedX : -speedX;

		this.locationY += this.speedY;

		if (locationY >= Resources.HEIGHT)
			this.SetVanish();
	}

	@Override
	public List<? extends Bullet> GetShots()
	{
		return BulletEnemy.ScatterInstance(this);
	}
}
