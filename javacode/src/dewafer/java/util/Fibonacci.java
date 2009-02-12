package dewafer.java.util;

public class Fibonacci {

	private final static int ROUND = 50;
	private static long n_pervious = 1;
	private static long n_this = 1;
	private static long n_next = 0;
	private static double golden_section = 0.0d;

	private static void print()
	{
		StringBuilder str = new StringBuilder();
		str.append(n_pervious);
		str.append(" ");
		str.append(n_this);
		str.append(" ");
		str.append(n_next);
		str.append(" ");
		str.append(golden_section);
		str.append("\n");
		System.out.print(str.toString());
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i = 0;i<ROUND;i++)
		{
			n_next = n_pervious + n_this;
			golden_section = (double) n_next / n_this;
			print();
			n_pervious = n_this;
			n_this = n_next;
		}
	}

}
