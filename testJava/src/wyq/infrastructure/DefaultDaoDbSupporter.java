package wyq.infrastructure;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultDaoDbSupporter extends DBSupporter implements
		DaoDbSupporter {

	public static final String SQL_CONN_PROVIDER_CLASS_NAME = "SQL_CONN_PROVIDER_CLASS_NAME";
	public static final String SQL_CONN_STR = "SQL_CONN_STR";

	private Object[] sqlParameters;
	private Object result;
	private BeanFactory beanFactory = new DefaultDbBeanFactory();

	private Class<?> beanType;
	private Class<?> beanWrapperType;

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
		afterExecuted(rs);
	}

	@Override
	protected void afterSqlExecuted(int updateCount) {
		try {
			afterExecuted(updateCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void afterExecuted(Object sqlResult) throws SQLException {

		// no need of result
		if (beanType == null && beanWrapperType == null) {
			result = null;
			return;
		}

		// prepare result
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		boolean isOnlyOneResult = (beanWrapperType == null);
		if (sqlResult instanceof ResultSet) {
			ResultSet rs = (ResultSet) sqlResult;
			ResultSetMetaData metaData = rs.getMetaData();
			while (rs.next()) {
				Map<String, Object> bean = new HashMap<String, Object>();
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					String columnName = metaData.getColumnLabel(i + 1);
					Object value = rs.getObject(i + 1);
					bean.put(columnName, value);
				}
				resultList.add(bean);
				if (isOnlyOneResult) {
					break;
				}
			}
		} else {
			Map<String, Object> rowsEffected = new HashMap<String, Object>();
			rowsEffected.put("result", sqlResult);
			resultList.add(rowsEffected);
		}

		// produce the bean
		List<Object> beanList = new ArrayList<Object>();
		for (Map<String, Object> mapBean : resultList) {
			Object realBean = beanFactory.produceBean(mapBean, beanType);
			if (isOnlyOneResult) {
				result = realBean;
				break;
			}
			beanList.add(realBean);
		}

		// produce the wrappered bean
		if (!isOnlyOneResult) {
			result = beanFactory.produceWrapper(beanList, beanWrapperType);
		}
	}

	@Override
	public Object execute(String sql, Method invokedMethod, Class<?> daoClass,
			Object... params) {
		this.sqlParameters = params;
		// prepare result type
		beanType = null;
		beanWrapperType = null;
		Class<?> returnType = invokedMethod.getReturnType();

		if (void.class.equals(returnType)) {
			// return type is void
			beanType = null;
			beanWrapperType = null;
		} else if (returnType.isArray()) {
			// return type is Array
			beanType = returnType.getComponentType();
			beanWrapperType = returnType;
		} else if (List.class.equals(returnType)) {
			// return type is collection(generic type)
			ParameterizedType genericType = (ParameterizedType) invokedMethod
					.getGenericReturnType();
			beanType = (Class<?>) genericType.getActualTypeArguments()[0];
			beanWrapperType = returnType;
		} else {
			// return type is not array nor collection
			beanType = returnType;
			beanWrapperType = null;
		}

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
