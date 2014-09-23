package org.gandhi.db.reader.result;

import java.util.ArrayList;
import java.util.List;

public class Result {
	private List<Row> rows;

	public List<Row> getRows() {
		if(rows == null) rows =new ArrayList<Row>();
		return rows;
	}
	
	public void setRows(List<Row> rows) {
		this.rows = rows;
	}
}
