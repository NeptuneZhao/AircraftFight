package com.halfcooler.flying.prop;

import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.warplane.*;

import java.util.List;

public class PropHealth extends Prop
{
	public PropHealth(Warplane plane)
	{
		super(plane);
	}

	@Override
	public void TakeEffect(WarplaneHero hero, List<Warplane> enemies, List<Bullet> bullets)
	{
		// 插桩测试
		System.out.println("PropHealth takeEffect");
		hero.ChangeHealth(250);
	}
}
