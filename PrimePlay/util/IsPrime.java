package util;

import java.util.Scanner;

/*
 * 判断某一数字是否是素数
 */
@Deprecated
public class IsPrime {

	private boolean isPrime;

	private long number;

	public IsPrime(long number) throws UnexpectedNumberException {
		this.number = number;
		if (number == 1L || number <= 0) {
			throw new IsPrime.UnexpectedNumberException();
		} else if (number == 2L || number == 3L) {
			this.isPrime = true;
		} else {
			this.isPrime = initIsPrime();
		}
	}

	/**
	 * @param args
	 * @throws UnexpectedNumberException
	 */
	public static void main(String[] args) throws UnexpectedNumberException {

		long number = 110;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			log("input the number please(" + 0 + "~" + Long.MAX_VALUE + "):");
			number = scanner.nextLong();
			log(number + " is prime?" + isPrime(number));
		}

	}

	public static boolean isPrime(long number)
			throws UnexpectedNumberException {
		IsPrime isPrime = new IsPrime(number);
		return isPrime.isPrime;
	}
	
	public boolean isPrime()
	{
		return this.isPrime;
	}
	
	private boolean initIsPrime() {
		log("calc is primer...");
		boolean notPrime = false;

		long start = System.currentTimeMillis();
		
		double tmp = Math.sqrt((double)number);

		for(long i=3;i<tmp;i++)
		{
			if(number%i==0){
				notPrime = true;
				break;
			}
		}
		
		long end = System.currentTimeMillis();
		log((end - start) + " milliseconds");
		return !notPrime;
	}

	@Override
	public String toString() {
		return isPrime ? "true" : "false";
	}


	private static void log(Object obj) {
		System.out.println(obj);
	}

	public static class UnexpectedNumberException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public UnexpectedNumberException() {
			super("Can NOT be 1 or any number under 0.");
		}

	}

}
