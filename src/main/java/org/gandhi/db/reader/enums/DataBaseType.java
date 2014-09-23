package org.gandhi.db.reader.enums;

public enum DataBaseType {
	MY_SQL("mysql", "com.mysql.jdbc.Driver", "jdbc:mysql:"),
	POSTGRE_SQL("postgresql","org.postgresql.Driver","jdbc:postgresql:");
	
	private String code;
	
	private String driverClass;
	
	private String urlPrefix;
	
	
	public String driverClass() {
		return this.driverClass;
	}
	
	public String code(){
		return this.code;
	}
	
	public String urlPrefix() {
		return this.urlPrefix;
	}
	
	private DataBaseType(String code, String driverClass, String urlPrefix) {
		this.code = code;
		this.driverClass = driverClass;
		this.urlPrefix = urlPrefix;
	}
	
    public static DataBaseType fromCode(String code) {
        for (DataBaseType dbt : values()) {
            if (dbt.code().equalsIgnoreCase(code)) {
                return dbt;
            }
        }
        return DataBaseType.valueOf(code);
    }
}