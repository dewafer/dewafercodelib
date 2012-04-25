package wyq.test;

import wyq.infrastructure.InjectManager;

public class TestBean {

	private String value1;
	private int value2;
	private double value3 = 0.99f;
	private boolean value4;
	private InjectManager value5;

	public InjectManager getValue5() {
		return value5;
	}

	public void setValue5(InjectManager value5) {
		this.value5 = value5;
	}

	public boolean isValue4() {
		return value4;
	}

	public void setValue4(boolean value4) {
		this.value4 = value4;
	}

	public double getValue3() {
		return value3;
	}

	public void setValue3(double value3) {
		this.value3 = value3;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public int getValue2() {
		return value2;
	}

	public void setValue2(int value2) {
		this.value2 = value2;
	}

	@Override
	public String toString() {
		return "[v1:" + value1 + " v2:" + value2 + " v3:" + value3 + " v4:"
				+ value4 + " v5:" + value5 + "]" + super.toString();
	}
}
