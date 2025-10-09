package com.halfcooler.game.statistics;

import com.halfcooler.flying.warplane.*;

public class Score
{
	private final int difficulty, enemyScore, eliteScore, plusScore, bossScore;

	public Score(int df)
	{
		this.difficulty = (df + 1) * 100;

		this.enemyScore = df + 1;
		this.eliteScore = (df + 1) * 3;
		this.plusScore = (df + 1) * 5;
		this.bossScore = (df + 1) * 80;

	}

	/// 根据存活敌机数判定得分, 只在困难模式生效. 每多存活 1 敌机, 分数增加 1%。
	/// @param currentAlive 当前存活敌机数
	/// @return 得分
	public int GetScore(Warplane plane, int currentAlive)
	{
		return switch (plane)
		{
			case WarplaneEnemy _ -> this.enemyScore;
			case WarplaneElite _ -> this.eliteScore;
			case WarplanePlus _ -> this.plusScore;
			case WarplaneBoss _ -> this.bossScore;
			default -> 0;
		} * (this.difficulty == 300 ? (100 + currentAlive) / 100 : 1);
	}

}
