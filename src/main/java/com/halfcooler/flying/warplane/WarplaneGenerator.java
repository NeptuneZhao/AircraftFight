package com.halfcooler.flying.warplane;

public final class WarplaneGenerator
{
	// 实验 1:1 - 每隔一定周期随机产生一架普通敌机或精英敌机
	// 20250919
	// 产生敌机的方法 (工厂), 周期产生 (调用) 交给 Game

	public static WarplaneEnemy GenerateEnemy(int x, int y, int speedX, int speedY, int health)
	{
		return new WarplaneEnemy(x, y, speedX, speedY, health);
	}



}
