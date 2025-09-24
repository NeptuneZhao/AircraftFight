package com.halfcooler.game.record;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecordImplement implements RecordExport
{
	private final List<Record> records;
	private final File file = new File("data/gameRecords.txt");

	public RecordImplement() throws IOException
	{
		records = new ArrayList<>();
		if (!file.exists())
		{
			if (!file.createNewFile())
				throw new IOException("Cannot create file: " + file.getAbsolutePath());
		}

		FileInputStream fip = new FileInputStream(file);
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

	public void WriteToFile() throws IOException
	{
		FileOutputStream fop = new FileOutputStream(file);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fop));
		if (records != null) {
			for (Record gameRecord : records) {
				String lineToFile;
				lineToFile = gameRecord.userName() + ',' + gameRecord.time() + ',' + gameRecord.score() + ',' + gameRecord.difficulty() + '\n';
				writer.write(lineToFile);
			}
		}
		writer.close();
		fop.close();
	}

	@Override
	public List<Record> GetAllRecords()
	{
		return this.records;
	}

	@Override
	public void AddRecord(Record record)
	{
		this.records.add(record);
	}

	@Override
	public void DeleteRecord(Record record)
	{
		this.records.remove(record);
	}

}
