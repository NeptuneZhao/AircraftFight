package com.halfcooler.flying.bullet.type;

import com.halfcooler.flying.bullet.BulletEnemy;
import com.halfcooler.flying.warplane.Warplane;
import com.halfcooler.flying.warplane.WarplanePlus;

import java.util.List;


public class BulletTypePlus implements IBulletType
{
	public List<BulletEnemy> Shoots(Warplane plus)
	{
		return BulletEnemy.ScatterInstance( (WarplanePlus) plus );
	}
}
