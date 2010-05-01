package util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ObjectCopy {

	/**
	 * ²»¿½±´staticºÍfinalµÄfields
	 * 
	 * @param from
	 * @param to
	 */
	public static void copyFields(final Object from, final Object to) {
		Class<?> fromClzz = from.getClass();
		Class<?> toClzz = to.getClass();
		for (Field f : fromClzz.getDeclaredFields()) {
			if (!isFinal(f) && !isStatic(f)) {
				try {
					findAndSet(f, toClzz.getDeclaredFields(), from, to);
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private static void findAndSet(Field from, Field[] targets, Object from_o,
			Object to_o) throws IllegalArgumentException,
			IllegalAccessException {
		for (Field to : targets) {
			if (to.getName().equals(from.getName())) {
				if (to.getType().equals(from.getType())) {
					to.setAccessible(true);
					from.setAccessible(true);
					to.set(to_o, from.get(from_o));
					break;
				}
			}
		}
	}

	private static boolean isFinal(Field f) {
		return Modifier.isFinal(f.getModifiers());
	}

	private static boolean isStatic(Field f) {
		return Modifier.isStatic(f.getModifiers());
	}

	public static void printObject(Object o) {
		Class<?> c = o.getClass();
		println(o.toString());
		for (Field f : c.getDeclaredFields()) {
			f.setAccessible(true);
			print(Modifier.toString(f.getModifiers()));
			// if(isStatic(f)){
			// print(" static ");
			// }
			// if(isFinal(f)){
			// print(" final ");
			// }
			print(" ");
			print(f.getType().getName());
			print(" ");
			print(f.getName());
			print(" = ");
			try {
				print(f.get(o));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				print(" null;");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				print(" [unknown];");
			}
			println();
		}
		println();
	}

	public static void println(Object o) {
		System.out.println(o);
	}

	public static void println() {
		System.out.println();
	}

	public static void print(Object o) {
		System.out.print(o);
	}
}
