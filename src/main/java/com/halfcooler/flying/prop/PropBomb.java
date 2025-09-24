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
	public void TakeEffect(WarplaneHero hero, List<Warplane> enemies, List<Bullet> bullets)
	{
		// 插桩测试
		System.out.println("PropBomb takeEffect");

		for (Warplane enemy : enemies)
		{
			if (!(enemy instanceof WarplaneBoss))
				enemy.SetVanish();
		}

		for (Bullet bullet : bullets)
			bullet.SetVanish();
	}
}
