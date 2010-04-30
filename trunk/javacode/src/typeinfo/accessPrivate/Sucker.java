package typeinfo.accessPrivate;

public class Sucker {

	private static String field1 = "static";
	
	private final String field2 = "final";
	
	private static final String field3 = "static final";
	
	private String field4 = "normal";

	protected static String getField1() {
		return field1;
	}

	protected static void setField1(String field1) {
		Sucker.field1 = field1;
	}

	protected String getField4() {
		return field4;
	}

	protected void setField4(String field4) {
		this.field4 = field4;
	}

	protected String getField2() {
		return field2;
	}

	protected static String getField3() {
		return field3;
	}
	
	
	
}
