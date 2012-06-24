package wyq.appengine.component.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import wyq.appengine.Component;
import wyq.appengine.ComponentField;
import wyq.appengine.component.Repository;

public class DBEngine implements Component {

	/**
     * 
     */
	private static final long serialVersionUID = 7080149505747366739L;

	protected Connection conn;

	protected DBEngineHandler handler;

	protected ConnectionProvider provider;

	public static DBEngine get() {
		return Repository.get("DBEngine", DBEngine.class);
	}

	public void connect() throws ClassNotFoundException, SQLException {
		Class.forName(provider.getSqlConnProviderClass());
		conn = DriverManager.getConnection(provider.getConnStr(),
				provider.getUser(), provider.getPassword());
	}

	public void executeSQL(String sql) throws SQLException {
		if (conn == null)
			return;
		try {
			Statement stmt = null;
			if (sql.contains("?") && handler != null) {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				handlerCall(pstmt);
				stmt = pstmt;
			} else {
				stmt = conn.createStatement();
			}
			if (stmt instanceof PreparedStatement) {
				PreparedStatement pstmt = (PreparedStatement) stmt;
				pstmt.execute();
			} else {
				stmt.execute(sql);
			}
			if (handler != null) {
				ResultSet resultSet = stmt.getResultSet();
				int updateCount = stmt.getUpdateCount();
				DBResult result = new DBResult(updateCount, resultSet);
				handlerCall(result);
			}
			if (!conn.getAutoCommit()) {
				conn.commit();
			}
		} catch (SQLException e) {
			if (!conn.getAutoCommit()) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					throw e1;
				}
			}
			throw e;
		}
	}

	private void handlerCall(Object o) {
		if (handler == null) {
			return;
		}
		try {
			if (o instanceof PreparedStatement) {
				PreparedStatement p = (PreparedStatement) o;
				handler.prepareParameter(p);
			} else if (o instanceof DBResult) {
				DBResult r = (DBResult) o;
				handler.processResult(r);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	public void close() throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}

	public DBEngineHandler getHandler() {
		return handler;
	}

	public void setHandler(DBEngineHandler handler) {
		this.handler = handler;
	}

	public ConnectionProvider getProvider() {
		return provider;
	}

	@ComponentField(name = { "DBEngineConnectionProvider" })
	public void setProvider(ConnectionProvider provider) {
		this.provider = provider;
	}

	public class DBResult {
		private int rowsCount;
		private ResultSet resultSet;

		protected DBResult(int rowsCount, ResultSet resultSet) {
			this.rowsCount = rowsCount;
			this.resultSet = resultSet;
		}

		public int getRowsCount() {
			return rowsCount;
		}

		public ResultSet getResultSet() {
			return resultSet;
		}

		public boolean hasResultSet() {
			return this.resultSet != null;
		}

	}
}
