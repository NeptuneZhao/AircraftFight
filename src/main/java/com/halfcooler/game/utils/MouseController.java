package com.halfcooler.game.utils;

import com.halfcooler.flying.warplane.WarplaneHero;
import com.halfcooler.game.Game;
import com.halfcooler.game.statistics.Resources;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class MouseController
{
	public static void SetControl(Game game, WarplaneHero warplaneHero)
	{
		MouseAdapter mouseAdapter = new MouseAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				super.mouseDragged(e);
				int x = e.getX();
				int y = e.getY();

				if (x < 0 || x > Resources.WIDTH || y < 0 || y > Resources.HEIGHT)
					return;
				warplaneHero.SetLocation(x, y);
			}
		};

		game.addMouseListener(mouseAdapter);
		game.addMouseMotionListener(mouseAdapter);
	}
}
