package com.halfcooler.game.statistics;

import com.halfcooler.flying.warplane.*;

public final class Status
{
	// 伤害区
	private static final int
		HeroBullet = 1,
		EliteBullet = 1,
		PlusBullet = 3,
		BossBullet = 5;

	/// 根据飞机类型获取子弹伤害, 英雄机子弹有 20% 概率造成双倍伤害, 5% 概率造成暴击伤害(5 倍), 0.01% 概率造成 2147483647 点伤害。<br>
	/// 模式匹配, 仅 >JDK21
	/// @param plane 飞机类型
	/// @return 伤害值
	public static int GetDamage(Warplane plane)
	{
		double rand = Math.random();
		return switch (plane)
		{
			case WarplaneHero _ -> rand < 0.0001 ? Integer.MAX_VALUE : (rand < 0.05 ? HeroBullet * 5 : (rand < 0.2 ? HeroBullet * 2 : HeroBullet));
			case WarplaneElite _ -> EliteBullet;
			case WarplanePlus _ -> PlusBullet;
			case WarplaneBoss _ -> BossBullet;
			default -> 0;
		};
	}

	// 血量根据设定的伤害自动浮动
	private static final int
		HeroHealth = 100,
		EnemyHealth = 1,
		EliteHealth = 3,
		PlusHealth = 5,
		BossHealth = 300;
}
