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

	protected String getUser() {
		return "";
	};

	protected String getPassword() {
		return "";
	};

	private Connection conn;

	protected boolean useExecuteBatch = false;

	public void connect() throws ClassNotFoundException, SQLException {
		Class.forName(getSqlConnProviderClass());
		conn = DriverManager.getConnection(getConnStr(), getUser(),
				getPassword());
	}

	public void executeSQL(String sql) throws SQLException {
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
				if (useExecuteBatch) {
					pstmt.executeBatch();
					hasResult = false;
				} else {
					hasResult = pstmt.execute();
				}
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

	protected abstract void prepareParameter(PreparedStatement stmt)
			throws SQLException;

	protected abstract void processResult(ResultSet rs) throws SQLException;

	protected abstract void afterSqlExecuted(int updateCount);

	public void close() throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}
}
