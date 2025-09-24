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
	public void TakeEffect(WarplaneHero hero, List<Warplane> enemies, java.util.List<Bullet> bullets)
	{
		// 插桩测试
		System.out.println("PropBullet takeEffect");

		// 英雄机加子弹

	}
}
