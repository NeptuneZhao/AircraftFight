package com.halfcooler.utils;

import java.util.stream.IntStream;

public class Homework
{
	public static boolean containsChar(String str, char c)
	{
		/*  for (int i = 0; i < str.length(); i++)
				if (str.charAt(i) == c) return true;
			return false;
		*/
		return IntStream.range(0, str.length()).anyMatch(i -> str.charAt(i) == c);
	}

	public static boolean containsChar(String str, char c1, char c2)
	{
		return containsChar(str, c1) && containsChar(str, c2);
	}
}
