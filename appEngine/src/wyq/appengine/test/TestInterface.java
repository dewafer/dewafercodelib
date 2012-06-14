package wyq.appengine.test;

import wyq.appengine.Repository;

public class TestInterface {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		iTest001 i1 = Repository.getComponent(iTest001.class);
		i1.f1();
	}

}
