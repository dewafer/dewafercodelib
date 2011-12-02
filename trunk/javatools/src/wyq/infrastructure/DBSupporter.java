package wyq.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DBSupporter {

    protected abstract String getSqlConnProviderClass();

    protected abstract String getConnStr();

    private Connection conn;

    protected void connect() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
	Class.forName(getSqlConnProviderClass()).newInstance();
	conn = DriverManager.getConnection(getConnStr());
    }

    protected void executeSQL(String sql) throws SQLException {
	if (conn == null)
	    return;
	Statement stmt = null;
	if (sql.contains("?")) {
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    prepareParameter(pstmt);
	    stmt = pstmt;
	} else {
	    stmt = conn.createStatement();
	}
	boolean hasResult;
	try {
	    if (stmt instanceof PreparedStatement) {
		PreparedStatement pstmt = (PreparedStatement) stmt;
		hasResult = pstmt.execute();
	    } else {
		hasResult = stmt.execute(sql);
	    }
	    if (hasResult) {
		ResultSet resultSet = stmt.getResultSet();
		processResult(resultSet);
	    } else {
		afterSqlExecuted(stmt.getUpdateCount());
	    }
	    if (!conn.getAutoCommit()) {
		conn.commit();
	    }
	} catch (SQLException e) {
	    if (!conn.getAutoCommit()) {
		try {
		    conn.rollback();
		} catch (SQLException e1) {
		    e1.printStackTrace();
		}
	    }
	    throw e;
	}
    }

    protected abstract void prepareParameter(PreparedStatement stmt) throws SQLException;

    protected abstract void processResult(ResultSet rs) throws SQLException;

    protected abstract void afterSqlExecuted(int updateCount);

    protected void close() throws SQLException {
	if (conn != null) {
	    conn.close();
	}
    }
}
