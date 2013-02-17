package wyq.algorithm.GS.mulitthread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import wyq.algorithm.GS.Participator;

public class Girl2 extends Participator implements Runnable {

	private BlockingQueue<LoveLetter> mailBox = new LinkedBlockingQueue<LoveLetter>();

	public BlockingQueue<LoveLetter> getMailBox() {
		return mailBox;
	}

	@Override
	public void run() {
		try {
			LoveLetter maxpreferred = null;
			while (!Thread.interrupted()) {
				LoveLetter mail = mailBox.take();
				if (maxpreferred == null) {
					maxpreferred = mail;
					Boy2 boy = (Boy2) maxpreferred.getWriter();
					logln(this + ": boy:" + boy + " is my first love.");
					setMyLove(boy);
				} else {
					if (isBetter(mail, maxpreferred)) {
						Boy2 ex = (Boy2) maxpreferred.getWriter();
						Boy2 boy = (Boy2) mail.getWriter();
						logln(this + ": boy:" + boy + " is better than my ex:"
								+ ex + " reject ex and accept him.");
						maxpreferred.reject();
						maxpreferred = mail;
						setMyLove(boy);
						this.getBlacknameList().add(ex);
					} else {
						Boy2 boy = (Boy2) mail.getWriter();
						logln(this + ": boy:" + boy
								+ " is NO better than my bf:" + getMyLove()
								+ ", reject.");
						mail.reject();
						this.getBlacknameList().add(boy);
					}
				}
			}
		} catch (InterruptedException e) {
			logln(this + ": interrupted...");
		}
	}

	private boolean isBetter(LoveLetter a, LoveLetter b) {
		int ascore = this.getPreferenceList().indexOf(a);
		int bscore = this.getPreferenceList().indexOf(b);

		return ascore >= 0 && ascore < bscore;

	}

}
