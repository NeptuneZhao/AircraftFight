package com.halfcooler.flying.warplane;

import java.util.LinkedList;
import java.util.List;

import com.halfcooler.Program;
import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.bullet.BulletHero;
import com.halfcooler.utils.ImageManager;

public class WarplaneHero extends Warplane
{
	public static WarplaneHero Instance = new WarplaneHero(Program.WIDTH / 2, Program.HEIGHT - ImageManager.HeroImg.getHeight(), 0, 0, 100);

	private WarplaneHero(int x, int y, int speedX, int speedY, int health)
	{
		super(x, y, speedX, speedY, health);
	}

	@Override
	public void GoForward()
	{
		// 鼠标控制
	}

	@Override
	public int getScore()
	{
		return 0;
	}

	@Override
	public List<Bullet> GetShots()
	{
		List<Bullet> shots = new LinkedList<>();

		// 英雄机: 就射一次
		shots.add( new BulletHero(this.GetX(), this.GetY() - 2, 0, this.GetSpeedY() - 5, 30) );

		return shots;
	}
}
