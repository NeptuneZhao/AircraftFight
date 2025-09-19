package com.halfcooler.utils;

import com.halfcooler.Program;
import com.halfcooler.flying.warplane.WarplaneHero;
import com.halfcooler.game.Game;

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

				if (x < 0 || x > Program.WIDTH || y < 0 || y > Program.HEIGHT)
					return;

				warplaneHero.setLocation(x, y);
			}
		};

		game.addMouseListener(mouseAdapter);
		game.addMouseMotionListener(mouseAdapter);
	}
}
