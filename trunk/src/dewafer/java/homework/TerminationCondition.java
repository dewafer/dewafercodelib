package dewafer.java.homework;

//class BeTerminated {
//	private static long count = 0;
//	private static long alive = 0;
//
//	BeTerminated() {
//		count++;
//		alive++;
//		// System.out.println("B"+alive+" C");
//	}
//
//	protected void finalize() {
//		// System.out.println("B"+alive+" X");
//		alive--;
//	}
//
//	public void saySomething() {
//		System.out.println("hello! i will be terminated soon.");
//	}
//
//	public long getAliveCount() {
//		return alive;
//	}
//}

public class TerminationCondition {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//long l = Long.MAX_VALUE; // Long.MAX_VALUE==9223372036854775807
		final long LOOP_TIMES = 1000000L;
		final int SAMPE_TIMES = 10000;
		final int PERCENT_TIMES = 10;
		final int PERCENT_UNIT =100 / PERCENT_TIMES;
		
		long[] sampIntervals = new long[SAMPE_TIMES];
		long[] percentIntervals = new long[PERCENT_TIMES];
		
		
		int percent = 0;
		long[] tmp = new long[SAMPE_TIMES];
		int tmpCount = 0;
		int avg = 0;
		BeTerminated b;

		System.out.println("start step 1...calculating needed parms...");
		long sampIntervalTmp = LOOP_TIMES / SAMPE_TIMES;
		for(int i =0;i<SAMPE_TIMES;i++){
			sampIntervals[i] = sampIntervalTmp * (i+1);			
		}
		long percntIntevTmp = LOOP_TIMES / PERCENT_TIMES;
		for(int i =0;i<PERCENT_TIMES;i++){
			percentIntervals[i] = percntIntevTmp * (i+1);
		}
		System.out.println("step 1 done...");
		
		System.out.println("start step 2...object create looping...looping times:"+LOOP_TIMES);
		System.out.println("this will take sometime, please wait a moment.");
		for (long i = 0; i < LOOP_TIMES; i++) {
			b = new BeTerminated();
			if (i == sampIntervals[tmpCount]) {
				tmp[tmpCount] = b.getAliveCount();
				//System.out.println(tmp[tmpCount]);
				tmpCount++;
			}
			if (i == percentIntervals[percent]) {
				percent += 1;
				System.out.println(" " + percent * PERCENT_UNIT + "% done.");
				System.out.flush();
			}
		}

		System.out.println("step 2 done...now calculating average...");
		for (int i = 0; i < 10000; i++) {
			avg += tmp[i];
		}
		avg /= 10000;
		System.out.println("average is " + avg);
		//return avg;
		
	}

}
