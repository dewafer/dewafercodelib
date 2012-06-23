package wyq.appengine.component.dao;

import java.lang.reflect.Method;

public class DefaultHandler implements DaoEngineHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7420307159693763074L;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// // TODO implements
		// 1.locate SQL file(s)
		// 2.interpret SQL file
		// 3.execute SQL
		// 4.handle result

		// DaoEngineHandler handler = Repository.get("DaoEngineHandler",
		// DaoEngineHandler.class);
		// handler.setContent(new DaoEngineContent(method, args));
		//
		// DBEngine dbEngine = DBEngine.get();
		// dbEngine.setHandler(handler.getDBEngineHandler());
		//
		// dbEngine.connect();
		// dbEngine.executeSQL(handler.getSql());
		// dbEngine.close();
		//
		// return handler.getResult();
		return null;
	}

}
