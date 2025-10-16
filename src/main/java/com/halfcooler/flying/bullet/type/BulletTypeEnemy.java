package com.halfcooler.flying.bullet.type;

import com.halfcooler.flying.bullet.BulletEnemy;
import com.halfcooler.flying.warplane.Warplane;

import java.util.List;

public class BulletTypeEnemy implements IBulletType
{
	public List<BulletEnemy> Shoots(Warplane ignored)
	{
		return List.of();
	}
}
