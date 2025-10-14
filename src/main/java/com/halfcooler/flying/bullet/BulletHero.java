package com.halfcooler.flying.bullet;

import com.halfcooler.flying.warplane.WarplaneHero;
import com.halfcooler.game.statistics.Status;

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
		int separation = 20;
		List<BulletHero> bullets = new ArrayList<>();

		if (parallelCount % 2 == 1) bullets.add(new BulletHero(hero.GetX(), hero.GetY(), 0, -5, Status.GetDamage(hero)));
		for (int i = 1; i <= parallelCount >> 1; i++)
		{
			bullets.add(new BulletHero(hero.GetX() - separation * i, hero.GetY(), 0, -5, Status.GetDamage(hero)));
			bullets.add(new BulletHero(hero.GetX() + separation * i, hero.GetY(), 0, -5, Status.GetDamage(hero)));
		}

		return bullets;
	}

	public static List<BulletHero> CirclingInstance(WarplaneHero hero, int parallelCount)
	{
		int bulletCount = 10 * parallelCount;
		List<BulletHero> bullets = new ArrayList<>();

		for (int i = 0; i < bulletCount; i++)
		{
			double angle = 2 * Math.PI / bulletCount * i;
			int speedX = (int)(5 * Math.cos(angle));
			int speedY = (int)(5 * Math.sin(angle));

			bullets.add(new BulletHero(hero.GetX(), hero.GetY(), speedX, speedY, Status.GetDamage(hero)));
		}

		return bullets;
	}
}
