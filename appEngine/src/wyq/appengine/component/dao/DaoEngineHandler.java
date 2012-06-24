package wyq.appengine.component.dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import wyq.appengine.Component;

public interface DaoEngineHandler extends InvocationHandler, Component {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable;
}
