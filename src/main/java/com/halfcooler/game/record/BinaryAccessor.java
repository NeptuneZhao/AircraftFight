
package com.halfcooler.game.record;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.halfcooler.game.record.Converter.*;
import static java.util.Arrays.copyOfRange;

public final class BinaryAccessor implements IDataAccessor
{
	private static final File binaryFile = new File("records.dat");
	private static final int recordSize = 128;

	public List<Record> LoadRecords()
	{
		if (!binaryFile.exists())
			return new ArrayList<>();

		try (FileInputStream in = new FileInputStream(binaryFile))
		{
			byte[] buffer = in.readAllBytes();
			int recordCount = buffer.length / recordSize;
			List<Record> records = new ArrayList<>(recordCount);
			for (int i = 0; i < recordCount; i++)
			{
				byte[] recordBytes = copyOfRange(buffer, i * recordSize, (i + 1) * recordSize);
				records.add(this.readThis(recordBytes));
			}
			return records;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public void SaveRecord(Record r)
	{
		try (FileOutputStream out = new FileOutputStream(binaryFile, true))
		{
			out.write(this.writeThis(r));
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
		try (FileOutputStream out = new FileOutputStream(binaryFile, false))
		{
			for (Record record : records)
				out.write(this.writeThis(record));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private Record readThis(byte[] bytes)
	{
		if (bytes.length != recordSize)
			throw new IllegalArgumentException("Invalid byte array length for Record: " + bytes.length);

		Cursor c = new Cursor(bytes);
		String uuidStr = c.readFixedString(36);
		String name = c.readFixedString(56);
		int df = c.readInt();
		int time = c.readInt();
		float score = c.readFloat();
		int damage = c.readInt();
		int total = c.readInt();
		int enemy = c.readInt();
		int elite = c.readInt();
		int plus = c.readInt();
		int boss = c.readInt();

		return new Record(UUID.fromString(uuidStr), name, df, time, score, damage, total, enemy, elite, plus, boss);
	}

	private byte[] writeThis(Record r)
	{
		byte[] recordBytes = new byte[recordSize];
		Cursor c = new Cursor(recordBytes);

		c.write(StringToFixedBytes(r.uuid().toString(), 36));
		c.write(StringToFixedBytes(r.name(), 56));
		c.write(IntToBytes(r.difficulty()));
		c.write(IntToBytes(r.time()));
		c.write(FloatToBytes(r.score()));
		c.write(IntToBytes(r.damage()));
		c.write(IntToBytes(r.total()));
		c.write(IntToBytes(r.enemy()));
		c.write(IntToBytes(r.elite()));
		c.write(IntToBytes(r.plus()));
		c.write(IntToBytes(r.boss()));

		return recordBytes;
	}

	/// 读写指针辅助类, 用于封装位置管理与数组拷贝
	private static class Cursor
	{
		private final byte[] buf;
		private int pos = 0;

		Cursor(byte[] buf)
		{
			this.buf = buf;
		}

		String readFixedString(int len)
		{
			String s = BytesToString(copyOfRange(buf, pos, pos + len));
			pos += len;
			return s;
		}

		int readInt()
		{
			int v = BytesToInt(copyOfRange(buf, pos, pos + 4));
			pos += 4;
			return v;
		}

		float readFloat()
		{
			float v = BytesToFloat(copyOfRange(buf, pos, pos + 4));
			pos += 4;
			return v;
		}

		void write(byte[] src)
		{
			System.arraycopy(src, 0, buf, pos, src.length);
			pos += src.length;
		}
	}
}
