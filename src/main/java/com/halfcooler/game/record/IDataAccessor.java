package com.halfcooler.game.record;

import java.util.List;

/// 实际上, DAO 模式只在有更多种存储方式时才有意义<br>
/// 目前只有二进制文件一种存储方式
public interface IDataAccessor
{
	void SaveRecord(Record record);
	List<Record> LoadRecords();
	void DeleteRecordByIndex(int index);
}
