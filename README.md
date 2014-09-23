DB-Reader

A simple library which configures database connection via a property file and then allows user to read any data on that datasource. 

All the write operations like INSERT/UPDATE or DELETE operations are filtered and thrown back as exceptions by the library before even connecting to the data source.

It also takes a list of tables called  asrestricted tables, on which all operation including read/write are filtered and thrown back as exceptions by the library.

Currently only 'mysql' is supported.

I am using JSQLParser(http://jsqlparser.sourceforge.net/) to syntactically validate the provided SQL query & perform the above semantic restrictions as well.

All the library user is supposed to provide the path of the config file as a system property($org.gandhi.db.reader.config.file.path).

You can find a sampleTest file and a sample config file at 
    https://github.com/srigandhi98/db-reader/blob/master/src/test/java/org/gandhi/db/reader/test/DBReaderTest.java & 
    https://github.com/srigandhi98/db-reader/blob/master/src/test/resources/db-reader.cfg.properties
