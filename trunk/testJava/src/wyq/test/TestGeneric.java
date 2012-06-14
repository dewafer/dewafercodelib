package wyq.test;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TestGeneric {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Method[] methods = DaoTestInterface4.class.getDeclaredMethods();
		for (Method m : methods) {
			println("==============");
			println("method:" + m);
			Class<?> returnType = m.getReturnType();
			println("\treturnType:" + returnType);
			println("\tisArray:" + returnType.isArray());
			println("\tcompments type:" + returnType.getComponentType());
			println("\tgenericSuperclass of returnType:"
					+ returnType.getGenericSuperclass());
			println("\t----------");
			Type grtype = m.getGenericReturnType();
			println("\tgclass:" + grtype);
			if (grtype instanceof ParameterizedType) {
				ParameterizedType gtype = (ParameterizedType) grtype;
				println("\tgeneric type:" + gtype);
				println("\t\tactualTypeArguments:"
						+ gtype.getActualTypeArguments()[0]);
				println("\t\townerType:" + gtype.getOwnerType());
				println("\t\trawType:" + gtype.getRawType());

			}
			Class<?> genericReturnType = (Class<?>) grtype.getClass();
			println("\tgreturnType:" + genericReturnType);
			println("\tgisArray:" + genericReturnType.isArray());
			println("\tgcompments type:" + genericReturnType.getComponentType());
			println("\tgenericSuperclass of greturnType:"
					+ genericReturnType.getGenericSuperclass());
			println("==============");
		}
	}

	public static void println(Object o) {
		System.out.println(o);
	}

}
