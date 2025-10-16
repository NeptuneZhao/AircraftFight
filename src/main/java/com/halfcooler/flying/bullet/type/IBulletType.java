package com.halfcooler.flying.bullet.type;

import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.warplane.Warplane;

import java.util.List;

public interface IBulletType
{
	List<? extends Bullet> Shoots(Warplane enemy);
}
