package com.halfcooler.game;

import com.halfcooler.music.MusicThread;

import javax.swing.*;

public class Game extends JPanel
{
	public static JPanel StartGame(int difficulty, boolean isMusic)
	{
		MusicThread.musicOn(isMusic);
		System.out.println(isMusic);
		System.out.println("Music Started.");

		return switch (difficulty)
		{
			case 0 -> new GameEasy();
			case 1 -> new GameNormal();
			case 2 -> new GameHard();

			default -> throw new IllegalArgumentException("Invalid difficulty");
		};
	}
}
