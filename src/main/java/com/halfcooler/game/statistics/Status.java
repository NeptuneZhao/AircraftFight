package com.halfcooler.game.statistics;

import com.halfcooler.flying.warplane.*;

public final class Status
{
	// 伤害区
	private static final int HeroBullet = 1;
	private static final int EliteBullet = 1;
	private static final int PlusBullet = 3;
	private static final int BossBullet = 5;

	/// 根据飞机类型获取子弹伤害, 英雄机子弹有 20% 概率造成双倍伤害, 5% 概率造成暴击伤害(5 倍), 0.01% 概率造成 2147483647 点伤害。<br>
	/// 模式匹配, 仅 >JDK21
	///
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
		HeroHealth = 100 * BossBullet,
		EnemyHealth = HeroBullet,
		EliteHealth = 2 * HeroBullet,
		PlusHealth = HeroBullet,
		BossHealth = 100 * HeroBullet;

	public static int GetHealth(Warplane plane)
	{
		return switch (plane)
		{
			case WarplaneHero _ -> HeroHealth;
			case WarplaneEnemy _ -> EnemyHealth;
			case WarplaneElite _ -> EliteHealth;
			case WarplanePlus _ -> PlusHealth;
			case WarplaneBoss _ -> BossHealth;
			default -> 0;
		};
	}

}
