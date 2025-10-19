package com.halfcooler.game.record;

import com.halfcooler.game.utils.ResourcesBundler;

import java.util.UUID;

/// 36 字节 UUID + 56 字节名字 + 4 字节难度 + 4 字节时间 + 4 字节分数 + 4 字节伤害 + 4 字节全部 + 4 字节击杀普通 + 4 字节击杀精英 + 4 字节击杀超级 + 4 字节击杀 Boss = 128 字节
public record Record(UUID uuid, String name, int difficulty, int time, float score, int damage, int total, int enemy, int elite, int plus, int boss)
{
	private static final ResourcesBundler rb = new ResourcesBundler();

	public String[] ToString()
	{
		return new String[]
		{
			this.uuid.toString(),
			this.name,
			switch (difficulty)
			{
				case 0 -> rb.GetMessage("mode.easy");
				case 1 -> rb.GetMessage("mode.normal");
				case 2 -> rb.GetMessage("mode.hard");
				default -> throw new IllegalStateException("Unexpected value: " + difficulty);
			},
			String.format("%02d:%02d", this.time / 1000 / 60, (this.time / 1000) % 60),
			String.valueOf(this.score),
			String.valueOf(this.damage),
			String.valueOf(this.total),
			String.valueOf(this.enemy),
			String.valueOf(this.elite),
			String.valueOf(this.plus),
			String.valueOf(this.boss)
		};
	}

}