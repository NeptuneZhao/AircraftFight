package com.halfcooler.utils;

import javax.swing.*;

public final class SwingUtilities
{
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
