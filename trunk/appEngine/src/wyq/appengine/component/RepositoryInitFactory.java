package wyq.appengine.component;

import wyq.appengine.Component;
import wyq.appengine.Factory;
import wyq.appengine.FactoryParameter;
import wyq.appengine.component.RepositoryInitFactory.CompInitFactoryParam;

public class RepositoryInitFactory extends
		AbstractFactory<Object, CompInitFactoryParam> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1740371298282094781L;

	@SuppressWarnings("unchecked")
	@Override
	protected Object build(CompInitFactoryParam fparam) {
		Object component = fparam.compObject;

		if (component == null) {
			return null;
		}

		String factoryRegKey = "^" + getClass().getName().replace(".", "\\.")
				+ "\\.(\\w+Factory)$";
		for (String initFactoryName : Property.get().getProperties(
				factoryRegKey)) {
			Factory<Component> initFactory = (Factory<Component>) Repository
					.find(initFactoryName, Factory.class);
			if (initFactory == null) {
				Factory<Component> f = Repository.get("Factory", Factory.class);
				initFactory = (Factory<Component>) f
						.manufacture(initFactoryName);
				Repository.put(initFactoryName, Factory.class, initFactory);
			}
			component = initFactory.manufacture(component);
		}

		return component;
	}

	@Override
	protected Class<? extends CompInitFactoryParam> factoryParamType() {
		return CompInitFactoryParam.class;
	}

	class CompInitFactoryParam implements FactoryParameter {
		private Object compObject;

		public CompInitFactoryParam(Object compObject) {
			this.compObject = compObject;
		}

	}

}
