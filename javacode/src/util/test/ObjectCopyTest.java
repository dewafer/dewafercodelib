package util.test;

import util.ObjectCopy;

public class ObjectCopyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Object1 o1 = new Object1();
		Object2 o2 = new Object2();
		ObjectCopy.println("=================before copy=================");
		ObjectCopy.printObject(o1);
		ObjectCopy.printObject(o2);
		ObjectCopy.copyFields(o1, o2);
		ObjectCopy.println("=================after  copy=================");
		ObjectCopy.printObject(o1);
		ObjectCopy.printObject(o2);
	}

	public static class Object1 {
		public String field1 = "this will be successfully copied";
		public int field2 = 199;
		private boolean hopefully = true;
		protected Object2 alllBBB = new Object2();
		public String wrongName = "wrongName";

		private Object nullfield = null;

		protected Object[] anArray = { "an array?", 110, false };

		private static Object1 thisIsWrong = new Object1();
		protected final String OhNo = "this will NOT be copied";
		public static final int yes = 110;

	}

	public static class Object2 {
		private String field1;
		private int field2;
		protected boolean hopefully;
		public Object2 alllBBB;
		String wrongname = "wrongname";

		private Object nullfield = "What about a null field?";

		public Object[] anArray = null;

		private static Object1 thisIsWrong;
		protected final String OhNo = "";
		public static final int yes = 0;
	}

}
