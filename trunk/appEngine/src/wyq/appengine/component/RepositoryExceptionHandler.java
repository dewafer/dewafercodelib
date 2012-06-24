package wyq.appengine.component;

import wyq.appengine.ExceptionHandler;

public class RepositoryExceptionHandler implements ExceptionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1909844012074747208L;

	@Override
	public void handle(Exception e) {
		throw new RuntimeException(e);
	}

}
