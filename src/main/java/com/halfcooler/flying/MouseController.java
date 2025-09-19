package com.halfcooler.flying;

import com.halfcooler.Program;
import com.halfcooler.flying.warplane.WarplaneHero;
import com.halfcooler.game.Game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseController
{
	private final Game game;
	private final WarplaneHero warPlaneHero;
	private final MouseAdapter mouseAdapter;

	public MouseController(Game game, WarplaneHero warPlaneHero)
	{
		this.game = game;
		this.warPlaneHero = warPlaneHero;

		mouseAdapter = new MouseAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				super.mouseDragged(e);
				int x = e.getX();
				int y = e.getY();

				if (x < 0 || x > Program.WIDTH || y < 0 || y > Program.HEIGHT)
					return;

				warPlaneHero.setLocation(x, y);
			}
		};

		game.addMouseListener(mouseAdapter);
		game.addMouseMotionListener(mouseAdapter);
	}
}
