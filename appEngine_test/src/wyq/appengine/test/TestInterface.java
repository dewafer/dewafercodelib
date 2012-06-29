package wyq.appengine.test;

import wyq.appengine.component.Repository;

public class TestInterface {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		iTest001 i1 = Repository.get(iTest001.class);
		i1.f1();
	}

}
