package com.halfcooler.flying.warplane;

import java.util.LinkedList;
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
	public void goForward()
	{
		super.goForward();
		if (locationY >= Program.HEIGHT)
			setVanish();
	}

	@Override
	public List<Bullet> getShots()
	{
		return new LinkedList<>();
	}
}
