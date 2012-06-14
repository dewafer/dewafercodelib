package wyq.appengine.test;

import wyq.appengine.Repository;
import wyq.appengine.XMLProperty;

public class TestRepository {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 2; i++) {
			Repository.load();

			XMLProperty p = Repository.getComponent(XMLProperty.class);
			Repository component = Repository.getComponent(Repository.class);
			XMLProperty p2 = Repository.getComponent(XMLProperty.class);
			System.out.println(p);
			System.out.println(component);
			Repository c2 = Repository.getComponent(Repository.class);
			System.out.println(c2);
			p.put("test1111", "vvvv");
			System.out.println(p2);
			System.out.println(p);
			
			Repository.save();

		}
	}

}
