package com.halfcooler.flying.prop;

import java.util.List;

import com.halfcooler.Program;
import com.halfcooler.flying.Flying;
import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.warplane.*;

/// <summary>
/// 该类表示游戏中的道具。<br>
/// 还没有写完
/// </summary>
/// @see com.halfcooler.flying.Flying
public abstract class Prop extends Flying
{
	/// 之前：由掉落的飞机构造
	/// 现在：由游戏主循环构造
	public Prop(Warplane plane)
	{
		super(plane.GetX(), plane.GetY(), 0, plane.GetSpeedY());
	}

	// 产生的概率以后再调
	public static Prop GenerateProp(Warplane enemy)
	{
		double rand = Math.random();
		switch (enemy)
		{
			case WarplaneEnemy ignored ->
			{
				// 插桩测试 - 通过
				// return new PropBomb(enemy);

				// 20% 加血, 10% 子弹
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

	@Override
	public void GoForward()
	{
		super.GoForward();

		if (locationY >= Program.HEIGHT)
			this.SetVanish();
	}

	public abstract void TakeEffect(WarplaneHero hero, List<Warplane> enemies, List<Bullet> bullets);

}
