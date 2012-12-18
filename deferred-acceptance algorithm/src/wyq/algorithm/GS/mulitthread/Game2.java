package wyq.algorithm.GS.mulitthread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import wyq.algorithm.GS.Participator;
import wyq.algorithm.GS.ParticipatorFactory;

public class Game2 {

	protected List<Participator> allParticipators = new ArrayList<Participator>();
	protected List<Boy2> allBoys;
	protected List<Girl2> allGirls;
	protected ParticipatorFactory factory = new ParticipatorFactory();
	protected float boysAverageHappiness = 0f;
	protected float girlsAverageHappiness = 0f;
	protected float allAverageHappiness = 0f;
	protected static boolean printResultList = true;

	// mulit-thread executor
	protected ExecutorService exec = Executors.newCachedThreadPool();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Game2().run();
	}

	private void run() {
		allBoys = factory.createMulitThreadBoyList();
		allGirls = factory.createMulitThreadGirlList();
		allParticipators.addAll(allBoys);
		allParticipators.addAll(allGirls);

		for (Participator p : allParticipators) {
			if (p instanceof Boy2) {
				p.createPreferenceList(allGirls);
			}
			if (p instanceof Girl2) {
				p.createPreferenceList(allBoys);
			}
		}

		for (Girl2 g : allGirls) {
			// girls go first
			exec.execute(g);
		}
		for (Boy2 b : allBoys) {
			// boys go second
			exec.execute(b);
		}
		// wait for 3 seconds...
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// end the task.
			exec.shutdownNow();

			if (printResultList)
				println("all participators:" + allParticipators);

			if (printResultList)
				println("------------ GAME OVER : RESULT ------------");
			float happiness = 0f;
			float allHappiness = 0f;
			for (Participator p : allBoys) {
				println(p);
				happiness += p.happiness();
				allHappiness += p.happiness();
			}
			boysAverageHappiness = happiness / allBoys.size();
			if (printResultList)
				println("Boys' average happiness:" + boysAverageHappiness);

			if (printResultList)
				println("--------------------------------------------");
			happiness = 0f;
			for (Participator p : allGirls) {
				println(p);
				happiness += p.happiness();
				allHappiness += p.happiness();
			}
			girlsAverageHappiness = happiness / allGirls.size();
			if (printResultList)
				println("Girls' average happiness:" + girlsAverageHappiness);

			if (printResultList)
				println("--------------------------------------------");
			allAverageHappiness = allHappiness / allParticipators.size();
			if (printResultList)
				println("All happiness average:" + allAverageHappiness);

		}
	}

	private void println(Participator p) {
		if (printResultList)
			System.out.println(p + "{myLove:" + p.getMyLove() + ", happiness:"
					+ p.happiness() + ", blackName:" + p.getBlacknameList()
					+ ", myPref:" + p.getPreferenceList() + "}");

	}

	private static void println(Object o) {
		System.out.println(o);
	}

}
