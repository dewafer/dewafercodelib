package typeinfo.accessPrivate;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import tools.Print;

public class AccessPrivate {

	/**
	 * @param args
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException {
		// TODO Auto-generated method stub

		Print.println("access sucker");
		
		Class<?> type = Sucker.class;
		Object s = type.newInstance();
		
		for (Field f : type.getDeclaredFields()) {
			Print.print("name:");
			Print.println(f.getName());
			Print.print("is private?");
			Print.println(Modifier.isPrivate(f.getModifiers()));
			Print.print("is static?");
			Print.println(Modifier.isStatic(f.getModifiers()));
			Print.print("is final?");
			Print.println(Modifier.isFinal(f.getModifiers()));
			Print.print("value:");
			f.setAccessible(true);
			Print.println(f.get(s));
			Print.println();
		}
		
		Print.println("DOne");
		
		if(type.getDeclaredFields().length == 0){
			Print.println("no declared methods found.");
		}

	}

}
