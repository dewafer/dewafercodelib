package wyq.test;

public class TestBean3 {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "TestBean3 [value=" + value + "]" + super.toString();
	}

}
