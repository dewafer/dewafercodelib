package wyq.algorithm.GS.old;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Game().run();
	}

	private void run() {
		ParticipatorFactory factory = new ParticipatorFactory();

		List<Participator> allParticipators = new ArrayList<Participator>();
		List<Participator> allBoys = factory.createboyList();
		List<Participator> allGirls = factory.createGirlList();
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

		println("all participators:" + allParticipators);
		for (Participator p : allParticipators) {
			if (p instanceof Boy) {
				Boy boy = (Boy) p;
				boy.lookForLove();
			}
		}

		println("------------ GAME OVER : RESULT ------------");
		for (Participator p : allBoys) {
			println(p);
		}

		println("--------------------------------------------");
		for (Participator p : allGirls) {
			println(p);
		}
	}

	private void println(Participator p) {
		System.out.println(p + "{myLove:" + p.myLove + ", blackName:"
				+ p.blacknameList + ", myPref:" + p.preferenceList + "}");

	}

	private void println(Object o) {
		System.out.println(o);
	}

}
