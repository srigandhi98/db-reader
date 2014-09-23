package org.gandhi.db.reader.enums;

public enum DBReaderConfigProperty {

	CONFIG_PROPERTY_FILE("org.gandhi.db.reader.config.file.path"),
	DB_TYPE("org.gandhi.db.reader.db.type"),
	DB_HOST("org.gandhi.db.reader.db.host"),
	DB_NAME("org.gandhi.db.reader.db.name"),
	DB_USER("org.gandhi.db.reader.db.user"),
	DB_USER_PASSWORD("org.gandhi.db.reader.db.user.password"),
	DB_RESTRICTED_TABLES("org.gandhi.db.reader.tables.restricted");
	
	private String key;
	
	public String key(){
		return this.key;
	}
	
	private DBReaderConfigProperty(String key) {
		this.key = key;
	}	
}
