package com.halfcooler.flying.prop;

import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.warplane.Warplane;
import com.halfcooler.flying.warplane.WarplaneBoss;
import com.halfcooler.flying.warplane.WarplaneHero;
import com.halfcooler.music.MusicPlayer;

import java.util.List;

public class PropBomb extends Prop
{
	public PropBomb(Warplane plane, int x)
	{
		super(plane, x);
	}

	@Override
	public void TakeEffect(WarplaneHero hero, List<Warplane> enemies, List<Bullet> bullets)
	{
		MusicPlayer.PlayBombExplosionMusic();

		for (Warplane enemy : enemies)
		{
			if (!(enemy instanceof WarplaneBoss))
				enemy.SetVanish();
		}

		for (Bullet bullet : bullets)
			bullet.SetVanish();
	}
}
