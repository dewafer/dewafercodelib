package wyq.algorithm.GS.sim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TheGame implements Runnable {

	private List<Participator> everyone = Collections
			.synchronizedList(new ArrayList<Participator>());
	private ExecutorService exec = Executors.newCachedThreadPool();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new TheGame().run();
	}

	public List<Participator> getEveryone() {
		return everyone;
	}

	@Override
	public void run() {
		try {
			Matchmaker m = new Matchmaker(exec, this);
			Future<?> maker = exec.submit(m);

			TimeUnit.SECONDS.sleep(10);
			maker.cancel(true);
			TimeUnit.SECONDS.sleep(10);

			exec.shutdownNow();
			while (!exec.isTerminated()) {
				TimeUnit.MILLISECONDS.sleep(200);
			}
			printall();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void printall() {
		println("------------------------ RESULT ------------");
		println("all participators:" + everyone);

		println("------------------------ BOYS --------------");
		for (Participator p : everyone) {
			if (p instanceof Boy)
				println(p);
		}

		println("------------------------ GIRLS -------------");
		for (Participator p : everyone) {
			if (p instanceof Girl)
				println(p);
		}
		println("------------------------ END ----------------");
	}

	private void println(Participator p) {
		System.out.println(p + "{myLove:" + p.getMyLove() + ", blackName:"
				+ p.getBlacknameList() + ", myPref:" + p.getMyPreference()
				+ "}");

	}

	private void println(Object o) {
		System.out.println(o);
	}

}
