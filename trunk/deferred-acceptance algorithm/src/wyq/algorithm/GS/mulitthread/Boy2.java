package wyq.algorithm.GS.mulitthread;

import wyq.algorithm.GS.Participator;

public class Boy2 extends Participator implements Runnable {

	@Override
	public void run() {
		try {
			while (!Thread.interrupted() && iterator.hasNext()) {
				Girl2 girl = (Girl2) iterator.next();
				logln(this + ": pursuit girl:" + girl);
				setMyLove(girl);
				LoveLetter mail = new LoveLetter(this);
				girl.getMailBox().offer(mail);
				while (!mail.isRejected()) {
					logln(this + ": i'm waitting for girl:" + girl + "...");
					synchronized (this) {
						wait();
					}
				}
				if (mail.isRejected()) {
					logln(this + ": rejected by girl:" + girl
							+ ", pursuit next...");
					this.getBlacknameList().add(girl);
				}
			}
			if (!iterator.hasNext()) {
				logln(this + ": no more girls...");
			}
		} catch (InterruptedException e) {
			logln(this + ": interrupted...");
		}
	}

}
