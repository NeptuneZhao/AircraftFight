package com.halfcooler.flying.prop;

import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.warplane.*;

import java.util.List;

public class PropBullet extends Prop
{

	public PropBullet(Warplane plane)
	{
		super(plane);
	}

	@Override
	public void takeEffect(WarplaneHero hero, List<Warplane> enemies, java.util.List<Bullet> bullets)
	{
		// 英雄机加子弹
	}
}
