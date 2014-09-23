package org.gandhi.db.reader.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.gandhi.db.reader.enums.DBReaderConfigProperty;
import org.gandhi.db.reader.exception.DBReaderException;
import org.gandhi.db.reader.exception.DBReaderExceptionCode;

public class DBReaderPropertyUtil {

	private Properties properties;
	private static DBReaderPropertyUtil instance;

	private DBReaderPropertyUtil() throws DBReaderException{
		String configFilePath = System.getProperty(DBReaderConfigProperty.CONFIG_PROPERTY_FILE.key());
		if(configFilePath != null){
			try {
				properties = new Properties();
				InputStream is = this.getClass().getClassLoader().getResourceAsStream(configFilePath);
				if(is != null){
					properties.load(is);
				} else{
					throw new DBReaderException(DBReaderExceptionCode.CALLER_CONFIGURATION_PROPERTY_FILE_SPECIFICATION_EXCEPTION.code(), "configFile : "+configFilePath+" could not be provided");
				}
			} catch (IOException e) {
				throw new DBReaderException(DBReaderExceptionCode.CALLER_CONFIGURATION_PROPERTY_FILE_SPECIFICATION_EXCEPTION.code(), "IOException occured while trying to read/parse the configuration property file : "+configFilePath+" provided", e);
			}
		} else{
			throw new DBReaderException(DBReaderExceptionCode.CALLER_CONFIGURATION_PROPERTY_FILE_SPECIFICATION_EXCEPTION.code(), "Path / Location of the configuration property file need to be provided by setting the : "+DBReaderConfigProperty.CONFIG_PROPERTY_FILE.key());
		}
	}

	//returns null if the property is not currently set.
	public static String getProperty(String key) throws DBReaderException{
		if (instance == null) {
			instance = new DBReaderPropertyUtil();
		}
		return instance.properties.getProperty(key);
	}
}