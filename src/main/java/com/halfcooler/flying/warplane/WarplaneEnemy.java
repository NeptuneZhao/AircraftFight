package com.halfcooler.flying.warplane;

import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.game.statistics.Resources;

import java.util.List;

public class WarplaneEnemy extends Warplane
{
	public WarplaneEnemy(int x, int y)
	{
		super(x, y, 0, Resources.GameInstance.IntervalP.GetSpeedY(WarplaneEnemy.class, Resources.GameInstance.GetMillisecond(), Resources.GameInstance.GetScore()));
	}

	@Override
	public List<? extends Bullet> GetShots()
	{
		return List.of();
	}
}
