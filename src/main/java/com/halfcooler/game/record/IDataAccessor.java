package com.halfcooler.game.record;

import java.util.List;

public interface IDataAccessor
{
	void SaveRecord(Record record);
	List<Record> LoadRecords();
	void DeleteRecordByIndex(int index);
}
