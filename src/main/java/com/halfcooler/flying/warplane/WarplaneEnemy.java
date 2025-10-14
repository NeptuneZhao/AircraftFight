package com.halfcooler.flying.warplane;

import com.halfcooler.Program;
import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.bullet.type.BulletTypeEnemy;

import java.util.List;

public class WarplaneEnemy extends Warplane
{
	public WarplaneEnemy(int x, int y)
	{
		super(x, y, 0, Program.GameInstance.IntervalP.GetSpeedY(WarplaneEnemy.class, Program.GameInstance.GetMillisecond(), Program.GameInstance.GetScore()));
	}

	@Override
	public List<? extends Bullet> GetShots()
	{
		return new BulletTypeEnemy().Shoots();
	}
}
