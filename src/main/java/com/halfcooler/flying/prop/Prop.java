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
	public static List<Prop> GenerateProp(Warplane enemy)
	{
		double rand = Math.random();
		List<Prop> props;
		switch (enemy)
		{
			case WarplaneEnemy ignored -> props = Collections.singletonList(rand < 0.2 ? new PropHealth(enemy) : rand < 0.3 ? new PropBullet(enemy) : null);
			case WarplaneElite ignored -> props = Collections.singletonList(rand < 0.5 ? new PropHealth(enemy) : rand < 0.8 ? new PropBullet(enemy) : null);
			case WarplaneBoss ignored ->
			{
				// 60% 产生, 3 个
				// 30% 加血, 50% 子弹, 20% 清屏
				props = new ArrayList<>();
				for (int i = 0; i < 3; i++)
				{
					rand = Math.random();
					if (Math.random() < 0.6) continue;
					props.add(rand < 0.3 ? new PropHealth(enemy) : rand < 0.8 ? new PropBullet(enemy) : new PropBomb(enemy));
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
