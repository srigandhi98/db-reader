package org.gandhi.db.reader.exception;

public enum DBReaderExceptionCode {
	
	CALLER_CONFIGURATION_PROPERTY_FILE_SPECIFICATION_EXCEPTION("101.1"),
	CALLER_DB_TYPE_NOT_SPECIFIED_OR_INVALID("101.2"),
	CALLER_DB_HOST_OR_NAME_NOT_SPECIFIED("101.3"),
	CALLER_QUERY_IS_SYNTACTICALLY_INVALID("101.4"),
	CALLER_QUERY_IS_OF_DML_TYPE("101.5"),
	CALLER_QUERY_CONTAINS_RESTRICTED_TABLES("101.6"),
	SERVER_SQL_EXCEPTION_OCCURED("201.1");

	private String code;

	private DBReaderExceptionCode(String errorCode) {
		this.code = errorCode;
	}

	public String code() {
		return code;
	}

	public static DBReaderExceptionCode fromCode(String code) {
		for(DBReaderExceptionCode errorCode : DBReaderExceptionCode.values()) {
			if(errorCode.code.equalsIgnoreCase(code)) {
				return errorCode;
			}
		}
		return null;
	}
}