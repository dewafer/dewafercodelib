package wyq.infrastructure.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import wyq.infrastructure.DBSupporter;

public class DBSupporterTest extends DBSupporter {

    @Override
    protected String getSqlConnProviderClass() {
	return "org.sqlite.JDBC";
    }

    @Override
    protected String getConnStr() {
	return "jdbc:sqlite:./src/javatest/sqliteTest/db1.db3";
    }

    @Override
    protected void prepareParameter(PreparedStatement stmt) {
	// do nothing.
    }

    @Override
    protected void processResult(ResultSet rs) {
	try {
	    ResultSetMetaData metaData = rs.getMetaData();
	    while (rs.next()) {
		for (int i = 1; i <= metaData.getColumnCount(); i++) {
		    String strColLabel = metaData.getColumnLabel(i);
		    print(strColLabel + ":");
		    print(rs.getObject(i));
		    print(" ");
		}
		println("");
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    protected void afterSqlExecuted(int updateCount) {
	// It won't go here.
    }

    /**
     * @param args
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws SQLException,
	    ClassNotFoundException {
	println("start");
	DBSupporterTest test = new DBSupporterTest();
	println("connect");
	test.connect();
	println("exe sql");
	test.executeSQL("Select * from dataInfo");
	println("close");
	test.close();
    }

    public static void println(Object o) {
	System.out.println(o);
    }

    public static void print(Object o) {
	System.out.print(o);
    }
}
