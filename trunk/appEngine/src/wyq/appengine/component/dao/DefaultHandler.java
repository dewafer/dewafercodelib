package wyq.appengine.component.dao;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;

import wyq.appengine.ExceptionHandler;
import wyq.appengine.component.db.DBEngine;
import wyq.appengine.component.db.DBEngine.DBResult;
import wyq.appengine.component.db.DBEngineHandler;
import wyq.appengine.component.file.TextFile;

public class DefaultHandler implements DaoEngineHandler, DBEngineHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7420307159693763074L;

	private DaoResult daoResult = null;
	private Object[] params = null;
	private Class<?>[] paramTypes = null;

	private ExceptionHandler exceptionHandler;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		Class<?> clazz = method.getDeclaringClass();
		String fileName = clazz.getSimpleName() + "_" + method.getName()
				+ ".sql";
		String sql = new TextFile(clazz, fileName).readAll();

		daoResult = null;
		// params = getSqlParams(args);
		params = args;
		paramTypes = method.getParameterTypes();

		DBEngine db = DBEngine.get();
		DBEngineHandler tmpHandler = db.getHandler();
		db.setHandler(this);
		db.connect();
		db.executeSQL(sql);
		db.close();
		db.setHandler(tmpHandler);

		return daoResult;
	}

	// protected Object[] getSqlParams(Object[] args) {
	// Object[] argTmp = new Object[0];
	// if (args != null && args.length == 1) {
	// Object arg = args[0];
	// if (arg instanceof Object[]) {
	// argTmp = (Object[]) arg;
	// } else if (arg instanceof Collection<?>) {
	// Collection<?> c = (Collection<?>) arg;
	// argTmp = c.toArray(argTmp);
	// } else {
	// argTmp = args;
	// }
	// } else {
	// argTmp = args;
	// }
	// return argTmp;
	// }

	@Override
	public void prepareParameter(PreparedStatement stmt) {
		if (params == null)
			return;
		try {
			for (int i = 0; i < params.length; i++) {
				Object value = params[i];
				if (value == null) {
					stmt.setNull(i + 1, getJDBCType(paramTypes[i]));
				} else {
					stmt.setObject(i + 1, params[i]);
				}
			}
		} catch (SQLException e) {
			exceptionHandler.handle(e);
		}
	}

	protected int getJDBCType(Class<?> c) {
		if (String.class.equals(c)) {
			return Types.VARCHAR;
		} else if (BigDecimal.class.equals(c)) {
			return Types.NUMERIC;
		} else if (Boolean.TYPE.equals(c) || Boolean.class.equals(c)) {
			return Types.BIT;
		} else if (Byte.TYPE.equals(c) || Byte.class.equals(c)) {
			return Types.TINYINT;
		} else if (Short.TYPE.equals(c) || Short.class.equals(c)) {
			return Types.SMALLINT;
		} else if (Integer.TYPE.equals(c) || Integer.class.equals(c)) {
			return Types.INTEGER;
		} else if (Long.TYPE.equals(c) || Long.class.equals(c)) {
			return Types.BIGINT;
		} else if (Float.TYPE.equals(c) || Float.class.equals(c)) {
			return Types.REAL;
		} else if (Double.TYPE.equals(c) || Double.class.equals(c)) {
			return Types.DOUBLE;
		} else if (c.isArray()
				&& (Byte.TYPE.equals(c.getComponentType()) || Byte.class
						.equals(c.getComponentType()))) {
			return Types.VARBINARY;
		} else if (Date.class.equals(c)) {
			return Types.DATE;
		} else if (Time.class.equals(c)) {
			return Types.TIME;
		} else if (Timestamp.class.equals(c)) {
			return Types.TIMESTAMP;
		} else {
			return Types.OTHER;
		}
	}

	@Override
	public void processResult(DBResult result) {
		try {
			daoResult = new DaoResult(result);
		} catch (SQLException e) {
			exceptionHandler.handle(e);
		}
	}

	public ExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

}
