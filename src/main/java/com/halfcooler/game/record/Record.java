package com.halfcooler.game.record;

import com.halfcooler.utils.ResourcesBundler;

import java.util.UUID;

import static com.halfcooler.game.record.Converter.BytesToInt;
import static com.halfcooler.game.record.Converter.BytesToString;
import static java.util.Arrays.copyOfRange;

/// 36 字节 UUID + 56 字节名字 + 4 字节难度 + 4 字节时间 + 4 字节分数 + 4 字节伤害 + 4 字节全部 + 4 字节击杀普通 + 4 字节击杀精英 + 4 字节击杀超级 + 4 字节击杀Boss = 128 字节
public record Record(UUID uuid, String name, int difficulty, int time, int score, int damage, int total, int enemy, int elite, int plus, int boss)
{
	private static final ResourcesBundler rb = new ResourcesBundler();

	/// 接收一次游戏的结果<br>
	/// 我诚心忏悔, 我使用字符串作为 UUID 的存储格式
	public static Record Read(byte[] bytes)
	{
		if (bytes.length != 128)
			throw new IllegalArgumentException("Invalid byte array length for Record: " + bytes.length);
		int ptr = 0;
		String uuidStr = BytesToString(copyOfRange(bytes, ptr, ptr + 36)); ptr += 36;
		String name = BytesToString(copyOfRange(bytes, ptr, ptr + 56)); ptr += 56;
		int df = BytesToInt(copyOfRange(bytes, ptr, ptr + 4)); ptr += 4;
		int time = BytesToInt(copyOfRange(bytes, ptr, ptr + 4)); ptr += 4;
		int score = BytesToInt(copyOfRange(bytes, ptr, ptr + 4)); ptr += 4;
		int damage = BytesToInt(copyOfRange(bytes, ptr, ptr + 4)); ptr += 4;
		int total = BytesToInt(copyOfRange(bytes, ptr, ptr + 4)); ptr += 4;
		int enemy = BytesToInt(copyOfRange(bytes, ptr, ptr + 4)); ptr += 4;
		int elite = BytesToInt(copyOfRange(bytes, ptr, ptr + 4)); ptr += 4;
		int plus = BytesToInt(copyOfRange(bytes, ptr, ptr + 4)); ptr += 4;
		int boss = BytesToInt(copyOfRange(bytes, ptr, ptr + 4));

		return new Record(UUID.fromString(uuidStr), name, df, time, score, damage, total, enemy, elite, plus, boss);
	}

	public byte[] Save()
	{
		byte[] uuidBytes = Converter.StringToFixedBytes(uuid.toString(), 36);
		byte[] nameBytes = Converter.StringToFixedBytes(name, 56);
		byte[] dfBytes = Converter.IntToBytes(difficulty);
		byte[] timeBytes = Converter.IntToBytes(time);
		byte[] scoreBytes = Converter.IntToBytes(score);
		byte[] damageBytes = Converter.IntToBytes(damage);
		byte[] totalBytes = Converter.IntToBytes(total);
		byte[] enemyBytes = Converter.IntToBytes(enemy);
		byte[] eliteBytes = Converter.IntToBytes(elite);
		byte[] plusBytes = Converter.IntToBytes(plus);
		byte[] bossBytes = Converter.IntToBytes(boss);
		byte[] recordBytes = new byte[128];

		int ptr = 0;
		System.arraycopy(uuidBytes, 0, recordBytes, ptr, 36); ptr += 36;
		System.arraycopy(nameBytes, 0, recordBytes, ptr, 56); ptr += 56;
		System.arraycopy(dfBytes, 0, recordBytes, ptr, 4); ptr += 4;
		System.arraycopy(timeBytes, 0, recordBytes, ptr, 4); ptr += 4;
		System.arraycopy(scoreBytes, 0, recordBytes, ptr, 4); ptr += 4;
		System.arraycopy(damageBytes, 0, recordBytes,ptr, 4); ptr += 4;
		System.arraycopy(totalBytes, 0, recordBytes, ptr, 4); ptr += 4;
		System.arraycopy(enemyBytes, 0, recordBytes, ptr, 4); ptr += 4;
		System.arraycopy(eliteBytes, 0, recordBytes, ptr, 4); ptr += 4;
		System.arraycopy(plusBytes, 0, recordBytes, ptr, 4); ptr += 4;
		System.arraycopy(bossBytes, 0, recordBytes, ptr, 4);
		return recordBytes;
	}

	public String[] Display()
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