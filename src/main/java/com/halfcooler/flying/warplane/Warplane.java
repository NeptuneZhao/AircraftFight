package com.halfcooler.flying.warplane;

import com.halfcooler.Program;
import com.halfcooler.flying.Flying;
import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.utils.ImageManager;

import java.util.List;

/// 哎, 该死的工厂模式
public abstract class Warplane extends Flying
{

	private final int maxHealth;
	private int health;

	public Warplane(int locX, int locY, int speedX, int speedY, int health)
	{
		super(locX, locY, speedX, speedY);

		this.maxHealth = health;
		this.health = health;
	}

	public int GetHealth()
	{
		return this.health;
	}

	/// 注意是血量变化
	/// @param amount 加血就是正, 掉血就是负
	public void ChangeHealth(int amount)
	{
		this.health += amount;

		if (this.health <= 0)
		{
			this.health = 0;
			this.SetVanish();
		}
		else if (this.health > this.maxHealth)
			this.health = this.maxHealth;
	}

	@Override
	public void GoForward()
	{
		this.locationX += !(this.locationX <= 0 || this.locationX >= Program.WIDTH) ? this.speedX : -this.speedX;
		this.locationY += this.speedY;

		if (locationY >= Program.HEIGHT)
			this.SetVanish();
	}

	public abstract List<Bullet> GetShots();

	public abstract int GetScore();

	/// 产生敌机
	public static Warplane GenerateWarplane()
	{
		double rand = Math.random();
		if (rand < 0.7) return new WarplaneEnemy(
			(int) (Math.random() * (Program.WIDTH - ImageManager.EnemyImg.getWidth())), // x
			(int) (Math.random() * Program.HEIGHT / 20), // y
			0, // speedX
			Math.random() < 0.01 ? 50 : 5, // speedY
			30);
		else if (rand < 0.85) return new WarplaneElite(
			(int) (Math.random() * (Program.WIDTH - ImageManager.EliteImg.getWidth())), // x
			(int) (Math.random() * Program.HEIGHT / 20), // y
			0, // speedX
			Math.random() < 0.01 ? 50 : 5, // speedY
			30);
		else return new WarplanePlus(
			(int) (Math.random() * (Program.WIDTH - ImageManager.PlusImg.getWidth())), // x
			(int) (Math.random() * Program.HEIGHT / 20), // y
			0, // speedX
			Math.random() < 0.01 ? 50 : 3, // speedY
			60);

	}

}
