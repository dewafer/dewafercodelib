package examples.di.main;

public class test2 {
	public static boolean isPrime(long n) {
		long start = System.currentTimeMillis();
		boolean re = false;
		for (long i = 3; i < n; i += 2) {
			long j = 1;
			double temp = Math.sqrt(i);
			for (j = 2; j < temp; j++) {
				if (i % j == 0)
					break;
			}
			if (j == (long) temp + 1)
				re = true;
		}
		long end = System.currentTimeMillis();
		System.out.println((end - start) + " milliseconds");
		return re;
	}

	public static void main(String[] args) {
		System.out.println(isPrime(13916045561L));
	}
}