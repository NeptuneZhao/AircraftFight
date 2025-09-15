package com.halfcooler.game;

import com.halfcooler.music.MusicThread;

import javax.swing.*;

public class Game extends JPanel
{
	public static final String MUSIC_PATH = "resources/bgm.wav";

	public static JPanel StartGame(int difficulty, boolean isMusic)
	{
		MusicThread.musicOn(isMusic);

		return switch (difficulty)
		{
			case 0 -> new GameEasy();
			case 1 -> new GameNormal();
			case 2 -> new GameHard();

			default -> throw new IllegalArgumentException("Invalid difficulty");
		};
	}
}
