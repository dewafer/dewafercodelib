package wyq.test;

import wyq.infrastructure.DaoManager;

public class TestDao {

	private DaoTestInterface1 dao1;
	private DaoTestInterface2 dao2;
	private DaoTestInterface3 dao3;
	private DaoTestInterface1 dao4;

	public DaoTestInterface1 getDao4() {
		return dao4;
	}

	public void setDao4(DaoTestInterface1 dao4) {
		this.dao4 = dao4;
	}

	public DaoTestInterface2 getDao2() {
		return dao2;
	}

	public void setDao2(DaoTestInterface2 dao2) {
		this.dao2 = dao2;
	}

	public DaoTestInterface3 getDao3() {
		return dao3;
	}

	public void setDao3(DaoTestInterface3 dao3) {
		this.dao3 = dao3;
	}

	public DaoTestInterface1 getDao1() {
		return dao1;
	}

	public void setDao1(DaoTestInterface1 dao1) {
		this.dao1 = dao1;
	}

	public void doProcess() {
		System.out.println("Call dao1.selectDataCountWhere(\"a\", \"b\"):"
				+ dao1.selectDataCountWhere("a", "b"));
		dao1.selectDataCountWhere("sdcw1", "sdcw2");
		DaoTestBean1 bean = dao1.select("a", "b");
		System.out.println(bean);
		dao2.f();
		dao3.g();
		dao4.doDao4("test param");
	}

	public static void main(String[] args) {
		DaoManager dm = new DaoManager();
		// DaoTestInterface1 dao = (DaoTestInterface1) dm
		// .generateDao(DaoTestInterface1.class);
		DaoTestInterface1 dao1 = (DaoTestInterface1) dm
				.getDao(DaoTestInterface1.class);
		DaoTestInterface2 dao2 = (DaoTestInterface2) dm
				.getDao(DaoTestInterface2.class);
		DaoTestInterface3 dao3 = (DaoTestInterface3) dm
				.getDao(DaoTestInterface3.class);
		DaoTestInterface1 dao4 = (DaoTestInterface1) dm
				.getDao(DaoTestInterface1.class);
		TestDao td = new TestDao();
		td.setDao1(dao1);
		td.setDao2(dao2);
		td.setDao3(dao3);
		td.setDao4(dao4);
		td.doProcess();
	}
}
