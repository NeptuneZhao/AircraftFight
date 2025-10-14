package com.halfcooler.flying.prop;

import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.warplane.Warplane;
import com.halfcooler.flying.warplane.WarplaneHero;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/// <summary>
/// 测试通过
/// </summary>

public class PropBullet extends Prop
{
	public static int ActiveThread = 0;

	/// 种类 0 并行, 切换到 1 则为环形
	public static int ActiveType = 0;

	public PropBullet(Warplane plane)
	{
		super(plane);
	}

	public PropBullet(Warplane plane, int x)
	{
		super(plane, x);
	}

	@Override
	public void TakeEffect(WarplaneHero hero, List<Warplane> enemies, List<Bullet> bullets)
	{
		ActiveType = 0;
		effect1();
	}

	private static final int maxActiveThread = 8;

	/// 线程池, 固定大小, 限制最大并行子弹数就是 + 1
	/// 思路来源: ChatGPT
	private static final ExecutorService es = new ThreadPoolExecutor(
		maxActiveThread, // Core pool size
		maxActiveThread, // Max pool size
		5001L, // Keep-alive time
		TimeUnit.MILLISECONDS, // Time unit for keep-alive
		new ArrayBlockingQueue<>(maxActiveThread), // Work queue with a capacity of 10
		new ThreadPoolExecutor.DiscardPolicy()); // Rejection policy (discard new tasks when the queue is full)

	/// <summary>
	/// 火力道具备选效果 1<br>
	/// 持续 5 秒增加 1 并行子弹<br>
	/// 可叠加<br>
	/// </summary>
	protected void effect1()
	{
		es.execute(() ->
		{
			ActiveThread++;
			try
			{
				Thread.sleep(5000);
			}
			catch (InterruptedException e)
			{
				throw new RuntimeException(e);
			}
			ActiveThread--;
		});
	}
}
