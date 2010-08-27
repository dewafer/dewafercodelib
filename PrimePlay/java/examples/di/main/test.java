package examples.di.main;

public class test {
	public static void main(String[] args) {
		double re = 0;
		test t = new test();
		int i = 1;
		long start = System.currentTimeMillis();
		while (Math.abs(t.term(i))>0.00000001){
			re += t.term(i);
			i ++;
		}
		long end = System.currentTimeMillis();
		System.out.println(re*4);
		System.out.println(end - start);
   }

	public double term(int n)
	{
		if(n%2 == 0){
			return -1/(2*n-1.0);
		}
		else return 1/(2*n-1.0);
	}
}