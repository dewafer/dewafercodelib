package wyq.appengine.component.dao;

import wyq.appengine.Component;
import wyq.appengine.Factory;
import wyq.appengine.FactoryParameter;
import wyq.appengine.component.Property;
import wyq.appengine.component.ProxyFactory;
import wyq.appengine.component.Repository;

public class DaoEngine implements Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8705962428277588108L;
	private String daoEngineHandlerName;

	public DaoEngine() {
		this.daoEngineHandlerName = Repository.get("Property", Property.class)
				.getProperty(DaoEngineHandler.class.getName() + ".impl");
	}

	@SuppressWarnings("unchecked")
	public <T> T getDao(Class<? extends T>... daoInterface) {

		DaoEngineHandler handler = Repository.find("DaoEngineHandler",
				DaoEngineHandler.class);
		if (handler == null) {
			Factory f = Repository.get("Factory", Factory.class);
			handler = (DaoEngineHandler) f.factory(f.buildParameter(
					daoEngineHandlerName, null));
			Repository.put("DaoEngineHandler", DaoEngineHandler.class, handler);
		}

		Factory proxyFactory = Repository.get("ProxyFactory",
				ProxyFactory.class);
		FactoryParameter parameter = proxyFactory.buildParameter(daoInterface,
				handler);
		return (T) proxyFactory.factory(parameter);
	}

	public static DaoEngine get() {
		return Repository.get("DaoEngine", DaoEngine.class);
	}
}
