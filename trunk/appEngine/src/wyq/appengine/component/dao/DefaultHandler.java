package wyq.appengine.component.dao;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

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

	private ExceptionHandler exceptionHandler;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		Class<?> clazz = method.getDeclaringClass();
		String fileName = clazz.getSimpleName() + "_" + method.getName()
				+ ".sql";
		String sql = new TextFile(clazz, fileName).readAll();

		daoResult = null;
		params = getSqlParams(args);

		DBEngine db = DBEngine.get();
		DBEngineHandler tmpHandler = db.getHandler();
		db.setHandler(this);
		db.connect();
		db.executeSQL(sql);
		db.close();
		db.setHandler(tmpHandler);

		return daoResult;
	}

	protected Object[] getSqlParams(Object[] args) {
		Object[] argTmp = new Object[0];
		if (args != null && args.length == 1) {
			Object arg = args[0];
			if (arg instanceof Object[]) {
				argTmp = (Object[]) arg;
			} else if (arg instanceof Collection<?>) {
				Collection<?> c = (Collection<?>) arg;
				argTmp = c.toArray(argTmp);
			} else {
				argTmp = args;
			}
		} else {
			argTmp = args;
		}
		return argTmp;
	}

	@Override
	public void prepareParameter(PreparedStatement stmt) {
		if (params == null)
			return;
		try {
			for (int i = 0; i < params.length; i++) {
				stmt.setObject(i + 1, params[i]);
			}
		} catch (SQLException e) {
			exceptionHandler.handle(e);
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
