package org.gandhi.db.reader.test;

import java.sql.SQLException;
import java.util.List;

import org.gandhi.db.reader.QueryProcessor;
import org.gandhi.db.reader.exception.DBReaderException;
import org.gandhi.db.reader.result.Result;
import org.gandhi.db.reader.result.Row;

public class DBReaderTest {

	public static void main(String args[])  throws DBReaderException, SQLException{
		String sqlQuery1 = "Select * from Table1;";
		String sqlQuery2 = "Delete from Table2 where col1 = 2;";
		String sqlQuery3 = "Insert in to Table2 values(1,2,3)";
		String sqlQuery4 = "Update Table2 set col=2;";
		String sqlQuery5 = "Select * from Table3";
		
		//System.setProperty("org.gandhi.db.reader.config.file.path", "db-reader-mysql.cfg.properties");
		System.setProperty("org.gandhi.db.reader.config.file.path", "db-reader-postgresql.cfg.properties");

		
		Result result = QueryProcessor.getInstance().process(sqlQuery1);
		List<Row> rows = result.getRows();
		for(Row r : rows){
			System.out.println(r.getColumns());
		}
	}
}
