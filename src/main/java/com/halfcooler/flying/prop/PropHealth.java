package com.halfcooler.flying.prop;

import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.warplane.Warplane;
import com.halfcooler.flying.warplane.WarplaneHero;
import com.halfcooler.game.statistics.Status;

import java.util.List;

public class PropHealth extends Prop
{
	public PropHealth(Warplane plane)
	{
		super(plane);
	}

	public PropHealth(Warplane plane, int x)
	{
		super(plane, x);
	}

	@Override
	public void TakeEffect(WarplaneHero hero, List<Warplane> enemies, List<Bullet> bullets)
	{
		hero.ChangeHealth(Status.GetHealth(hero) / 100);
	}
}
