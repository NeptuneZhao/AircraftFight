package com.halfcooler.flying.warplane;

import com.halfcooler.Program;
import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.bullet.BulletHero;
import com.halfcooler.flying.prop.PropBullet;
import com.halfcooler.utils.ImageManager;

import java.util.List;

public class WarplaneHero extends Warplane
{
	public static final WarplaneHero Instance = new WarplaneHero(Program.WIDTH / 2, Program.HEIGHT - ImageManager.HeroImg.getHeight());

	private WarplaneHero(int x, int y)
	{
		super(x, y, 0, 0);
	}

	@Override
	public void GoForward() { }

	@Override
	public List<? extends Bullet> GetShots()
	{
		return PropBullet.ActiveType == 0 ?
			BulletHero.ParallelInstance(this, PropBullet.ActiveThread + 1) :
			BulletHero.CirclingInstance(this, PropBullet.ActiveThread + 1);
	}

	/// To be recorded
	public int Difficulty = 0, Score = 0, Time = 0, DamagedTotal = 0, Total = 0, Enemy = 0, Elite = 0, Plus = 0, Boss = 0;
}
