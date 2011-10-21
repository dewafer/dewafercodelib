package wyq.infrastructure.test;

import java.lang.reflect.Field;

import wyq.infrastructure.PropertySupporter;

//public class PropertySupporterTest extends PropertySupporter {
public class PropertySupporterTest {

	private String testA;
	private int testB;
	private boolean testC;
	private char testD;
	private long testE;

	// public PropertySupporterTest() {
	// super("C:\\test.properties");
	// }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.out.println(new PropertySupporterTest().toString());
		System.out.println(PropertySupporter
				.inject(new PropertySupporterTest()).toString());
	}

	@Override
	public String toString() {
		Field[] fields = this.getClass().getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		for (Field f : fields) {
			sb.append(f.getName());
			f.setAccessible(true);
			try {
				sb.append("=" + f.get(this));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sb.append("\r\n");
		}
		return super.toString() + " \\\\ " + sb.toString();
	}

}
