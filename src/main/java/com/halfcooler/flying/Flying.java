package com.halfcooler.flying;

import com.halfcooler.Program;
import com.halfcooler.flying.warplane.Warplane;
import com.halfcooler.utils.ImageManager;

import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;

public abstract class Flying
{
	protected int locationX, locationY, speedX, speedY;

	// 飞机图片
	protected BufferedImage image = null;
	protected int imageWidth = -1, imageHeight = -1;

	// Alive Flag
	protected boolean isFlying = false;

	public int getX()
	{
		return this.locationX;
	}

	public int getY()
	{
		return this.locationY;
	}

	public int getSpeedX()
	{
		return this.speedX;
	}

	public int getSpeedY()
	{
		return this.speedY;
	}

	public int getWidth()
	{
		return this.imageWidth;
	}

	public int getHeight()
	{
		return this.imageHeight;
	}

	public Flying()
	{
	}

	public Flying(int locationX, int locationY, int speedX, int speedY)
	{
		this.locationX = locationX;
		this.locationY = locationY;
		this.speedX = speedX;
		this.speedY = speedY;
	}

	/// 飞机移动, 越界反向
	public void goForward()
	{
		this.locationX += !(this.locationX <= 0 || this.locationX >= Program.WIDTH) ? this.speedX : -this.speedX;
		this.locationY += this.speedY;
	}

	public boolean goCrash(Flying flying)
	{
		if (flying == null)
			throw new InvalidParameterException("Parameter flying is null.");

		// 缩放因子
		// TODO: ???
		int factorMe = this instanceof Warplane ? 2 : 1;
		int factorHe = flying instanceof Warplane ? 2 : 1;

		int hisX = flying.getX(), hisY = flying.getY();
		int hisWidth = flying.getWidth(), hisHeight = flying.getHeight();

		return
				hisX + (hisWidth + this.getWidth()) / 2 > this.locationX &&
				hisX - (hisWidth + this.getWidth()) / 2 < this.locationX &&
				hisY + (hisHeight / factorHe + this.getHeight() / factorMe) / 2 > this.locationY &&
				hisY - (hisHeight / factorHe + this.getHeight() / factorMe) / 2 < this.locationY;
	}

	public void setLocation(double x, double y)
	{
		this.locationX = (int) x;
		this.locationY = (int) y;
	}

	public BufferedImage getImage()
	{
		if (this.image == null)
			this.image = ImageManager.getImage(this);
		return this.image;
	}

	public boolean getFlying()
	{
		return this.isFlying;
	}

	public void setVanish()
	{
		this.isFlying = false;
	}

}