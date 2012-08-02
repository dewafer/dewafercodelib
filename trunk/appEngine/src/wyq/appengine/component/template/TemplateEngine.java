package wyq.appengine.component.template;

import wyq.appengine.Component;
import wyq.appengine.Factory;
import wyq.appengine.FactoryParameter;
import wyq.appengine.component.ProxyFactory;
import wyq.appengine.component.Repository;

public class TemplateEngine implements Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1812650709192507019L;

	private TemplateEngineHandler handler;

	@SuppressWarnings("unchecked")
	public <T> T getTemplate(Class<? extends T> templateInterface) {

		Factory<Object> proxyFactory = Repository.get("ProxyFactory",
				ProxyFactory.class);
		FactoryParameter parameter = proxyFactory.prepare(
				new Class<?>[] { templateInterface }, handler);
		return (T) proxyFactory.factory(parameter);
	}

	public static TemplateEngine get() {
		return Repository.get("TemplateEngine", TemplateEngine.class);
	}

	public TemplateEngineHandler getHandler() {
		return handler;
	}

	public void setHandler(TemplateEngineHandler handler) {
		this.handler = handler;
	}
}
