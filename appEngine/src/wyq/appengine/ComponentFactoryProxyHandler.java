package wyq.appengine;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import wyq.appengine.Factory.FactoryParameter;

public class ComponentFactoryProxyHandler implements InvocationHandler,
		Component {

	/**
	 * *
	 */
	private static final long serialVersionUID = 6916777032196397091L;

	private Object proxy;
	private Set<Class<?>> proxyFaces = new HashSet<Class<?>>();

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

	public Object getProxy(Class<?>[] ifaces) {
		if (proxy == null) {
			proxy = getNewProxy(ifaces);
			Collections.addAll(proxyFaces, ifaces);
		} else {
			List<Class<?>> newFaces = Arrays.asList(ifaces);
			if (!proxyFaces.containsAll(newFaces)) {
				proxyFaces.addAll(newFaces);
				Class<?>[] arrProxyFaces = new Class<?>[proxyFaces.size()];
				arrProxyFaces = proxyFaces.toArray(arrProxyFaces);
				proxy = getNewProxy(arrProxyFaces);
			}
		}
		return proxy;
	}

	private Object getNewProxy(Class<?>[] ifaces) {
		return Proxy.newProxyInstance(this.getClass().getClassLoader(), ifaces,
				this);
	}
}
