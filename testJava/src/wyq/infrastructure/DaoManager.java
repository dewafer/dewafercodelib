package wyq.infrastructure;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Comparator;

public class DaoManager implements InvocationHandler {

	private Object daoProxy;

	private DaoDbSupporter daoDbSupporter = new DefaultDaoDbSupporter();

	public void setDaoDbSupporter(DaoDbSupporter daoDbSupporter) {
		this.daoDbSupporter = daoDbSupporter;
	}

	private static final String LINE_SEP = System.getProperty("line.separator");

	public Object generateDao(Class<?>... daoInterface) {
		return Proxy.newProxyInstance(this.getClass().getClassLoader(),
				daoInterface, this);
	}

	public Object getDao(Class<?> daoInterface) {
		if (daoProxy == null) {
			daoProxy = Proxy.newProxyInstance(this.getClass().getClassLoader(),
					new Class<?>[] { daoInterface }, this);
		} else {
			Class<?>[] interfaces = daoProxy.getClass().getInterfaces();
			int pos = Arrays.binarySearch(interfaces, daoInterface,
					new Comparator<Class<?>>() {

						@Override
						public int compare(Class<?> o1, Class<?> o2) {
							String name1 = o1.getCanonicalName();
							String name2 = o2.getCanonicalName();
							return name1.compareTo(name2);
						}
					});
			if (pos < 0) {
				int length = interfaces.length;
				int newLength = length + 1;
				Class<?>[] newInterfaces = Arrays.copyOf(interfaces, newLength);
				newInterfaces[length] = daoInterface;
				daoProxy = generateDao(newInterfaces);
			}
		}
		return daoProxy;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// find the SQL file first.
		Class<?> daoClass = method.getDeclaringClass();
		// String resName = daoClass.getName() + "_" + method.getName();
		// InputStream resourceAsStream = daoClass.getResourceAsStream(resName);
		// BufferedReader reader = new BufferedReader(new InputStreamReader(
		// resourceAsStream));
		StringBuilder sb = new StringBuilder();
		// String line = null;
		// while ((line = reader.readLine()) != null) {
		// sb.append(line);
		// sb.append(LINE_SEP);
		// }

		Type returnType = method.getGenericReturnType();

		// then execute the SQL and return the result.
		return daoDbSupporter
				.execute(sb.toString(), returnType, daoClass, args);
	}

}
