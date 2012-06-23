package wyq.appengine.component.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

public class DefaultHandler implements DaoEngineHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7420307159693763074L;

	private static final String LINE_SEP = System.getProperty("line.separator");

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// // TODO implements
		// 1.Read the SQL file(s)
//		Class<?> clazz = method.getDeclaringClass();
//		String name = clazz.getSimpleName() + "_" + method.getName();
//		InputStream input = clazz.getResourceAsStream(name);
//		String sql = read(input).toString();

		// 2.interpret the SQL
//		int paramCount = findParams(sql);

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

//	private int findParams(String sql) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	private StringBuilder read(InputStream is) {
//		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//		StringBuilder sb = new StringBuilder();
//		String line = null;
//		try {
//			while ((line = reader.readLine()) != null) {
//				sb.append(line);
//				sb.append(LINE_SEP);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return sb;
//	}

}
