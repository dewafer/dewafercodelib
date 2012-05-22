package wyq.infrastructure;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultDaoDbSupporter extends DBSupporter implements
		DaoDbSupporter {

	public static final String SQL_CONN_PROVIDER_CLASS_NAME = "SQL_CONN_PROVIDER_CLASS_NAME";
	public static final String SQL_CONN_STR = "SQL_CONN_STR";

	private Object[] sqlParameters;

	private Object result;
	private Type returnType;

	private InjectManager injectManager = new InjectManager();
	private Convertor convertor;

	public void setConvertor(Convertor convertor) {
		this.convertor = convertor;
	}

	public void setInjectManager(InjectManager injectManager) {
		this.injectManager = injectManager;
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
		result = null;
		if (returnType != null && !void.class.equals(returnType)) {
			try {
				Class<?> resultClass = null;
				Class<?> returnClass = null;
				if (returnType instanceof ParameterizedType) {
					ParameterizedType ptype = (ParameterizedType) returnType;
					resultClass = (Class<?>) ptype.getActualTypeArguments()[0];
					returnClass = (Class<?>) ptype.getRawType();
				} else {
					returnClass = (Class<?>) returnType;
					if (returnClass.isArray()) {
						resultClass = returnClass.getComponentType();
					} else {
						resultClass = returnClass;
					}
				}
				
				Method[] allMethods = resultClass.getMethods();
				List<String> fields = new ArrayList<String>();
				for (Method m : allMethods) {
					String methodName = m.getName();
					if (methodName.startsWith("set")) {
						fields.add(methodName.substring(3));
					}
				}
				List<Object> resultList = new ArrayList<Object>();
				// while (rs.next()) {
				int i = 10;
				while (i-- > 0) {
					Object bean = resultClass.newInstance();
					for (String fieldName : fields) {
						String dbColumnName = getDbColumnName(fieldName);
						// Object value = rs.getObject(dbColumnName);
						Object value = "test" + i;
						injectManager.setterInject(fieldName, value, bean,
								convertor);
					}
					if (resultClass.equals(returnClass)) {
						result = bean;
						return;
					}
					resultList.add(bean);
				}
				if (Collection.class.isAssignableFrom(returnClass)) {
					result = resultList;
				} else if (returnClass.isArray()) {
					Object[] arr = (Object[]) Array.newInstance(resultClass, 0);
					result = resultList.toArray(arr);
				}

			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private String getDbColumnName(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void afterSqlExecuted(int updateCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object execute(String sql, Type returnType, Object... params) {
		this.sqlParameters = params;
		this.returnType = returnType;
		this.result = null;
		try {
			processResult(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// try {
		// connect();
		// executeSQL(sql);
		// close();
		// } catch (ClassNotFoundException e) {
		// e.printStackTrace();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		return result;
	}

}
