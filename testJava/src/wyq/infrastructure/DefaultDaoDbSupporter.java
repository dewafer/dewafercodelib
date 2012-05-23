package wyq.infrastructure;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DefaultDaoDbSupporter extends DBSupporter implements
		DaoDbSupporter {

	public static final String SQL_CONN_PROVIDER_CLASS_NAME = "SQL_CONN_PROVIDER_CLASS_NAME";
	public static final String SQL_CONN_STR = "SQL_CONN_STR";

	private Object[] sqlParameters;
	private Object result;
	private BeanFactory beanFactory;
	private Class<?> daoClass;
	private Method invokedMethod;

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	protected String getSqlConnProviderClass() {
		return PropertyManager.getProperty(SQL_CONN_PROVIDER_CLASS_NAME);
	}

	@Override
	protected String getConnStr() {
		return PropertyManager.getProperty(SQL_CONN_STR);
	}

	@Override
	protected void prepareParameter(PreparedStatement stmt) throws SQLException {
		if (sqlParameters != null) {
			for (int i = 0; i < sqlParameters.length; i++) {
				stmt.setObject(i + 1, sqlParameters[i]);
			}
		}
	}

	@Override
	protected void processResult(ResultSet rs) throws SQLException {
		result = beanFactory.produceResult(rs, daoClass, invokedMethod);
	}

	@Override
	protected void afterSqlExecuted(int updateCount) {
		result = beanFactory
				.produceResult(updateCount, daoClass, invokedMethod);
	}

	@Override
	public Object execute(String sql, Method invokedMethod, Class<?> daoClass,
			Object... params) {
		this.sqlParameters = params;
		this.invokedMethod = invokedMethod;
		this.daoClass = daoClass;

		// clean the result before go.
		result = null;

		try {
			connect();
			executeSQL(sql);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
