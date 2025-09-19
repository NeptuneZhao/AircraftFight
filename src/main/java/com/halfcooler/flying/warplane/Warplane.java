package com.halfcooler.flying.warplane;

import com.halfcooler.flying.Flying;
import com.halfcooler.flying.bullet.Bullet;

import java.util.List;

public abstract class Warplane extends Flying
{

	protected int maxHealth, health;

	public int getHealth()
	{
		return health;
	}

	public Warplane(int locX, int locY, int speedX, int speedY, int health)
	{
		super(locX, locY, speedX, speedY);
		this.maxHealth = health;
		this.health = health;
	}

	/// 注意是血量变化
	/// @param amount 加血就是正, 掉血就是负
	public void ChangeHealth(int amount)
	{

		this.health += amount;
		if (this.health <= 0)
		{
			this.health = 0;
			this.setVanish();
		}
		else if (this.health > this.maxHealth)
			this.health = this.maxHealth;
	}

	public abstract List<Bullet> getShots();

}
