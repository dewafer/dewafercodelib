package wyq.test;

public class DaoTestBean1 {

	private String value1;
	private boolean value2;
	private int value3;

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public boolean isValue2() {
		return value2;
	}

	public void setValue2(boolean value2) {
		this.value2 = value2;
	}

	public int getValue3() {
		return value3;
	}

	public void setValue3(int value3) {
		this.value3 = value3;
	}

	@Override
	public String toString() {
		return "DaoTestBean1[value1=" + value1 + ";value2=" + value2
				+ ";value3=" + value3 + "]/" + super.toString();
	}

}
