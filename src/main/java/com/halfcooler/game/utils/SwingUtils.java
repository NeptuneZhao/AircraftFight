package com.halfcooler.game.utils;

import com.halfcooler.flying.Flying;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public final class SwingUtils
{
	public static void DrawImage(Graphics g, List<? extends Flying> flyingList)
	{
		if (flyingList.isEmpty())
			return;

		for (Flying flying : flyingList)
		{
			BufferedImage image = flying.GetImage();
			if (image == null) throw new NullPointerException(flyingList.getClass().getName() + " image is null.");

			g.drawImage(image, flying.GetX() - image.getWidth() / 2, flying.GetY() - image.getHeight() / 2, null);
		}
	}

	public static void Write(Graphics g, String message, Color color, Font font, int x, int y)
	{
		g.setColor(color);
		g.setFont(font);
		g.drawString(message, x, y);
	}

	private record TextMnemonic(String text, boolean hasMnemonic, char mnemonic, int mnemonicIndex) { }

	private static TextMnemonic parseTextAndMnemonic(String text)
	{
		StringBuilder result = new StringBuilder();
		boolean haveMnemonic = false;
		char mnemonic = '\0';
		int mnemonicIndex = -1;
		for (int i = 0; i < text.length(); i++)
		{
			if (text.charAt(i) == '&')
			{
				i++;
				if (i == text.length()) break;
				if (!haveMnemonic && text.charAt(i) != '&')
				{
					haveMnemonic = true;
					mnemonic = text.charAt(i);
					mnemonicIndex = result.length();
				}
			}
			result.append(text.charAt(i));
		}
		return new TextMnemonic(result.toString(), haveMnemonic, mnemonic, mnemonicIndex);
	}

	public static void LoadLabelText(JLabel component, String text)
	{
		TextMnemonic tm = parseTextAndMnemonic(text);
		component.setText(tm.text);
		if (tm.hasMnemonic)
		{
			component.setDisplayedMnemonic(tm.mnemonic);
			component.setDisplayedMnemonicIndex(tm.mnemonicIndex);
		}
	}

	public static void LoadButtonText(AbstractButton component, String text)
	{
		TextMnemonic tm = parseTextAndMnemonic(text);
		component.setText(tm.text);
		if (tm.hasMnemonic)
		{
			component.setMnemonic(tm.mnemonic);
			component.setDisplayedMnemonicIndex(tm.mnemonicIndex);
		}
	}

}
