package wyq.appengine.component;

import java.lang.reflect.InvocationHandler;

import wyq.appengine.Component;
import wyq.appengine.ExceptionHandler;
import wyq.appengine.Factory;
import wyq.appengine.FactoryParameter;

public class ComponentFactory implements Factory<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1267121772581641261L;

	private String defaultPackageName;
	private String invocationHandlerName;

	private InvocationHandler invocationHandler;
	private ExceptionHandler exceptionHandler;

	protected ComponentFactory() {
		Property p = Property.get();
		defaultPackageName = p
				.getProperty("ComponentFactory.defaultPackageName");
		invocationHandlerName = p
				.getProperty("ComponentFactory.invocationHandlerName");
		exceptionHandler = Repository.get("ExceptionHandler",
				ExceptionHandler.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see wyq.appengine.Factory#factory(java.lang.String, java.lang.Class)
	 */
	@Override
	public Object factory(FactoryParameter factoryParameterObject) {
		if (factoryParameterObject instanceof ComponentFactoryParameter) {
			ComponentFactoryParameter parameterObject = (ComponentFactoryParameter) factoryParameterObject;
			if (parameterObject.getComponentName() == null
					&& parameterObject.getComponentClass() == null) {
				throw new IllegalArgumentException(
						"Wrong arguments! NullPointException!");
			}
			try {
				Class<?> keyCls = null;
				Object comp = null;

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
						try {
							fullName = parameterObject.getComponentName();
							keyCls = Class.forName(fullName);
						} catch (ClassNotFoundException e1) {
							exceptionHandler.handle(e1);
						}
					}
				}

				if (keyCls != null) {
					if (keyCls.isInterface()) {
						if (invocationHandler == null) {
							loadHandler();
						}
						Class<?>[] ifaces = new Class<?>[] { keyCls,
								Component.class };
						Factory<Object> factory = Repository.get(
								"ProxyFactory", ProxyFactory.class);
						FactoryParameter para = factory.buildParameter(ifaces,
								invocationHandler);
						comp = factory.factory(para);
					} else {
						comp = keyCls.newInstance();
					}
				}
				return comp;

			} catch (Exception e) {
				exceptionHandler.handle(e);
			}
		} else {
			throw new IllegalArgumentException();
		}
		return null;
	}

	protected void loadHandler() {
		invocationHandler = (InvocationHandler) Repository
				.get(invocationHandlerName);
	}

	@Override
	public FactoryParameter buildParameter(Object... values) {
		if (values == null || values.length != 2) {
			throw new IllegalArgumentException();
		}
		return new ComponentFactoryParameter((String) values[0],
				(Class<?>) values[1]);
	}

	public class ComponentFactoryParameter implements FactoryParameter {
		private String componentName;
		private Class<?> componentClass;

		public ComponentFactoryParameter(String componentName,
				Class<?> componentClass) {
			this.componentName = componentName;
			this.componentClass = componentClass;
		}

		public String getComponentName() {
			return componentName;
		}

		public void setComponentName(String componentName) {
			this.componentName = componentName;
		}

		public Class<?> getComponentClass() {
			return componentClass;
		}

		public void setComponentClass(Class<?> componentClass) {
			this.componentClass = componentClass;
		}
	}

	public String getDefaultPackageName() {
		return defaultPackageName;
	}

	public ExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}
}
