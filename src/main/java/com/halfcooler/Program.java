package com.halfcooler;

import com.halfcooler.game.Game;
import com.halfcooler.menu.StartsMenu;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Program
{
	public static final Object MainLock = new Object();
	public static final int WIDTH = 480, HEIGHT = 640;

	public static void main(String[] args)
	{
		// 阶段 1: 开始菜单
		JFrame frame = new JFrame("StartMenu");
		frame.setResizable(false);

		StartsMenu startsMenu = new StartsMenu();
		JPanel startPanel = startsMenu.getPanel();
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		frame.setContentPane(startPanel);
		frame.setBounds(100, 100, WIDTH, HEIGHT);
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
		Game gamePanel = Game.StartGame(startsMenu.getDifficulty(), startsMenu.isMusicOn());
		frame.setContentPane(gamePanel);
		frame.setTitle("Game");
		frame.setVisible(true);
		gamePanel.Loop();

		// 退出阶段
		// MusicThread.musicOff();
	}
}