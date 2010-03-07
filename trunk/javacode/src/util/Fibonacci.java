package util;


/**
 * 一个示例<br/>
 * 打印斐波那契数列，并计算黄金比例。
 * @author dewafer
 * @version 2010/3/7
 */
public class Fibonacci {

	
	/**
	 * 计算次数
	 */
	private final static int ROUND = 50;
	
	/**
	 * 前一个数字
	 */
	private static long n_pervious = 1;
	/**
	 * 当前数
	 */
	private static long n_this = 1;
	
	/**
	 * 下一个数字
	 */
	private static long n_next = 0;
	/**
	 * 黄金比例
	 */
	private static double golden_section = 0.0d;

	
	/**
	 * 打印表头
	 */
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
	 * main函数
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
