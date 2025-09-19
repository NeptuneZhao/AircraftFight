package com.halfcooler.game.record;

public class Record
{
	private final String userName;
	private final String time;
	private final int score;
	private final String difficulty;

	public Record(String userName, String time, int score, String difficulty)
	{
		this.userName = userName;
		this.time = time;
		this.score = score;
		this.difficulty = difficulty;
	}

	public String getUserName()
	{
		return this.userName;
	}

	public String getTime()
	{
		return this.time;
	}

	public int getScore()
	{
		return this.score;
	}

	public String getDifficulty()
	{
		return this.difficulty;
	}

}
