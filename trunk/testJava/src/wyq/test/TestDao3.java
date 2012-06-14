package wyq.test;

import wyq.infrastructure.DaoManager;

public class TestDao3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DaoManager dm = new DaoManager();
		DaoTestInterface6 dao = (DaoTestInterface6) dm
				.getDao(DaoTestInterface6.class);
		println(dao.test1());
		println(dao.test2());
		println(dao.test3());
	}

	public static void println(Object o) {
		System.out.println(o);
	}

}
