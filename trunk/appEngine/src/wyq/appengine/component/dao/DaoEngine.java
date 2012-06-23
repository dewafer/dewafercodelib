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

	public Object getDao(Class<?>... daoInterface) {
		Factory factory = Repository.get("ProxyFactory", ProxyFactory.class);
		DaoEngineHandler handler = Repository.get("DaoEngineHandler",
				DaoEngineHandler.class);
		FactoryParameter parameter = factory.buildParameter(daoInterface,
				handler);
		return factory.factory(parameter);
	}

	public static DaoEngine get() {
		return Repository.get("DaoEngine", DaoEngine.class);
	}
}
