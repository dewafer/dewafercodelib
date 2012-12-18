package wyq.algorithm.GS.sim;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Girl extends Participator {

	private BlockingQueue<LoveLetter> mailBox = new LinkedBlockingQueue<LoveLetter>();

	public Girl(String name) {
		super(name);
	}

	@Override
	public void run() {
		try {
			LoveLetter mail = null;
			while (!Thread.interrupted()) {
				LoveLetter newMail = mailBox.take();
				Boy boy = newMail.getWriter();
				println(this + ": received mail from " + boy);
				if (isBetter(boy, getMyLove())) {
					if (mail != null) {
						println(this + ": " + boy + " is better then my ex:"
								+ getMyLove());
						mail.reject();
					} else {
						println(this + ": " + boy + " is my first love...");
					}
					mail = newMail;
					setMyLove(boy);
				} else {
					println(this + ": " + boy + " is NO better then my ex:"
							+ getMyLove());
					newMail.reject();
					blackname(boy);
				}
				TimeUnit.MILLISECONDS.sleep(500 + rand.nextInt(500));
			}
			println(this + ": END...");
		} catch (InterruptedException e) {
			println(this + ": Interrupted...");
		}
	}

	public BlockingQueue<LoveLetter> getMailBox() {
		return mailBox;
	}

}
