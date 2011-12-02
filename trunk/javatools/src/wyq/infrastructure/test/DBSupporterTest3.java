package wyq.infrastructure.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import wyq.infrastructure.DBSupporter;

public class DBSupporterTest3 extends DBSupporter {

    @Override
    protected String getSqlConnProviderClass() {
	return "org.sqlite.JDBC";
    }

    @Override
    protected String getConnStr() {
	return "jdbc:sqlite:./src/javatest/sqliteTest/db1.db3";
    }

    @Override
    protected void prepareParameter(PreparedStatement stmt) throws SQLException {
	stmt.setInt(1, 1);
    }

    @Override
    protected void processResult(ResultSet rs) throws SQLException {
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
	DBSupporterTest3 test = new DBSupporterTest3();
	println("connect");
	test.connect();
	println("exe sql");
	test.executeSQL("Select * from dataInfo where id = ?");
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
