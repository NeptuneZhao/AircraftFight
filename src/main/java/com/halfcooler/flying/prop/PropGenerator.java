package com.halfcooler.flying.prop;

import com.halfcooler.flying.warplane.*;

public final class PropGenerator
{
	public static Prop GenerateProp(Warplane enemy)
	{
		switch (enemy)
		{
			case WarplaneEnemy ignored ->
			{
				// 20% 加血, 10% 子弹
				double rand = Math.random();
				if (rand < 0.2)
					return new PropHealth(enemy);
				else if (rand < 0.3)
					return new PropBullet(enemy);
				else
					return null;
			}
			case WarplaneElite ignored ->
			{
				// 50% 加血, 30% 子弹, 20% 清屏
				double rand = Math.random();
				if (rand < 0.5)
					return new PropHealth(enemy);
				else if (rand < 0.8)
					return new PropBullet(enemy);
				else
					return new PropBomb(enemy);
			}
			case WarplaneBoss ignored ->
			{
				// 20% 加血, 30% 子弹, 50% 清屏
				double rand = Math.random();
				if (rand < 0.2)
					return new PropHealth(enemy);
				else if (rand < 0.5)
					return new PropBullet(enemy);
				else
					return new PropBomb(enemy);
			}
			case null, default ->
			{
				return null;
			}
		}
	}
}
