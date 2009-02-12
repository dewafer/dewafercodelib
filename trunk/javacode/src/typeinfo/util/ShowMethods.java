package typeinfo.util;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class ShowMethods {

  // Print with a newline:
  public static void print(Object obj) {
    System.out.println(obj);
  }
  // Print a newline by itself:
  public static void print() {
    System.out.println();
  }
  // Print with no line break:
  public static void printnb(Object obj) {
    System.out.print(obj);
  }
  // The new Java SE5 printf() (from C):
  public static PrintStream
  printf(String format, Object... args) {
    return System.out.printf(format, args);
  }
  
  private static String usage = 
	  "usage:\n" +
	  "ShowMethods qualified.class.name\n" +
	  "To show all methods in class or:\n" +
	  "ShowMethods qualified.class.name word\n" +
	  "To search for methods involving 'word'";
  private static Pattern p = Pattern.compile("\\w+\\.");
	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length < 1){
			print(usage);
			System.exit(0);
		}
		int lines = 0;
		try{
			Class<?> c = Class.forName(args[0]);
			Method[] methods = c.getMethods();
			Constructor[] ctors = c.getConstructors();
			if(args.length == 1){
				for(Method method : methods)
					print(
						p.matcher(method.toString()).replaceAll(""));
				for(Constructor ctor : ctors)
					print(
						p.matcher(ctor.toString()).replaceAll(""));
				lines = methods.length + ctors.length;
			}else{
				for(Method method : methods)
					if(method.toString().indexOf(args[1]) !=-1){
						print(
							p.matcher(method.toString()).replaceAll(""));
						lines++;
					}
				for(Constructor ctor : ctors)
				{
					if(ctor.toString().indexOf(args[1]) !=-1){
						print(
							p.matcher(ctor.toString()).replaceAll(""));
						lines++;
					}
				}
			}
		}catch(ClassNotFoundException e){
			print("No such class: "+e);
		}
	}

}
