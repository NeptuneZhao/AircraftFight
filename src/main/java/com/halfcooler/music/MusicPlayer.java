package com.halfcooler.music;

import java.io.File;

/// 所有音乐播放均由 {@link MusicPlayer#IsMusicOn} 控制。
public final class MusicPlayer
{
	private MusicPlayer() { }

	public static boolean IsMusicOn;
	private static MusicThread CurrentBgm, CurrentBossBgm;
	private static boolean playingBgmFlag, playingBossFlag;

	/// 嗨, 大家好! 嗨, 家人们! 如果你需要我, 咱们只是隔着一个屏幕而已<br>
	/// 播放背景音乐。<br>
	/// 状态由 {@link MusicPlayer#playingBgmFlag} 控制。<br>
	/// 类中停止播放的方案: 先将标志位置 false, 然后调用 {@link MusicThread#musicOff()}。<br>
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
		playingBossFlag = false;

		if (CurrentBgm != null)
			CurrentBgm.musicOff();

		if (CurrentBossBgm != null)
			CurrentBossBgm.musicOff();

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

	/// 兼有停止 Boss 音乐和播放击败 Boss 音乐的功能。<br>
	/// 播放击败 Boss 音乐后，恢复背景音乐。<br>
	/// 状态由 {@link MusicPlayer#playingBossFlag} 控制。<br>
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
		catch (InterruptedException ie)
		{
			throw new RuntimeException(ie);
		}
		finally
		{
			CurrentBossBgm.musicOff();
			PlayBgm(IsMusicOn);
		}
	}

	public static void PlayBulletMusic()
	{
		if (!IsMusicOn) return;
		new MusicThread(new File("src/main/resources/bgm/bullet.wav")).start();
	}

	public static void PlayBulletHitMusic()
	{
		if (!IsMusicOn) return;
		new MusicThread(new File("src/main/resources/bgm/bullet_hit.wav")).start();
	}

	public static void PlayBombExplosionMusic()
	{
		if (!IsMusicOn) return;
		new MusicThread(new File("src/main/resources/bgm/bomb_explosion.wav")).start();
	}

	public static void PlayGetSupplyMusic()
	{
		if (!IsMusicOn) return;
		new MusicThread(new File("src/main/resources/bgm/get_supply.wav")).start();
	}
}
