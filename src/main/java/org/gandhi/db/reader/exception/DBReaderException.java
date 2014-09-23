package org.gandhi.db.reader.exception;

public class DBReaderException extends Exception{

	private static final long serialVersionUID = 8382617058578028812L;
	
	private String code;
	
	public DBReaderException(String message) {
		super(message);
	}

	public DBReaderException(Throwable throwable) {
		super(throwable);
	}

	public DBReaderException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public DBReaderException(String code, String message) {
		super(message);
		this.code=code;
	}
	
	public DBReaderException(String code, String message, Throwable throwable) {
		super(message, throwable);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}