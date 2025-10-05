package com.halfcooler.flying.warplane;

import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.bullet.type.BulletTypeEnemy;

import java.util.List;

public class WarplaneEnemy extends Warplane
{
	public WarplaneEnemy(int x, int y, int speedX, int speedY, int health)
	{
		super(x, y, speedX, speedY, health);
	}

	@Override
	public List<? extends Bullet> GetShots()
	{
		return new BulletTypeEnemy().Shoots(this);
	}

	@Override
	public int GetScore()
	{
		return 10;
	}
}
