package wyq.appengine.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import wyq.appengine.Component;
import wyq.appengine.Property;
import wyq.appengine.Repository;

public class DBEngine implements Component {

	/**
     * 
     */
	private static final long serialVersionUID = 7080149505747366739L;

	protected Connection conn;

	protected DBEngineHandler handler = null;

	protected ConnectionProvider provider = null;

	protected Property property = null;

	public DBEngine() {
		property = Property.get();
		handler = getDBEngineHandler();

		provider = Repository.get("DBEngineDefConnProvider",
				DefaultConnProvider.class);
	}

	public static DBEngine get() {
		return Repository.get("DBEngine", DBEngine.class);
	}

	@SuppressWarnings("unchecked")
	public DBEngineHandler getDBEngineHandler() {
		String dbHandlerClassName = property
				.getProperty("DBEngine.DBEngineHandlerClassName");
		if (dbHandlerClassName == null || dbHandlerClassName.length() == 0) {
			return null;
		}
		Class<DBEngineHandler> dbHandlerClass = null;
		try {
			dbHandlerClass = (Class<DBEngineHandler>) Class
					.forName(dbHandlerClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String dbHandlerName = property
				.getProperty("DBEngine.DBEngineHandlerName");
		return Repository.get(dbHandlerName, dbHandlerClass);
	}

	public void connect() throws ClassNotFoundException, SQLException {
		Class.forName(provider.getSqlConnProviderClass());
		conn = DriverManager.getConnection(provider.getConnStr(),
				provider.getUser(), provider.getPassword());
	}

	public void executeSQL(String sql) throws SQLException {
		if (conn == null)
			return;
		Statement stmt = null;
		if (sql.contains("?")) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			if (handler != null) {
				handler.prepareParameter(pstmt);
			}
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
				if (handler != null) {
					handler.processResult(resultSet);
				}
			} else {
				if (handler != null) {
					handler.processResult(stmt.getUpdateCount());
				}
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

	public void setProvider(ConnectionProvider provider) {
		this.provider = provider;
	}
}
