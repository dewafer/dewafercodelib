package wyq.test;

import java.lang.reflect.Method;

public class TestAutoBoxing {

	public Integer f1() {
		return 1;
	}

	public int f2() {
		return 2;
	}

	/**
	 * @param args
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static void main(String[] args) throws SecurityException,
			NoSuchMethodException {
		Class<?> clazz = TestAutoBoxing.class;
		Method mf1 = clazz.getMethod("f1");
		Method mf2 = clazz.getMethod("f2");

		Class<?> returnTypeOfF1 = mf1.getReturnType();
		Class<?> returnTypeOfF2 = mf2.getReturnType();

		println("rtf1==rtf2?" + returnTypeOfF1.equals(returnTypeOfF2));
		println("isPrimitive?");
		println("\trtf1:" + returnTypeOfF1.isPrimitive());
		println("\trtf2:" + returnTypeOfF2.isPrimitive());

	}

	public static void println(Object o) {
		System.out.println(o);
	}

}
