package wyq.appengine.test;

import wyq.appengine.Repository;

public class Itest002Impl implements Itest002 {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9113841290833631003L;

	@Override
	public void f1() {
		System.out.println("Itest002Impl.f1() called!");
	}

	@Override
	public String f2(String v) {
		System.out.println("Itest002Impl.f2(" + v + ") called!");
		return v;
	}

	public static void main(String[] arg) {
		Itest002 o = Repository.get(Itest002.class);
		o.f1();
		System.out.println("abc=" + o.f2("abc"));
	}

}
