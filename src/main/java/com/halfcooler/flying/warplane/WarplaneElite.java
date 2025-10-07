package com.halfcooler.flying.warplane;


import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.bullet.type.BulletTypeElite;

import java.util.List;

public class WarplaneElite extends Warplane
{
	public WarplaneElite(int x, int y, int speedX, int speedY, int health)
	{
		super(x, y, speedX, speedY, health);
	}

	// 实验 1:2 - 精英敌机按设定周期直射子弹
	// 20250919
	// 周期交给 Game
	@Override
	public List<? extends Bullet> GetShots()
	{
		return new BulletTypeElite().Shoots(this);
	}

}
