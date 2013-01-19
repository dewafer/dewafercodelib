package wyq.appengine.component.dao;

import wyq.appengine.Component;
import wyq.appengine.Factory;
import wyq.appengine.FactoryParameter;
import wyq.appengine.component.ProxyFactory;
import wyq.appengine.component.Repository;

/**
 * This Engine provides an easy DAO object which can access the DB through the
 * JDBC Drivers. Simply use the DaoEngine.get() to get the DaoEngine with the
 * default configuration of the Repository.
 * 
 * @author dewafer
 * @version 1
 */
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
		FactoryParameter parameter = proxyFactory.prepare(
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
