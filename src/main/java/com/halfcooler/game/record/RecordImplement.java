package com.halfcooler.game.record;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecordImplement implements RecordExport
{
	private final List<Record> records;
	private final File f = new File("data/gameRecords.txt");

	public RecordImplement() throws IOException
	{
		records = new ArrayList<>();
		if (!f.exists())
		{
			if (!f.createNewFile())
				throw new IOException("Cannot create file: " + f.getAbsolutePath());
		}

		FileInputStream fip = new FileInputStream(f);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fip));

		String[] lineFromFile;
		while (reader.ready())
		{
			lineFromFile = reader.readLine().split(",");
			records.add(new Record(lineFromFile[0], lineFromFile[1], Integer.parseInt(lineFromFile[2]), lineFromFile[3]));
		}
		fip.close();
		reader.close();
	}

	public void writeToFile() throws IOException
	{
		FileOutputStream fop = new FileOutputStream(f);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fop));
		if (records != null) {
			for (Record gameRecord : records) {
				String lineToFile;
				lineToFile = gameRecord.getUserName() + ',' + gameRecord.getTime() + ',' + gameRecord.getScore() + ',' + gameRecord.getDifficulty() + '\n';
				writer.write(lineToFile);
			}
		}
		writer.close();
		fop.close();
	}

	@Override
	public List<Record> getAllRecords()
	{
		return this.records;
	}

	@Override
	public void addRecord(Record record)
	{
		this.records.add(record);
	}

	@Override
	public void deleteRecord(Record record)
	{
		this.records.remove(record);
	}

}
