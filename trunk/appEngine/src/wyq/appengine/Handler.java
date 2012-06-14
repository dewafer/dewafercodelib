package wyq.appengine;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Handler implements InvocationHandler, Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6916777032196397091L;

	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2)
			throws Throwable {
		System.out.println("Handler [arg0=" + "..." + ", arg1=" + arg1
				+ ", arg2=" + Arrays.toString(arg2) + "]");
		// TODO implements
		return null;
	}
}
