package com.halfcooler.flying.warplane;

import com.halfcooler.Program;
import com.halfcooler.flying.Flying;
import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.game.statistics.Status;
import com.halfcooler.utils.ImageManager;

import java.util.List;
import java.util.Random;

/// 哎, 该死的工厂模式
public abstract class Warplane extends Flying
{
	private final int maxHealth;
	private int health;

	public Warplane(int locX, int locY, int speedX, int speedY)
	{
		super(locX, locY, speedX, speedY);
		this.maxHealth = this.health = Status.GetHealth(this);
	}

	public int GetHealth()
	{
		return this.health;
	}

	/// 注意是血量变化, 本方法已包含归零死亡
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
		this.locationX += !(this.locationX <= ImageManager.GetImage(this).getWidth() || this.locationX >= Program.WIDTH - ImageManager.GetImage(this).getWidth()) ? this.speedX : -this.speedX;
		this.locationY += this.speedY;

		if (locationY >= Program.HEIGHT)
			this.SetVanish();
	}

	public abstract List<? extends Bullet> GetShots();

	/// 产生敌机
	public static Warplane GenerateWarplane(int time, int score)
	{
		double rand = Math.random();
		Random randGen = new Random();
		if (rand < 0.7) return new WarplaneEnemy(randGen.nextInt(Program.WIDTH - ImageManager.EnemyImg.getWidth() / 2), 0);
		else if (rand < 0.85) return new WarplaneElite(randGen.nextInt(Program.WIDTH - ImageManager.EliteImg.getWidth() / 2), 0, time, score);
		else return new WarplanePlus(randGen.nextInt(Program.WIDTH - ImageManager.PlusImg.getWidth() / 2), 0, time, score);
	}

}
