package wyq.appengine;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import wyq.appengine.Factory.FactoryParameter;

public class ComponentFactory implements Component, Factory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1267121772581641261L;

	private String defaultPackageName;
	private String invocationHandlerName;

	private InvocationHandler invocationHandler;

	protected ComponentFactory() {
		Property p = Property.get("/factory.properties");
		defaultPackageName = p.getProperty("defaultPackageName");
		invocationHandlerName = p.getProperty("invocationHandlerName");
	}

	/* (non-Javadoc)
	 * @see wyq.appengine.Factory#factory(java.lang.String, java.lang.Class)
	 */
	@Override
	public Component factory(FactoryParameter parameterObject) {
		if (parameterObject.getComponentName() == null && parameterObject.getComponentClass() == null) {
			throw new RuntimeException("Wrong arguments! NullPointException!");
		}
		try {
			Class<?> keyCls;
			Component comp;

			if (parameterObject.getComponentClass() != null) {
				keyCls = parameterObject.getComponentClass();
			} else {
				String fullName;
				if (defaultPackageName != null
						&& defaultPackageName.length() > 0) {
					fullName = defaultPackageName + "." + parameterObject.getComponentName();
				} else {
					fullName = parameterObject.getComponentName();
				}

				try {
					keyCls = Class.forName(fullName);
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(e);
				}
			}

			if (keyCls.isInterface()) {
				if (invocationHandler == null) {
					loadHandler();
				}
				comp = (Component) Proxy.newProxyInstance(this.getClass()
						.getClassLoader(), new Class<?>[] { keyCls,
						Component.class }, invocationHandler);
			} else {
				comp = (Component) keyCls.newInstance();
			}
			return comp;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void loadHandler() {
		invocationHandler = (InvocationHandler) Repository
				.get(invocationHandlerName);
	}
}
