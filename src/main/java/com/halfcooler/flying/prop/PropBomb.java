package com.halfcooler.flying.prop;

import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.warplane.*;

import java.util.List;

public class PropBomb extends Prop
{

	public PropBomb(Warplane plane)
	{
		super(plane);
	}

	@Override
	public void takeEffect(WarplaneHero hero, List<Warplane> enemies, List<Bullet> bullets)
	{
		for (Warplane enemy : enemies)
		{
			if (!(enemy instanceof WarplaneBoss))
				enemy.setVanish();
		}

		for (Bullet bullet : bullets)
			bullet.setVanish();
	}
}
