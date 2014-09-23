package org.gandhi.db.reader;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.gandhi.db.reader.enums.DBReaderConfigProperty;
import org.gandhi.db.reader.enums.DataBaseType;
import org.gandhi.db.reader.exception.DBReaderException;
import org.gandhi.db.reader.exception.DBReaderExceptionCode;
import org.gandhi.db.reader.util.DBReaderPropertyUtil;

public class QueryProcessor {
	private static QueryProcessor instance;

	private QueryProcessor() {

	}

	public static QueryProcessor getInstance() {
		if (instance == null) {
			synchronized (QueryProcessor.class) {
				if (instance == null)
					instance = new QueryProcessor();
			}
		}
		return instance;
	}

	public ResultSet process(String sqlQuery) throws DBReaderException {
		ResultSet resultSet = null;
		String databaseType = DBReaderPropertyUtil.getProperty(DBReaderConfigProperty.DB_TYPE.key());
		String dbHost = DBReaderPropertyUtil.getProperty(DBReaderConfigProperty.DB_HOST.key());
		String dbName = DBReaderPropertyUtil.getProperty(DBReaderConfigProperty.DB_NAME.key());
		String userName = DBReaderPropertyUtil.getProperty(DBReaderConfigProperty.DB_USER.key());
		String userPassword = DBReaderPropertyUtil.getProperty(DBReaderConfigProperty.DB_USER_PASSWORD.key());
		String restrictedTables = DBReaderPropertyUtil.getProperty(DBReaderConfigProperty.DB_RESTRICTED_TABLES.key());
			String[] restricedTablesList = restrictedTables.split(",");
			Set<String> restrictedTablesSet = new HashSet<String>();
			for(String s : restricedTablesList){
				restrictedTablesSet.add(s.toLowerCase());
			}

		validateQuery(sqlQuery, restrictedTablesSet);	
		String dbUrl = prepareDBUrl(databaseType, dbHost, dbName);
		Connection connection = null;
		Statement statement = null;
		try {
			connection = DriverManager.getConnection(dbUrl, userName, userPassword);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlQuery);
		} catch (SQLException sqle) {
			throw new DBReaderException(DBReaderExceptionCode.SERVER_SQL_EXCEPTION_OCCURED.code(), "An SQL Exception occured while processing the given query with the provided configurations - "+sqle.getMessage(), sqle);
		} finally{
			try{
				if(statement != null && !statement.isClosed()){
					statement.close();
				}
				if(connection != null && !connection.isClosed()){
					connection.close();
				}
			}catch (SQLException sqle) {
				throw new DBReaderException(DBReaderExceptionCode.SERVER_SQL_EXCEPTION_OCCURED.code(), "An SQL Exception occured while releasing the JDBC resources - "+sqle.getMessage(), sqle);
			}
		}
		return resultSet;
	}
	
	//validates Query as per our restrictions of avoiding DML(Data Modification Language) & certain tables in DQL(Data Query Language).
	private void validateQuery(String sqlQuery, Set<String> restrictedTablesSet) throws DBReaderException{
		try{
			net.sf.jsqlparser.parser.CCJSqlParserManager pm = new net.sf.jsqlparser.parser.CCJSqlParserManager();
			net.sf.jsqlparser.statement.Statement statement = pm.parse(new StringReader(sqlQuery));
			if(statement instanceof net.sf.jsqlparser.statement.select.Select){
				net.sf.jsqlparser.statement.select.Select selectStatement = (net.sf.jsqlparser.statement.select.Select) statement;
				net.sf.jsqlparser.parser.TablesNamesFinder tablesNamesFinder = new net.sf.jsqlparser.parser.TablesNamesFinder();
				Set<String> tableList = tablesNamesFinder.getTableList(selectStatement);
				for (Iterator<String> iter = tableList.iterator(); iter.hasNext();) {
					String currentTable = iter.next().toLowerCase();
					if(restrictedTablesSet.contains(currentTable)){
						throw new DBReaderException(DBReaderExceptionCode.CALLER_QUERY_CONTAINS_RESTRICTED_TABLES.code(), "Query Provided : "+sqlQuery+" contains reference to the restrictedTable - "+currentTable);
					}
				}
			} else if(statement instanceof net.sf.jsqlparser.statement.insert.Insert || statement instanceof net.sf.jsqlparser.statement.update.Update || statement instanceof net.sf.jsqlparser.statement.delete.Delete ){
				throw new DBReaderException(DBReaderExceptionCode.CALLER_QUERY_IS_OF_DML_TYPE.code(), "Query Provided : "+sqlQuery+" contains Data Modification Language(DML) constructs of SQL. Contains insert/update/delete.");
			}
		} catch(net.sf.jsqlparser.JSQLParserException jsqlpe){
			throw new DBReaderException(DBReaderExceptionCode.CALLER_QUERY_IS_SYNTACTICALLY_INVALID.code(), "Query Provided : "+sqlQuery+" is not syntactically valid.");
		}
	}

	private String prepareDBUrl(String databaseType, String dbHost, String dbName) throws DBReaderException {
		if (databaseType != null) {
			try {
				DataBaseType databaseTypeEnum = DataBaseType.fromCode(databaseType);
				if (dbHost != null && dbName != null) {
					return databaseTypeEnum.urlPrefix() + "//" + dbHost + "/" + dbName;
				} else {
					throw new DBReaderException(DBReaderExceptionCode.CALLER_DB_HOST_OR_NAME_NOT_SPECIFIED.code(), "The host/name of the database being read needs to be provided by setting : " + DBReaderConfigProperty.DB_HOST.key() + " & "
							+ DBReaderConfigProperty.DB_NAME + " in the config property file.");
				}
			} catch (IllegalArgumentException iae) {
				throw new DBReaderException(DBReaderExceptionCode.CALLER_DB_TYPE_NOT_SPECIFIED_OR_INVALID.code(), "The type of the database being read : " + databaseType
						+ " is either invalid or currently not supported. Currently only mysql is supported");
			}
		} else {
			throw new DBReaderException(DBReaderExceptionCode.CALLER_DB_TYPE_NOT_SPECIFIED_OR_INVALID.code(), "The type of the database being read needs to be provided by setting : " + DBReaderConfigProperty.DB_TYPE.key()
					+ "in the config property file. Currently only mysql is supported");
		}
	}
}