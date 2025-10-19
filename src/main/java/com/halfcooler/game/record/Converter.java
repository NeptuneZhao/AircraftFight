package com.halfcooler.game.record;

import java.nio.charset.StandardCharsets;

public final class Converter
{
	public static byte[] StringToFixedBytes(String s, int length)
	{
		byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
		byte[] fixed = new byte[length];

		System.arraycopy(bytes, 0, fixed, 0, Math.min(bytes.length, length));
		return fixed;
	}

	public static String BytesToString(byte[] bytes)
	{
		int length = 0;
		while (length < bytes.length && bytes[length] != 0)
			length++;
		return new String(bytes, 0, length, StandardCharsets.UTF_8);
	}

	public static byte[] IntToBytes(int value)
	{
		byte[] bytes = new byte[4];
		for (int i = 0; i < 4; i++)
			bytes[i] = (byte) ((value >> (8 * (3 - i))) & 0xFF);
		return bytes;
	}

	public static int BytesToInt(byte[] bytes)
	{
		int value = 0;
		for (int i = 0; i < 4; i++)
			value |= (bytes[i] & 0xFF) << (8 * (3 - i));
		return value;
	}

	public static byte[] FloatToBytes(float value)
	{
		return IntToBytes(Float.floatToIntBits(value));
	}

	public static float BytesToFloat(byte[] bytes)
	{
		return Float.intBitsToFloat(BytesToInt(bytes));
	}
}
