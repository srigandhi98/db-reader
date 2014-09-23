package org.gandhi.db.reader.result;

import java.util.ArrayList;
import java.util.List;

public class Row {
	private List<Object> columns;

	public List<Object> getColumns() {
		if(columns == null) columns = new ArrayList<Object>();
		return columns;
	}

	public void setColumns(List<Object> columns) {
		this.columns = columns;
	}
}
