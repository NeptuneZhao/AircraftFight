package com.halfcooler.music;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/// Debug Pass on 2025/09/10
public class MusicThread extends Thread
{
    private final File musicPath;
    private final boolean loop;
    private Clip clip;

	public static boolean IsMusicOn;

    private MusicThread(File path, boolean loop)
    {
        this.musicPath = path;
        this.loop = loop;
        setName("MusicThread-" + path.getName());
        setDaemon(true);
    }

    @Override
    public void run()
    {
        if (musicPath == null || !musicPath.exists() || !musicPath.isFile())
		{
            System.err.println("File not found: " + musicPath);
            return;
        }

        AudioInputStream ais = null;
        try
        {
            ais = AudioSystem.getAudioInputStream(musicPath);
            AudioFormat baseFormat = ais.getFormat();

			// 可能有用 转换为 PCM
            AudioFormat targetFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false
            );
			
            clip = (Clip)AudioSystem.getLine( new DataLine.Info(Clip.class, targetFormat) );
            clip.open( AudioSystem.getAudioInputStream(targetFormat, ais) );

            final CountDownLatch finishedLatch = new CountDownLatch(1);

            clip.addLineListener(event ->
            {
	            // Stop 事件可能由播放完成或手动停止触发
                if ((event.getType() == LineEvent.Type.STOP && !clip.isRunning()) || event.getType() == LineEvent.Type.CLOSE)
                    finishedLatch.countDown();
            });

            if (loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
			else clip.start();

	        finishedLatch.await();

        }
		catch (UnsupportedAudioFileException e)
		{
            System.err.println("Not supported music format: " + e.getMessage());
        }
		catch (LineUnavailableException e)
        {
            System.err.println("Music is not available" + e.getMessage());
        }
		catch (IOException e)
		{
            System.err.println("Read music error: " + e.getMessage());
        }
		catch (InterruptedException e)
		{
            // Thread interrupted, and goes finally
            Thread.currentThread().interrupt();
        }
		finally
        {
            cleanUp();
            try
            {
                if (ais != null) ais.close();
            }
			catch (IOException ignored) { }
        }
    }

	// Safe stop playing
    private void stopPlaying()
    {
        if (clip != null)
		{
            if (clip.isRunning()) clip.stop();
			clip.flush();
            clip.close();
			clip = null;
        }
        this.interrupt();
    }

    private void cleanUp()
    {
        if (clip != null) clip.close();
    }

	public static final MusicThread BackgroundMusicInstance = new MusicThread(new File("src/main/resources/bgm.wav"), true);
	public static final MusicThread GameOverMusicInstance = new MusicThread(new File("src/main/resources/game_over.wav"), false);

	public static void MusicOn(MusicThread instance)
	{
		if (IsMusicOn) instance.start();
	}

	/// @see MusicThread#stopPlaying()
	public static void MusicOff(MusicThread instance)
	{
		if (instance.isAlive()) instance.stopPlaying();
	}

}
