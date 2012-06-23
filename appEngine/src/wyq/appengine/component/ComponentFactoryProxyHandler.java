package wyq.appengine.component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import wyq.appengine.Component;
import wyq.appengine.Factory;
import wyq.appengine.FactoryParameter;

public class ComponentFactoryProxyHandler implements InvocationHandler,
		Component {

	/**
	 * *
	 */
	private static final long serialVersionUID = 6916777032196397091L;

	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2)
			throws Throwable {
		Class<?> clazz = arg1.getDeclaringClass();
		String keyName = clazz.getName() + ".impl";
		String implClazzName = Property.get().getProperty(keyName);
		if (implClazzName == null || implClazzName.length() == 0) {
			implClazzName = new Property(clazz).getProperty("implClass");
		}
		if (implClazzName == null || implClazzName.length() == 0) {
			// throw new RuntimeException("No implements configuration!");
			throw new RuntimeException(new UnsupportedOperationException(
					arg1.toString()));
		}

		Class<?> implClazz = null;
		Object implObj = null;
		try {
			try {
				implClazz = Class.forName(implClazzName);
			} catch (ClassNotFoundException e) {
				String defaultPakName = Property.get().getProperty(
						"ComponentFactory.defaultPackageName");
				implClazz = Class.forName(defaultPakName + "." + implClazzName);
			}
			implClazzName = implClazz.getName();
			if (Component.class.isAssignableFrom(implClazz)) {
				Class<? extends Component> implCompClz = implClazz
						.asSubclass(Component.class);
				implObj = Repository.get("ComponentFactoryProxy:"
						+ implClazzName, implCompClz);
			} else {
				throw new ClassNotFoundException();
			}
		} catch (ClassNotFoundException e) {
			Factory f = Repository.get("Factory", Factory.class);
			FactoryParameter param = f.buildParameter(implClazzName, implClazz);
			implObj = f.factory(param);
		}

		if (implObj == null) {
			throw new RuntimeException(
					"ComponentFactoryProxyHandler: Implements load failure!");
		}

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
			throw new RuntimeException(e);
		}
		return result;
	}

}
