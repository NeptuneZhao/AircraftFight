package com.halfcooler.game.record;

import java.util.List;

public interface RecordExport
{
	List<Record> getAllRecords();

	void addRecord(Record gameRecord);

	void deleteRecord(Record gameRecord);
}
