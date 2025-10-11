package com.halfcooler.game.record;

import java.util.Arrays;

/// 32字节名字 + 4 字节难度 + 4 字节时间 + 4 字节分数 + 4 字节伤害 + 4 字节击杀普通 + 4 字节击杀精英 + 4 字节击杀超级 + 4 字节击杀Boss = 64 字节
public record Record(String name, int difficulty, int time, int score, int damage, int enemy, int elite, int plus, int boss)
{
	/// 接收一次游戏的结果
	public static Record Read(byte[] bytes)
	{
		String name = Converter.BytesToString(Arrays.copyOfRange(bytes, 0, 32));
		int df = Converter.BytesToInt(Arrays.copyOfRange(bytes, 32, 36));
		int time = Converter.BytesToInt(Arrays.copyOfRange(bytes, 36, 40));
		int score = Converter.BytesToInt(Arrays.copyOfRange(bytes, 40, 44));
		int damage = Converter.BytesToInt(Arrays.copyOfRange(bytes, 44, 48));
		int enemy = Converter.BytesToInt(Arrays.copyOfRange(bytes, 48, 52));
		int elite = Converter.BytesToInt(Arrays.copyOfRange(bytes, 52, 56));
		int plus = Converter.BytesToInt(Arrays.copyOfRange(bytes, 56, 60));
		int boss = Converter.BytesToInt(Arrays.copyOfRange(bytes, 60, 64));
		return new Record(name, df, time, score, damage, enemy, elite, plus, boss);
	}

	public byte[] Save()
	{
		byte[] nameBytes = Converter.StringToFixedBytes(name, 32);
		byte[] dfBytes = Converter.IntToBytes(difficulty);
		byte[] scoreBytes = Converter.IntToBytes(score);
		byte[] timeBytes = Converter.IntToBytes(time);
		byte[] damageBytes = Converter.IntToBytes(damage);
		byte[] enemyBytes = Converter.IntToBytes(enemy);
		byte[] eliteBytes = Converter.IntToBytes(elite);
		byte[] plusBytes = Converter.IntToBytes(plus);
		byte[] bossBytes = Converter.IntToBytes(boss);
		byte[] recordBytes = new byte[64];
		System.arraycopy(nameBytes, 0, recordBytes, 0, 32);
		System.arraycopy(dfBytes, 0, recordBytes, 32, 4);
		System.arraycopy(scoreBytes, 0, recordBytes, 36, 4);
		System.arraycopy(timeBytes, 0, recordBytes, 40, 4);
		System.arraycopy(damageBytes, 0, recordBytes, 44, 4);
		System.arraycopy(enemyBytes, 0, recordBytes, 48, 4);
		System.arraycopy(eliteBytes, 0, recordBytes, 52, 4);
		System.arraycopy(plusBytes, 0, recordBytes, 56, 4);
		System.arraycopy(bossBytes, 0, recordBytes, 60, 4);
		return recordBytes;
	}
}