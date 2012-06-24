package wyq.appengine.component.dao;

import java.lang.reflect.Method;
import java.util.Arrays;

public class DefaultHandler implements DaoEngineHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7420307159693763074L;

	// private static final String LINE_SEP =
	// System.getProperty("line.separator");

	Method method;
	Object[] args;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		this.method = method;
		this.args = args;
		System.out.println(this);
		// Annotation[] annotations = method.getAnnotations();
		// for (Annotation a : annotations)
		// System.out.println(a);
		// // TODO implements
		// 1.Read the SQL file(s)
		// Class<?> clazz = method.getDeclaringClass();
		// String name = clazz.getSimpleName() + "_" + method.getName();
		// InputStream input = clazz.getResourceAsStream(name);
		// String sql = read(input).toString();

		// 2.interpret the SQL
		// int paramCount = findParams(sql);

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

	@Override
	public String toString() {
		return "DefaultHandler [method=" + method + ", args="
				+ Arrays.toString(args) + "]";
	}

	// private int findParams(String sql) {
	// // TODO Auto-generated method stub
	// return 0;
	// }
	//
	// private StringBuilder read(InputStream is) {
	// BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	// StringBuilder sb = new StringBuilder();
	// String line = null;
	// try {
	// while ((line = reader.readLine()) != null) {
	// sb.append(line);
	// sb.append(LINE_SEP);
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// return sb;
	// }

}
