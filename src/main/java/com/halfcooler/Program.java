package com.halfcooler;

import com.halfcooler.game.Game;
import com.halfcooler.menu.StartsMenu;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Program
{
	public static final Object MainLock = new Object();
	public static final int WIDTH = 480, HEIGHT = 640;

	public static void main(String[] args)
	{
		// Debug 模式下自动更新版本号
		onBuilding();

		// 阶段 1: 开始菜单
		JFrame frame = new JFrame("StartMenu");
		frame.setResizable(false);

		StartsMenu startsMenu = new StartsMenu();
		JPanel startPanel = startsMenu.GetPanel();
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		frame.setContentPane(startPanel);
		frame.setBounds(100, 100, WIDTH, HEIGHT);
		frame.setVisible(true);

		// 同步锁借鉴自 https://github.com/ZSTIH/2022_HITSZ_IOSC-Labs
		synchronized (MainLock)
		{
			while (startPanel.isVisible())
			{
				try
				{
					MainLock.wait();
				}
				catch (InterruptedException e)
				{
					System.err.println("Main thread interrupted: " + e.getMessage());
				}
			}
		}
		frame.remove(startPanel);

		// 阶段 2: 游戏主循环
		Game gamePanel = Game.StartGame(startsMenu.GetDifficulty(), startsMenu.IsMusicOn(), startsMenu.GetFps());
		frame.setContentPane(gamePanel);
		// Title 设置为 "Game - [Difficulty] - [Music On/Off], Version: a.b.c.d"
		// 版本号从 version.properties 中读取
		Properties props = new Properties();
		try (FileInputStream fis = new FileInputStream("version.properties"))
		{
			props.load(fis);
		}
		catch (Exception e)
		{
			System.err.println("Failed to load version properties: " + e.getMessage());
		}
		String version = props.getProperty("major") + "." + props.getProperty("minor") + "." + props.getProperty("build") + "." + props.getProperty("patch");
		frame.setTitle(String.format("Game [%s], Music %s, Ver: %s", startsMenu.GetDifficulty(), startsMenu.IsMusicOn() ? "On" : "Off", version));
		frame.setVisible(true);
		gamePanel.Loop();

		// 退出阶段
		synchronized (MainLock)
		{
			// 等待英雄机死
			try
			{
				MainLock.wait();
			}
			catch (InterruptedException e)
			{
				System.err.println("Main thread interrupted: " + e.getMessage());
			}
			finally
			{
				System.out.println("Game over test pass.");
			}
		}
	}

	private static void onBuilding()
	{
		Properties props = new Properties();
		try (FileInputStream fis = new FileInputStream("version.properties"))
		{
			props.load(fis);
		}
		catch (Exception e)
		{
			System.err.println("Failed to load version properties: " + e.getMessage());
			return;
		}

		int build = Integer.parseInt(props.getProperty("build"));
		int patch = Integer.parseInt(props.getProperty("patch")) + 1;

		if (patch > 9)
		{
			patch = 0;
			build++;
		}

		props.setProperty("build", Integer.toString(build));
		props.setProperty("patch", Integer.toString(patch));

		try (FileOutputStream fos = new FileOutputStream("version.properties"))
		{
			props.store(fos, "Auto-updated build and patch number");
		}
		catch (Exception e)
		{
			System.err.println("Failed to update version properties: " + e.getMessage());
		}

	}
}