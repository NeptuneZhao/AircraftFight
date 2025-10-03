package com.halfcooler.flying.bullet;

import com.halfcooler.flying.warplane.WarplaneHero;

import java.util.ArrayList;
import java.util.List;

public class BulletHero extends Bullet
{
	private BulletHero(int locX, int locY, int speedX, int speedY, int power)
	{
		super(locX, locY, speedX, speedY, power);
	}

	public static List<BulletHero> ParallelInstance(WarplaneHero hero, int parallelCount)
	{
		int separation = 15;
		List<BulletHero> bullets = new ArrayList<>();

		if (parallelCount % 2 == 1) bullets.add(new BulletHero(hero.GetX(), hero.GetY(), 0, -5, 30));
		for (int i = 1; i <= parallelCount >> 1; i++)
		{
			bullets.add(new BulletHero(hero.GetX() - separation * i, hero.GetY(), 0, -5, 30));
			bullets.add(new BulletHero(hero.GetX() + separation * i, hero.GetY(), 0, -5, 30));
		}

		return bullets;
	}


}
