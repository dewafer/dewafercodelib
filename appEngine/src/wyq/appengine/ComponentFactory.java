package wyq.appengine;


public class ComponentFactory implements Factory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1267121772581641261L;

	private String defaultPackageName;
	private String invocationHandlerName;

	private ComponentFactoryProxyHandler invocationHandler;

	protected ComponentFactory() {
		Property p = Property.get();
		defaultPackageName = p.getProperty("ComponentFactory.defaultPackageName");
		invocationHandlerName = p.getProperty("ComponentFactory.invocationHandlerName");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see wyq.appengine.Factory#factory(java.lang.String, java.lang.Class)
	 */
	@Override
	public Object factory(FactoryParameter parameterObject) {
		if (parameterObject.getComponentName() == null
				&& parameterObject.getComponentClass() == null) {
			throw new RuntimeException("Wrong arguments! NullPointException!");
		}
		try {
			Class<?> keyCls;
			Object comp;

			if (parameterObject.getComponentClass() != null) {
				keyCls = parameterObject.getComponentClass();
			} else {
				String fullName;
				if (defaultPackageName != null
						&& defaultPackageName.length() > 0) {
					fullName = defaultPackageName + "."
							+ parameterObject.getComponentName();
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
				comp = invocationHandler.getProxy(new Class<?>[] { keyCls,
						Component.class });
			} else {
				comp = keyCls.newInstance();
			}
			return comp;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void loadHandler() {
		invocationHandler = (ComponentFactoryProxyHandler) Repository
				.get(invocationHandlerName);
	}
}
