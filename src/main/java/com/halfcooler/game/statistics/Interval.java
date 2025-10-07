package com.halfcooler.game.statistics;

/// 基本软件包 com.halfcooler.game.statistics
public class Interval
{
	/// 渲染间隔
	public final int FpsInterval;

	/// 基本事件间隔
	public final int TimeInterval;

	/// 子弹发射间隔
	public final int CycleDuration;

	/// 敌机产生间隔
	private final int cycleEnemyDuration;

	/// 难度
	private final int difficulty;

	public Interval(int fps, int difficulty)
	{
		this.FpsInterval = 1000 / fps;
		this.TimeInterval = this.FpsInterval * 2;
		this.difficulty = difficulty + 1;
		this.CycleDuration = 900 / this.difficulty;
		this.cycleEnemyDuration = (int) (this.CycleDuration * 1.5);
	}

	/// 根据时间和分数获取敌机产生间隔, 只在困难模式生效. 每 60 秒或 600 分 减少 10ms(取最大), 最少减少到 200ms。
	/// @param timeEclipsed 已经过去的时间
	/// @param currentScore 当前分数
	/// @return 敌机产生间隔
	public int GetCycleEnemyDuration(int timeEclipsed, int currentScore)
	{
		return this.difficulty == 3 ?
			Math.max(200, this.cycleEnemyDuration - 10 *  Math.max(timeEclipsed / 60000, currentScore / 600)) :
			this.cycleEnemyDuration;
	}

	/// 根据时间和分数获取最大敌机数, 只在困难模式生效. 每 120 秒或 1200 分 增加 1(取最大), 最多增加到 10。
	/// @param timeEclipsed 已经过去的时间
	/// @param currentScore 当前分数
	/// @return 最大敌机数
	public int GetMaxEnemies(int timeEclipsed, int currentScore)
	{
		return Math.min(10, 5 + Math.max(timeEclipsed / 120000, currentScore / 1200));
	}
}
