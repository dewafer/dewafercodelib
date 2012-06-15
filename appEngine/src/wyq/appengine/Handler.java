package wyq.appengine;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import wyq.appengine.Factory.FactoryParameter;

public class Handler implements InvocationHandler, Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6916777032196397091L;

	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2)
			throws Throwable {
		Class<?> clazz = arg1.getDeclaringClass();
		String implClazzName = Property.get(clazz).getProperty("Class");
		if (implClazzName == null || implClazzName.length() == 0) {
			throw new RuntimeException("No implements configuration!");
		}

		Class<?> implClazz;
		try {
			implClazz = Class.forName(implClazzName);
			implClazzName = implClazz.getSimpleName();
		} catch (Exception e) {
			// e.printStackTrace();
			implClazz = null;
		}
		Factory f = Repository.get("Factory", Factory.class);
		Object implObj = f.factory(new FactoryParameter(implClazzName,
				implClazz));

		if (implClazz == null) {
			implClazz = implObj.getClass();
		}

		// find same method in implObj
		String methodName = arg1.getName();
		Class<?>[] paramTypes = arg1.getParameterTypes();
		Method realMethod;
		Object result = null;
		try {
			realMethod = implClazz.getDeclaredMethod(methodName, paramTypes);
			realMethod.setAccessible(true);
			result = realMethod.invoke(implObj, arg2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
