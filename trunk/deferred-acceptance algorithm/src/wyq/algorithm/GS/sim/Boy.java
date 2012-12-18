package wyq.algorithm.GS.sim;

import java.util.concurrent.TimeUnit;

public class Boy extends Participator {

	public Boy(String name) {
		super(name);
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Girl girl = (Girl) next();
				println(this + ": request love from " + girl);
				setMyLove(girl);
				LoveLetter mail = new LoveLetter(this);
				girl.getMailBox().put(mail);

				while (!mail.isRejected()) {
					println(this + ": waiting for " + girl + "'s reply...");
					synchronized (this) {
						wait();
					}
				}
				if (mail.isRejected()) {
					println(this + ": " + girl + " rejected me.");
					setMyLove(null);
					blackname(girl);
				}
				TimeUnit.MILLISECONDS.sleep(300 + rand.nextInt(500));
			}
			println(this + ": END...");
		} catch (InterruptedException e) {
			println(this + ": Interrupted...");
		}
	}

}
