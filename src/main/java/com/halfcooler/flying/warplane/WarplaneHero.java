package com.halfcooler.flying.warplane;

import com.halfcooler.Program;
import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.bullet.BulletHero;
import com.halfcooler.flying.prop.PropBullet;
import com.halfcooler.utils.ImageManager;

import java.util.List;

public class WarplaneHero extends Warplane
{
	public static WarplaneHero Instance = new WarplaneHero(Program.WIDTH / 2, Program.HEIGHT - ImageManager.HeroImg.getHeight(), 0, 0, 1000000);

	private WarplaneHero(int x, int y, int speedX, int speedY, int health)
	{
		super(x, y, speedX, speedY, health);
	}

	@Override
	public void GoForward() { }

	@Override
	public int GetScore()
	{
		return 0;
	}

	@Override
	public List<? extends Bullet> GetShots()
	{
		return BulletHero.ParallelInstance(this, PropBullet.ActiveThread + 1);
	}


}
