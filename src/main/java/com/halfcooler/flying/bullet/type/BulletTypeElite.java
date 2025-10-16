package com.halfcooler.flying.bullet.type;

import com.halfcooler.flying.bullet.BulletEnemy;
import com.halfcooler.flying.warplane.Warplane;
import com.halfcooler.flying.warplane.WarplaneElite;

import java.util.List;

public class BulletTypeElite implements IBulletType
{
	public List<BulletEnemy> Shoots(Warplane elite)
	{
		return BulletEnemy.DirectInstance( (WarplaneElite) elite );
	}
}
