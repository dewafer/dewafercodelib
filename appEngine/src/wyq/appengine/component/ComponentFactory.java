package wyq.appengine.component;

import java.lang.reflect.InvocationHandler;

import wyq.appengine.Component;
import wyq.appengine.ExceptionHandler;
import wyq.appengine.Factory;
import wyq.appengine.FactoryParameter;
import wyq.appengine.component.ComponentFactory.ComponentFactoryParameter;

/**
 * 
 * This factory produces the component in the Repository.
 * 
 * @author dewafer
 * 
 */
public class ComponentFactory extends
		AbstractFactory<Object, ComponentFactoryParameter> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1267121772581641261L;

	private String defaultPackageName;
	private String invocationHandlerName;

	private InvocationHandler invocationHandler;

	protected ComponentFactory() {
		Property p = Property.get();
		defaultPackageName = p
				.getProperty("ComponentFactory.defaultPackageName");
		invocationHandlerName = p
				.getProperty("ComponentFactory.invocationHandlerName");
		exceptionHandler = Repository.get("ExceptionHandler",
				ExceptionHandler.class);
	}

	protected void loadHandler() {
		invocationHandler = (InvocationHandler) Repository
				.get(invocationHandlerName);
	}

	public String getDefaultPackageName() {
		return defaultPackageName;
	}

	@Override
	protected Object build(ComponentFactoryParameter param) {
		if (param.componentName == null && param.componentClass == null) {
			throw new IllegalArgumentException(
					"Wrong arguments! NullPointException!");
		}
		Object comp = null;
		try {
			Class<?> keyCls = null;

			if (param.componentClass != null) {
				keyCls = param.componentClass;
			} else {
				String fullName = param.componentName;

				try {
					keyCls = Class.forName(fullName);
				} catch (ClassNotFoundException e) {
					if (defaultPackageName != null
							&& defaultPackageName.length() > 0) {
						fullName = defaultPackageName + "."
								+ param.componentName;
						try {
							keyCls = Class.forName(fullName);
						} catch (ClassNotFoundException e1) {
							exceptionHandler.handle(e1);
						}
					}
				}
			}

			if (keyCls != null) {
				if (keyCls.isInterface()) {
					if (isInterfaceProxyDefined(keyCls)) {
						if (invocationHandler == null) {
							loadHandler();
						}
						Class<?>[] ifaces = new Class<?>[] { keyCls,
								Component.class };
						Factory<Object> factory = Repository.get(
								"ProxyFactory", ProxyFactory.class);
						FactoryParameter para = factory.prepare(ifaces,
								invocationHandler);
						comp = factory.factory(para);
					}
				} else {
					comp = keyCls.newInstance();
				}
			}
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}
		return comp;
	}

	@Override
	protected Class<ComponentFactoryParameter> factoryParamType() {
		return ComponentFactoryParameter.class;
	}

	protected boolean isInterfaceProxyDefined(Class<?> icls) {
		Property p = Property.get();
		String key = "^" + icls.getName().replace(".", "\\.")
				+ "(\\.\\w+)?\\.impl$";
		if (!p.isKeyDefined(key)) {
			p = new Property(icls);
			String simpleKey = "^(\\w+\\.)impl$";
			return p.isKeyDefined(simpleKey);
		} else {
			return true;
		}
	}

	public class ComponentFactoryParameter implements FactoryParameter {
		private String componentName;
		private Class<?> componentClass;

		public ComponentFactoryParameter(String componentName) {
			this(componentName, null);
		}

		public ComponentFactoryParameter(String componentName,
				Class<?> componentClass) {
			this.componentName = componentName;
			this.componentClass = componentClass;
		}
	}
}
