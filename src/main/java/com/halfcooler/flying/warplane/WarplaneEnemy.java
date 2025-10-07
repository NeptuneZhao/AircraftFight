package com.halfcooler.flying.warplane;

import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.bullet.type.BulletTypeEnemy;

import java.util.List;

public class WarplaneEnemy extends Warplane
{
	public WarplaneEnemy(int x, int y, int speedX, int speedY)
	{
		super(x, y, speedX, speedY);
	}

	@Override
	public List<? extends Bullet> GetShots()
	{
		return new BulletTypeEnemy().Shoots(this);
	}
}
