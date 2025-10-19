package com.halfcooler.music;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class MusicThread extends Thread
{
    private final File music;
    private Clip clip;

    public MusicThread(File music)
    {
        this.music = music;
        setDaemon(true);
    }

    @Override
    public void run()
    {
        if (music == null || !music.exists() || !music.isFile())
		{
            System.err.println("File not found: " + music);
            return;
        }

        AudioInputStream ais = null;
        try
        {
            ais = AudioSystem.getAudioInputStream(music);
            AudioFormat baseFormat = ais.getFormat();

			// 转换为 PCM
            AudioFormat targetFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false);
			
            clip = (Clip) AudioSystem.getLine( new DataLine.Info(Clip.class, targetFormat) );
            clip.open( AudioSystem.getAudioInputStream(targetFormat, ais) );

            final CountDownLatch finishedLatch = new CountDownLatch(1);

            clip.addLineListener(event ->
            {
	            // Stop 事件可能由播放完成或手动停止触发
                if ((event.getType() == LineEvent.Type.STOP && !clip.isRunning()) || event.getType() == LineEvent.Type.CLOSE)
                    finishedLatch.countDown();
            });

			clip.start();
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
            this.cleanUp();
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

	/// @see MusicThread#stopPlaying()
	public void musicOff()
	{
		if (this.isAlive()) this.stopPlaying();
	}


}
