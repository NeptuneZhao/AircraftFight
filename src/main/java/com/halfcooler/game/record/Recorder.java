package com.halfcooler.game.record;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Recorder
{
	/// 定长二进制文件
	private static final File binaryFile = new File("records.dat");

	public static List<Record> ReadBinaryRecord()
	{
		if (!binaryFile.exists())
			return new ArrayList<>();

		// 读取文件
		try (var in = new FileInputStream(binaryFile))
		{
			byte[] buffer = in.readAllBytes();
			int recordCount = buffer.length / 128;
			List<Record> records = new ArrayList<>(recordCount);
			for (int i = 0; i < recordCount; i++)
			{
				byte[] recordBytes = Arrays.copyOfRange(buffer, i * 128, (i + 1) * 128);
				records.add(Record.Read(recordBytes));
			}
			return records;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}

	public static void SaveBinaryRecord(Record r)
	{
		try (var in = new FileOutputStream(binaryFile, true))
		{
			in.write(r.Save());
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
