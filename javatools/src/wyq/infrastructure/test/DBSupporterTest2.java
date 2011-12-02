package wyq.infrastructure.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import wyq.infrastructure.DBSupporter;

public class DBSupporterTest2 extends DBSupporter {

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
	    while (rs.next()) {
		println(rs.getString("INFO"));
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    protected void afterSqlExecuted(int updateCount) {
	println(updateCount + " row(s) affected");
    }

    /**
     * @param args
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws SQLException,
	    ClassNotFoundException {
	println("start");
	DBSupporterTest2 test = new DBSupporterTest2();
	println("connect");
	test.connect();
	println("exe sql");
	test.executeSQL("INSERT INTO dataInfo VALUES('8','5','6')");
	test.executeSQL("INSERT INTO dataInfo VALUES('7','b','c')");
	println("close");
	test.close();
    }

    public static void println(Object o) {
	System.out.println(o);
    }
}
