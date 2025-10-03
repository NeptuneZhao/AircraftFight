package com.halfcooler.music;

import java.io.File;

public final class MusicPlayer
{
	private MusicPlayer() { }

	public static boolean IsMusicOn;
	private static MusicThread CurrentBgm, CurrentBossBgm;
	private static boolean playingBgmFlag, playingBossFlag;

	/// 嗨, 大家好! 嗨, 家人们! 如果你需要我, 咱们只是隔着一个屏幕而已
	public static void PlayBgm(boolean on)
	{
		IsMusicOn = on;
		if (!IsMusicOn) return;

		playingBgmFlag = true;

		// Start a new thread to manage background music
		new Thread(() ->
		{
			while (playingBgmFlag)
			{
				double rand = Math.random();
				if (rand < 0.33) CurrentBgm = new MusicThread(new File("src/main/resources/bgm/bgm_1.wav"));
				else if (rand < 0.66) CurrentBgm = new MusicThread(new File("src/main/resources/bgm/bgm_2.wav"));
				else CurrentBgm = new MusicThread(new File("src/main/resources/bgm/bgm_3.wav"));

				CurrentBgm.start();
				try
				{
					CurrentBgm.join();
				}
				catch (InterruptedException ignored) { }
				finally
				{
					CurrentBgm.musicOff();
				}
			}
		}).start();
	}

	public static void PlayGameOver()
	{
		if (!IsMusicOn) return;

		playingBgmFlag = false;

		if (CurrentBgm != null)
			CurrentBgm.musicOff();

		new MusicThread(new File("src/main/resources/bgm/game_over.wav")).start();
	}

	public static void PlayBossMusic()
	{
		if (!IsMusicOn) return;

		playingBossFlag = true;

		// Stop current BGM if any
		playingBgmFlag = false;
		if (CurrentBgm != null) CurrentBgm.musicOff();

		// Start a new thread to manage background music
		new Thread(() ->
		{
			while (playingBossFlag)
			{
				double rand = Math.random();
				if (rand < 0.5) CurrentBossBgm = new MusicThread(new File("src/main/resources/bgm/boss_1.wav"));
				else CurrentBossBgm = new MusicThread(new File("src/main/resources/bgm/boss_2.wav"));

				CurrentBossBgm.start();
				try
				{
					CurrentBossBgm.join();
				}
				catch (InterruptedException ignored) { }
				finally
				{
					CurrentBossBgm.musicOff();
				}
			}
		}).start();
	}

	public static void PlayBossKilledMusic()
	{
		if (!IsMusicOn) return;

		playingBossFlag = false;

		if (CurrentBossBgm != null) CurrentBossBgm.musicOff();

		if (Math.random() < 0.5) CurrentBossBgm = new MusicThread(new File("src/main/resources/bgm/boss_killed_1.wav"));
		else CurrentBossBgm = new MusicThread(new File("src/main/resources/bgm/boss_killed_2.wav"));

		CurrentBossBgm.start();
		try
		{
			CurrentBossBgm.join();
		}
		catch (InterruptedException ignored) { }
		finally
		{
			CurrentBossBgm.musicOff();
			PlayBgm(IsMusicOn);
		}
	}
}
