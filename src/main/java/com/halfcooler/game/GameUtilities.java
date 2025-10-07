package com.halfcooler.game;

import com.halfcooler.flying.Flying;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
public final class GameUtilities
{
	public static void DrawImage(Graphics g, List<? extends Flying> flyingList)
	{
		if (flyingList.isEmpty())
			return;

		for (Flying flying : flyingList)
		{
			BufferedImage image = flying.GetImage();
			assert image != null : flyingList.getClass().getName() + " image is null.";
			g.drawImage(image, flying.GetX() - image.getWidth() / 2, flying.GetY() - image.getHeight() / 2, null);
		}
	}

	public static void Write(Graphics g, String message, Color color, Font font, int x, int y)
	{
		g.setColor(color);
		g.setFont(font);
		g.drawString(message, x, y);
	}
}
