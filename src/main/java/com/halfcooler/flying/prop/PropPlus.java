package com.halfcooler.flying.prop;

import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.warplane.Warplane;
import com.halfcooler.flying.warplane.WarplaneHero;
import com.halfcooler.music.MusicPlayer;

import java.util.List;

public class PropPlus extends PropBullet
{
	public PropPlus(Warplane plane)
	{
		super(plane);
	}

	public PropPlus(Warplane plane, int x)
	{
		super(plane, x);
	}

	@Override
	public void TakeEffect(WarplaneHero hero, List<Warplane> enemies, List<Bullet> bullets)
	{
		MusicPlayer.PlayGetSupplyMusic();
		ActiveType = 1;
		effect1();
	}
}
