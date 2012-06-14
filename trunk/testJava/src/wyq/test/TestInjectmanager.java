package wyq.test;

import wyq.infrastructure.Convertor;
import wyq.infrastructure.DefaultConvertor;
import wyq.infrastructure.InjectManager;

public class TestInjectmanager {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InjectManager m = new InjectManager();
		Convertor c = new DefaultConvertor();

		TestBean targetObject = new TestBean();
		m.setterInject("Value1", "testValue1", targetObject, c);
		m.setterInject("Value2", 345435432, targetObject, c);
		m.setterInject("Value3", 2343243242342435436578d, targetObject, c);
		m.setterInject("Value4", true, targetObject, c);
		m.setterInject("Value5", m, targetObject, c);

		System.out.println(targetObject);

		// System.out.println(Boolean.class.equals(Boolean.TYPE));
		// System.out.println(boolean.class.equals(Boolean.TYPE));
		// System.out.println(Boolean.class.isAssignableFrom(Boolean.TYPE));
		// System.out.println(Boolean.TYPE.isAssignableFrom(Boolean.class));
		// System.out.println(Boolean.class.isLocalClass());
		// System.out.println(Boolean.class.isMemberClass());
		// System.out.println(Boolean.class.isPrimitive());
		// System.out.println(Boolean.class.isSynthetic());
		// System.out.println(Boolean.TYPE.isLocalClass());
		// System.out.println(Boolean.TYPE.isMemberClass());
		// System.out.println(Boolean.TYPE.isPrimitive());
		// System.out.println(Boolean.TYPE.isSynthetic());
		// System.out.println(Boolean.class.getSuperclass());
		// System.out.println(double.class.getSuperclass());
	}
}
