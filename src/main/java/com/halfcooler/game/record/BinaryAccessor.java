package com.halfcooler.game.record;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.halfcooler.game.record.Converter.BytesToInt;
import static com.halfcooler.game.record.Converter.BytesToString;
import static java.util.Arrays.copyOfRange;

public final class BinaryAccessor implements IDataAccessor
{
	/// 定长二进制文件
	private static final File binaryFile = new File("records.dat");

	public List<Record> LoadRecords()
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

				records.add(this.readThis(recordBytes));
			}
			return records;
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}

	public void SaveRecord(Record r)
	{
		try (var in = new FileOutputStream(binaryFile, true))
		{
			in.write(this.writeThis(r));
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public void DeleteRecordByIndex(int index)
	{
		List<Record> records = this.LoadRecords();
		if (index < 0 || index >= records.size())
			return;
		records.remove(index);
		try (var out = new FileOutputStream(binaryFile, false))
		{
			for (var record : records)
				out.write(this.writeThis(record));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private Record readThis(byte[] bytes)
	{
		if (bytes.length != 128)
			throw new IllegalArgumentException("Invalid byte array length for Record: " + bytes.length);

		int ptr = 0;
		String uuidStr = BytesToString(copyOfRange(bytes, ptr, ptr + 36));
		ptr += 36;
		String name = BytesToString(copyOfRange(bytes, ptr, ptr + 56));
		ptr += 56;
		int df = BytesToInt(copyOfRange(bytes, ptr, ptr + 4));
		ptr += 4;
		int time = BytesToInt(copyOfRange(bytes, ptr, ptr + 4));
		ptr += 4;
		int score = BytesToInt(copyOfRange(bytes, ptr, ptr + 4));
		ptr += 4;
		int damage = BytesToInt(copyOfRange(bytes, ptr, ptr + 4));
		ptr += 4;
		int total = BytesToInt(copyOfRange(bytes, ptr, ptr + 4));
		ptr += 4;
		int enemy = BytesToInt(copyOfRange(bytes, ptr, ptr + 4));
		ptr += 4;
		int elite = BytesToInt(copyOfRange(bytes, ptr, ptr + 4));
		ptr += 4;
		int plus = BytesToInt(copyOfRange(bytes, ptr, ptr + 4));
		ptr += 4;
		int boss = BytesToInt(copyOfRange(bytes, ptr, ptr + 4));

		return new Record(UUID.fromString(uuidStr), name, df, time, score, damage, total, enemy, elite, plus, boss);
	}

	private byte[] writeThis(Record r)
	{
		byte[] uuidBytes = Converter.StringToFixedBytes(r.uuid().toString(), 36);
		byte[] nameBytes = Converter.StringToFixedBytes(r.name(), 56);
		byte[] dfBytes = Converter.IntToBytes(r.difficulty());
		byte[] timeBytes = Converter.IntToBytes(r.time());
		byte[] scoreBytes = Converter.IntToBytes(r.score());
		byte[] damageBytes = Converter.IntToBytes(r.damage());
		byte[] totalBytes = Converter.IntToBytes(r.total());
		byte[] enemyBytes = Converter.IntToBytes(r.enemy());
		byte[] eliteBytes = Converter.IntToBytes(r.elite());
		byte[] plusBytes = Converter.IntToBytes(r.plus());
		byte[] bossBytes = Converter.IntToBytes(r.boss());
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
}
