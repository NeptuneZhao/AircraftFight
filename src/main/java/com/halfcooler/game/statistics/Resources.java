package com.halfcooler.game.statistics;

import com.halfcooler.game.Game;
import com.halfcooler.game.record.BinaryAccessor;
import com.halfcooler.game.record.IDataAccessor;

/// 存放了游戏运行时的全局资源, 仅包含 Program 运行
public final class Resources
{
	public static final Object MainLock = new Object();
	public static final int WIDTH = 480;
	public static final int HEIGHT = 640;
	public static Game GameInstance = null;
	public static final IDataAccessor Recorder = new BinaryAccessor();
}
