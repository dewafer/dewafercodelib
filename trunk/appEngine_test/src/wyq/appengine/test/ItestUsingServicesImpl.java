package wyq.appengine.test;

import wyq.appengine.Factory;
import wyq.appengine.component.Repository;
import wyq.appengine.component.ServiceLoaderFactory;

public class ItestUsingServicesImpl implements ItestUsingServices,
		ItestUsingServices2 {

	@Override
	public void f1() {
		// TODO Auto-generated method stub
		System.out.println(this.getClass() + "//f1");
	}

	public static void main(String[] args) {

		// produced by factory
		Factory f = Repository.get("ServiceLoaderFactory",
				ServiceLoaderFactory.class);
		ItestUsingServices s = (ItestUsingServices) f
				.manufacture(ItestUsingServices.class);
		s.f1();

		// or get from repository
		ItestUsingServices2 s2 = (ItestUsingServices2) Repository
				.get(ItestUsingServices2.class);
		s2.f2();
	}

	@Override
	public void f2() {
		// TODO Auto-generated method stub
		System.out.println(this.getClass() + "//f2");
	}
}
