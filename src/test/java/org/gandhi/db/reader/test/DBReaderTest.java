package org.gandhi.db.reader.test;

import org.gandhi.db.reader.QueryProcessor;
import org.gandhi.db.reader.exception.DBReaderException;

public class DBReaderTest {

	public static void main(String args[])  throws DBReaderException{
		String sqlQuery1 = "Select * from Table1;";
		String sqlQuery2 = "Delete from Table2 where col1 = 2;";
		String sqlQuery3 = "Insert in to Table2 values(1,2,3)";
		String sqlQuery4 = "Update Table2 set col=2;";
		String sqlQuery5 = "Select * from Table3";
		
		System.setProperty("org.gandhi.db.reader.config.file.path", "db-reader.cfg.properties");
		QueryProcessor.getInstance().process(sqlQuery5);
	}
}
