package wyq.algorithm.GS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

	protected List<Participator> allParticipators = new ArrayList<Participator>();
	protected List<Boy> allBoys;
	protected List<Girl> allGirls;
	protected ParticipatorFactory factory = new ParticipatorFactory();
	protected float boysAverageHappiness = 0f;
	protected float girlsAverageHappiness = 0f;
	protected float allAverageHappiness = 0f;
	protected static boolean printResultList = true;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 new Game().run();
//		for (int i = 0; i < 10; i++) {
//			final long start = System.currentTimeMillis();
//			final int counti = i;
//			new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//					String gameName = "game" + (counti + 1);
//					println("Running " + gameName + "... ");
//					demonstrator(gameName);
//					println(gameName + ":"
//							+ (System.currentTimeMillis() - start)
//							+ " ms. used...");
//				}
//			}).start();
//		}
	}

	private static void demonstrator(String gameName) {
		// no log
		Game.printResultList = false;
		Participator.printLog = false;

		// 2000 participators
		ParticipatorFactory.MAX_NUM_BOYS = 100;
		ParticipatorFactory.MAX_NUM_GIRLS = 100;

		int MAX_TRY = 100;
		float[] boysHappinessPoints = new float[MAX_TRY];
		float[] girlsHappinessPoints = new float[MAX_TRY];
		float[] allHappinessPoints = new float[MAX_TRY];
		for (int i = 0; i < MAX_TRY; i++) {
			Game game = new Game();
			game.run();
			boysHappinessPoints[i] = game.boysAverageHappiness;
			girlsHappinessPoints[i] = game.girlsAverageHappiness;
			allHappinessPoints[i] = game.allAverageHappiness;
			System.gc();
		}

		// println("boys happiness points:" + toString(boysHappinessPoints));
		// println("girls happiness points:" + toString(girlsHappinessPoints));
		// println("all happiness points:" + toString(allHappinessPoints));

		println(gameName + ":boys average happiness point:"
				+ average(boysHappinessPoints));
		println(gameName + ":girls average happiness point:"
				+ average(girlsHappinessPoints));
		println(gameName + ":all average happiness point:"
				+ average(allHappinessPoints));

	}

	private static float sum(float[] nums) {
		float sum = 0f;
		for (float num : nums) {
			sum += num;
		}
		return sum;
	}

	private static float average(float[] nums) {
		return sum(nums) / (float) nums.length;
	}

	// private static String toString(float[] arr) {
	// StringBuilder sb = new StringBuilder("[");
	// for (int i = 0; i < arr.length; i++) {
	// sb.append(arr[i]);
	// if (i != arr.length - 1) {
	// sb.append(", ");
	// }
	// }
	// sb.append("]");
	// return sb.toString();
	// }

	private void run() {
		allBoys = factory.createBoyList();
		allGirls = factory.createGirlList();
		allParticipators.addAll(allBoys);
		allParticipators.addAll(allGirls);

		for (Participator p : allParticipators) {
			if (p instanceof Boy) {
				p.createPreferenceList(allGirls);
			}
			if (p instanceof Girl) {
				p.createPreferenceList(allBoys);
			}
		}

		Collections.shuffle(allParticipators);

		if (printResultList)
			println("all participators:" + allParticipators);
		for (Participator p : allParticipators) {
			if (p instanceof Boy) {
				Boy boy = (Boy) p;
				boy.lookForLove();
			}
		}

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

	private void println(Participator p) {
		if (printResultList)
			System.out.println(p + "{myLove:" + p.myLove + ", happiness:"
					+ p.happiness() + ", blackName:" + p.blacknameList
					+ ", myPref:" + p.preferenceList + "}");

	}

	private static void println(Object o) {
		System.out.println(o);
	}

}
