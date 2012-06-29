package wyq.appengine.component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import wyq.appengine.Component;
import wyq.appengine.ExceptionHandler;
import wyq.appengine.Factory;
import wyq.appengine.FactoryParameter;

public class ComponentFactoryProxyHandler implements InvocationHandler,
		Component {

	/**
	 * *
	 */
	private static final long serialVersionUID = 6916777032196397091L;

	private ExceptionHandler exceptionHandler;

	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2)
			throws Throwable {
		Class<?> clazz = arg1.getDeclaringClass();
		String methodName = arg1.getName();
		String keyName = clazz.getName() + "." + methodName + ".impl";
		String implClazzName = Property.get().getProperty(keyName);
		if (implClazzName == null) {
			keyName = clazz.getName() + ".impl";
			implClazzName = Property.get().getProperty(keyName);
		}
		if (implClazzName == null) {
			implClazzName = new Property(clazz).getProperty(methodName
					+ ".impl");
		}
		if (implClazzName == null) {
			implClazzName = new Property(clazz).getProperty("impl");
		}
		if (implClazzName == null) {
			// throw new RuntimeException("No implements configuration!");
			exceptionHandler.handle(new UnsupportedOperationException(arg1
					.toString()));
		} else if (implClazzName.length() == 0) {
			// do nothing just return the argument
			Class<?> returnType = arg1.getReturnType();
			if (arg2 != null) {
				for (Object o : arg2) {
					if (returnType.isAssignableFrom(o.getClass())) {
						return returnType.cast(o);
					}
				}
			}
			return null;
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
			@SuppressWarnings("unchecked")
			Factory<Component> f = Repository.get("Factory", Factory.class);
			FactoryParameter param = f.prepare(implClazzName, implClazz);
			implObj = f.factory(param);
		}

		Object result = null;
		if (implObj != null) {
			if (implClazz == null) {
				implClazz = implObj.getClass();
			}

			// find same method in implObj
			Class<?>[] paramTypes = arg1.getParameterTypes();
			Method realMethod;
			try {
				realMethod = implClazz
						.getDeclaredMethod(methodName, paramTypes);
				realMethod.setAccessible(true);
				result = realMethod.invoke(implObj, arg2);
			} catch (Exception e) {
				exceptionHandler.handle(e);
			}
		}

		return result;
	}

	public ExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

}
