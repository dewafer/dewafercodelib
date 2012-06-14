package wyq.appengine;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Factory implements Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1267121772581641261L;

	private String defaultPackageName;
	private String invocationHandlerName;

	private InvocationHandler invocationHandler;

	protected Factory() {
		Property p = new Property("/factory.properties");
		defaultPackageName = p.getProperty("defaultPackageName");
		invocationHandlerName = p.getProperty("invocationHandlerName");
	}

	public Component factory(String name, Class<?> c) {
		if (name == null && c == null) {
			throw new RuntimeException("Wrong arguments! NullPointException!");
		}
		try {
			Class<?> keyCls;
			Component comp;

			if (c != null) {
				keyCls = c;
			} else {
				String fullName;
				if (defaultPackageName != null
						&& defaultPackageName.length() > 0) {
					fullName = defaultPackageName + "." + name;
				} else {
					fullName = name;
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
				.getComponent(invocationHandlerName);
	}
}
