package com.halfcooler.game.record;

import java.util.List;

public interface RecordExport
{
	List<Record> GetAllRecords();

	void AddRecord(Record gameRecord);

	void DeleteRecord(Record gameRecord);
}
