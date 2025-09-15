package com.halfcooler;

import com.halfcooler.game.Game;
import com.halfcooler.menu.StartsMenu;
import com.halfcooler.music.MusicThread;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class Program
{
	public static final Object MainLock = new Object();

	public static void main(String[] args)
	{
		// 阶段 1: 开始菜单
		JFrame frame = new JFrame("StartMenu");
		frame.setResizable(false);

		StartsMenu startsMenu = new StartsMenu();
		JPanel startPanel = startsMenu.getPanel();
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		frame.setContentPane(startPanel);
		frame.setBounds(100, 100, 480, 640);
		frame.setVisible(true);

		// Start waiting, a stupid method to change the menu to the game loop.
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
		JPanel gamePanel = Game.StartGame(startsMenu.getDifficulty(), startsMenu.isMusicOn());

		// 退出阶段
		MusicThread.musicOff();
	}
}