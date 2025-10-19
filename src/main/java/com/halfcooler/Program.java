package com.halfcooler;

import com.halfcooler.flying.warplane.WarplaneHero;
import com.halfcooler.game.Game;
import com.halfcooler.game.statistics.Resources;
import com.halfcooler.menu.DeadDialog;
import com.halfcooler.menu.StartsMenu;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Program
{
	public static void main(String[] args)
	{
		// Debug 模式下自动更新版本号
		onBuilding();

		// 开始菜单
		JFrame frame = new JFrame("StartMenu");
		frame.setResizable(false);

		StartsMenu startsMenu = new StartsMenu();
		JPanel startPanel = startsMenu.GetPanel();
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		frame.setContentPane(startPanel);
		frame.setBounds(100, 100, Resources.WIDTH, Resources.HEIGHT);
		frame.setVisible(true);

		synchronized (Resources.MainLock)
		{
			while (startPanel.isVisible())
			{
				try
				{
					Resources.MainLock.wait();
				}
				catch (InterruptedException e)
				{
					System.err.println("Main thread interrupted: " + e.getMessage());
				}
			}
		}
		frame.remove(startPanel);

		// 游戏主循环
		Resources.GameInstance = Game.StartGame(startsMenu.GetDifficulty(), startsMenu.IsMusicOn(), startsMenu.GetFps());
		frame.setContentPane(Resources.GameInstance);

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
		Resources.GameInstance.Loop();

		// 退出阶段
		synchronized (Resources.MainLock)
		{
			// 等待英雄机死
			try
			{
				Resources.MainLock.wait();
			}
			catch (InterruptedException e)
			{
				System.err.println("Main thread interrupted: " + e.getMessage());
			}
		}

		DeadDialog dialog = new DeadDialog(WarplaneHero.Instance);
		dialog.pack();
		dialog.setVisible(true);
		System.exit(0);
	}

	/// 调试模式下自动更新版本号
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