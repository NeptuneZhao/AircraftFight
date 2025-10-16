package com.halfcooler.flying.prop;

import com.halfcooler.Program;
import com.halfcooler.flying.Flying;
import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.warplane.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/// <summary>
/// 该类表示游戏中的道具。<br>
/// </summary>
/// @see com.halfcooler.flying.Flying
public abstract class Prop extends Flying
{
	/// 由游戏主循环构造
	public Prop(Warplane plane)
	{
		super(plane.GetX(), plane.GetY(), 0, plane.GetSpeedY());
	}

	/// 有些产生好多个道具的飞机, 需要指定 x 坐标
	public Prop(Warplane plane, int x)
	{
		super(x, plane.GetY(), 0, plane.GetSpeedY());
	}

	public static List<Prop> GenerateProp(Warplane enemy)
	{
		double rand = Math.random();
		List<Prop> props;
		switch (enemy)
		{
			case WarplaneEnemy ignored -> props = Collections.singletonList(rand < 0.1 ? new PropHealth(enemy) : rand < 0.2 ? new PropBullet(enemy) : null);
			case WarplaneElite ignored -> props = Collections.singletonList(rand < 0.2 ? new PropHealth(enemy) : rand < 0.4 ? new PropBullet(enemy) : null);
			case WarplanePlus ignored -> props = Collections.singletonList(rand < 0.25 ? new PropHealth(enemy) : rand < 0.4 ? new PropBullet(enemy) : rand < 0.55 ? new PropPlus(enemy) : null);
			case WarplaneBoss ignored ->
			{
				// 60% 产生, 3 个
				// 30% 加血, 30% 子弹, 20% 超级, 20% 清屏
				props = new ArrayList<>();
				for (int i = -1; i < 2; i++)
				{
					rand = Math.random();
					if (Math.random() < 0.4) continue;
					props.add(
						rand < 0.3 ? new PropHealth(enemy, enemy.GetX() + 25 * i) :
						rand < 0.6 ? new PropBullet(enemy, enemy.GetX() + 25 * i) :
						rand < 0.8 ? new PropPlus(enemy, enemy.GetX() + 25 * i) :
						new PropBomb(enemy, enemy.GetX() + 25 * i));
				}
			}
			case null, default -> props = Collections.emptyList();
		}

		ArrayList<Prop> result = new ArrayList<>(props);
		result.removeIf(Objects::isNull);
		return result;

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
