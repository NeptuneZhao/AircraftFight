package com.halfcooler.game.record;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Recorder
{
	private static final File file = new File("records.dat");

	public static List<Record> ReadRecords()
	{
		if (!file.exists())
			return new ArrayList<>();

		// 读取文件
		try (var in = new FileInputStream(file))
		{
			byte[] buffer = in.readAllBytes();
			int recordCount = buffer.length / 64;
			List<Record> records = new ArrayList<>(recordCount);
			for (int i = 0; i < recordCount; i++)
			{
				byte[] recordBytes = Arrays.copyOfRange(buffer, i * 64, (i + 1) * 64);
				records.add(Record.Read(recordBytes));
			}
			return records;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}

	public static void SaveRecords(Record r)
	{
		try (var in = new FileOutputStream(file))
		{
			in.write(r.Save());
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	// 后记, 感觉二进制文件对写入还是挺好的
	// 至于提到的删除操作, 二进制感觉有点鸡肋, 因为你需要把后边的都往前挪
	// 查询性能也不错, 下一个 commit 就是对比各种文件形式的性能
	// 如果不行, 就直接 txt 算了
	// 备注: csv 也行吧, 反正逻辑都一样
	// xml json yaml 都不考虑了, 纯粹浪费时间
}
