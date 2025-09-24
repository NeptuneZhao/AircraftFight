package com.halfcooler.flying.warplane;

import java.util.List;
import com.halfcooler.Program;
import com.halfcooler.flying.bullet.Bullet;

public class WarplaneEnemy extends Warplane
{
	public WarplaneEnemy(int x, int y, int speedX, int speedY, int health)
	{
		super(x, y, speedX, speedY, health);
	}

	@Override
	public void GoForward()
	{
		super.GoForward();
		if (locationY >= Program.HEIGHT)
			SetVanish();
	}

	@Override
	public List<Bullet> GetShots()
	{
		return List.of();
	}

	@Override
	public int getScore()
	{
		return 10;
	}
}
