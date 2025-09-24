package com.halfcooler.flying;

import com.halfcooler.Program;
import com.halfcooler.flying.warplane.Warplane;
import com.halfcooler.utils.ImageManager;

import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;

public abstract class Flying
{
	protected int locationX, locationY;
	protected final int speedX, speedY;

	// 飞机图片
	private BufferedImage image = null;
	private int imageWidth = -1, imageHeight = -1;

	// Alive Flag
	private boolean isFlying = true;

	public Flying(int locationX, int locationY, int speedX, int speedY)
	{
		this.locationX = locationX;
		this.locationY = locationY;
		this.speedX = speedX;
		this.speedY = speedY;
	}

	public int GetX()
	{
		return this.locationX;
	}

	public int GetY()
	{
		return this.locationY;
	}

	public int GetSpeedX()
	{
		return this.speedX;
	}

	public int GetSpeedY()
	{
		return this.speedY;
	}

	public int GetWidth()
	{
		if (this.imageWidth == -1)
			this.imageWidth = this.GetImage().getWidth();
		return this.imageWidth;
	}

	public int GetHeight()
	{
		if (this.imageHeight == -1)
			this.imageHeight = this.GetImage().getHeight();
		return this.imageHeight;
	}

	/// 飞机移动, 越界反向
	public void GoForward()
	{
		this.locationX += !(this.locationX <= 0 || this.locationX >= Program.WIDTH) ? this.speedX : -this.speedX;
		this.locationY += this.speedY;
	}

	/// 碰撞检测
	/// @param flying 另一个飞行物
	/// @return 是否碰撞
	public boolean IsCrash(Flying flying)
	{
		if (flying == null)
			throw new InvalidParameterException("Parameter flying is null.");

		// 缩放因子
		int factorMe = this instanceof Warplane ? 2 : 1;
		int factorHe = flying instanceof Warplane ? 2 : 1;

		int hisX = flying.GetX(), hisY = flying.GetY();
		int hisWidth = flying.GetWidth(), hisHeight = flying.GetHeight();

		return
			hisX + (hisWidth + this.GetWidth()) / 2 > this.locationX &&
			hisX - (hisWidth + this.GetWidth()) / 2 < this.locationX &&
			hisY + (hisHeight / factorHe + this.GetHeight() / factorMe) / 2 > this.locationY &&
			hisY - (hisHeight / factorHe + this.GetHeight() / factorMe) / 2 < this.locationY;
	}

	public void SetLocation(double x, double y)
	{
		this.locationX = (int) x;
		this.locationY = (int) y;
	}

	public BufferedImage GetImage()
	{
		if (this.image == null)
			this.image = ImageManager.GetImage(this);
		return this.image;
	}

	/// 是否已经消失 <br>
	/// 你问我为什么不直接用 GetFlying() <br>
	/// 谓词不允许调用方法体
	/// @see java.util.List#removeIf(java.util.function.Predicate)
	public boolean GetNotFlying()
	{
		return !this.isFlying;
	}

	public void SetVanish()
	{
		this.isFlying = false;
	}

}