package dewafer.java.util;
import java.util.Date;

class BeTerminated {
	private static long count = 0;
	private static long alive = 0;

	BeTerminated() {
		count++;
		alive++;
		// System.out.println("B"+alive+" C");
	}

	protected void finalize() {
		// System.out.println("B"+alive+" X");
		alive--;
	}

	public void saySomething() {
		System.out.println("hello! i will be terminated soon.");
	}

	public long getAliveCount() {
		return alive;
	}
}


/**
 * @author Dewafer
 *
 */
public class GCTest {
	//long l = Long.MAX_VALUE; // Long.MAX_VALUE==9223372036854775807
	static final long LOOP_TIMES = 1000000L;
	static final int SAMPE_TIMES = 10000;
	static final int PERCENT_TIMES = 4;
	static final int TEST_TIMES = 20;
	//DO NOT CHANGE THIS ONE!
	static final int PERCENT_UNIT =100 / PERCENT_TIMES;
	
	
	public static long LoopTest()
	{
		long[] sampIntervals = new long[SAMPE_TIMES];
		long[] percentIntervals = new long[PERCENT_TIMES];
		
		int percent = 0;
		long[] tmp = new long[SAMPE_TIMES];
		int tmpCount = 0;
		long avg = 0;

		BeTerminated b;
		System.out.println("  start step 1...calculating needed parms...");
		long sampIntervalTmp = LOOP_TIMES / SAMPE_TIMES;
		for(int i =0;i<SAMPE_TIMES;i++){
			sampIntervals[i] = sampIntervalTmp * (i+1);			
		}
		long percntIntevTmp = LOOP_TIMES / PERCENT_TIMES;
		for(int i =0;i<PERCENT_TIMES;i++){
			percentIntervals[i] = percntIntevTmp * (i+1);
		}
		System.out.println("  step 1 done...");
		
		System.out.println("  start step 2...object create looping...looping times:"+LOOP_TIMES);
		System.out.println("  this will take sometime, please wait a moment.");
		for (long i = 0; i < LOOP_TIMES; i++) {
			b = new BeTerminated();
			if (i == sampIntervals[tmpCount]) {
				tmp[tmpCount] = b.getAliveCount();
				//System.out.println(tmp[tmpCount]);
				tmpCount++;
			}
			if (i == percentIntervals[percent]) {
				percent += 1;
				System.out.println("    " + percent * PERCENT_UNIT + "% done.");
				System.out.flush();
			}
		}

		System.out.println("  step 2 done...now calculating average...");
		for (int i = 0; i < 10000; i++) {
			avg += tmp[i];
		}
		avg /= 10000;
		System.out.println("  average is " + avg);
		return avg;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long[] avgs = new long[TEST_TIMES];
		long avg = 0;
		long start = new Date().getTime();
		
		System.out.println("Starting GC System Test...");
		for(int i =0;i<TEST_TIMES;i++){
			System.out.println("NO."+(i+1)+" Test ...");
			avgs[i]=LoopTest();
			//System.out.println("NO."+(i+1)+" Test finished...");
		}
		
		System.out.println("Loop test done...calculate average scorec...");
		for(int i=0;i<TEST_TIMES;i++){
			avg += avgs[i];
			System.out.println("No."+(i+1)+" Test score:"+avgs[i]);
		}
		avg /= TEST_TIMES;
		System.out.println("average score is...."+avg);
		System.out.println("with in "+ (new Date().getTime() - start)/1000 + " second(s).");
	}

}
