package tools;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;

/**
 * This Class helps print on the console easily.
 * @author AIR
 *
 */
public class Print {

	/**
	 * Print with traditional <i> printf </i> function in C.
	 * @param format: a formated string to be printed
	 * @param args: arguments
	 * @return <i> PrintStream </i>
	 */
	  public static PrintStream
	  printf(String format, Object... args) {
	    return System.out.printf(format, args);
	  }
	  
	  /**
	   * Traditional <i> System.out.print </i> function
	   * @param obj: an object to be printed
	   */
	  public static void print(Object obj) {
	    System.out.print(obj);
	  }
	  
	  /**
	   * Traditional <i> System.out.println </i> function.
	   * @param obj: an object to be printed
	   */
	  public static void println(Object obj){
		  System.out.println(obj);
	  }
	  
	  /**
	   * Print a blank line.
	   */
	  public static void println()
	  {
		  System.out.println();
	  }
	  
		public static String pformat(Collection<?> c) {

			if (c.size() == 0)
				return "[]";

			StringBuilder result = new StringBuilder("[");

			for (Object elem : c) {

				if (c.size() != 1)
					result.append("\n  ");

				result.append(elem);

			}

			if (c.size() != 1)
				result.append("\n");

			result.append("]");

			return result.toString();
		}

		public static void pprint(Collection<?> c) {
			System.out.println(pformat(c));
		}

		public static void pprint(Object[] c) {
			System.out.println(pformat(Arrays.asList(c)));
		}
}
