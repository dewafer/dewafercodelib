package dewafer.java.tools;

import java.io.PrintStream;

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
}
