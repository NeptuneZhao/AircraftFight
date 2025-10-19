package com.halfcooler.flying.warplane;

import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.bullet.BulletEnemy;
import com.halfcooler.game.statistics.Resources;

import java.util.List;

public class WarplaneElite extends Warplane
{
	public WarplaneElite(int x, int y, int time, int score)
	{
		super(x, y, 0, Resources.GameInstance.IntervalP.GetSpeedY(WarplaneElite.class, time, score));
	}

	@Override
	public List<? extends Bullet> GetShots()
	{
		return BulletEnemy.DirectInstance(this);
	}

}
