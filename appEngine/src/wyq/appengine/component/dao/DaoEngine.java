package wyq.appengine.component.dao;

import wyq.appengine.Component;
import wyq.appengine.Factory;
import wyq.appengine.FactoryParameter;
import wyq.appengine.component.ProxyFactory;
import wyq.appengine.component.Repository;

public class DaoEngine implements Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8705962428277588108L;

	private DaoEngineHandler handler;

	@SuppressWarnings("unchecked")
	public <T> T getDao(Class<? extends T> daoInterface) {

		Factory<Object> proxyFactory = Repository.get("ProxyFactory",
				ProxyFactory.class);
		FactoryParameter parameter = proxyFactory.buildParameter(
				new Class<?>[] { daoInterface }, handler);
		return (T) proxyFactory.factory(parameter);
	}

	public static DaoEngine get() {
		return Repository.get("DaoEngine", DaoEngine.class);
	}

	public DaoEngineHandler getHandler() {
		return handler;
	}

	public void setHandler(DaoEngineHandler handler) {
		this.handler = handler;
	}
}
