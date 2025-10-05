package com.halfcooler.flying.bullet.type;

import com.halfcooler.flying.bullet.BulletEnemy;
import com.halfcooler.flying.warplane.Warplane;
import com.halfcooler.flying.warplane.WarplaneBoss;

import java.util.List;

public class BulletTypeBoss implements BulletType
{
	@Override
	public List<BulletEnemy> Shoots(Warplane enemy)
	{
		return BulletEnemy.RingInstance( (WarplaneBoss) enemy );
	}
}
