package wyq.algorithm.GS.mulitthread;

import wyq.algorithm.GS.Participator;

public class LoveLetter {

	private Participator writer;
	private boolean rejected = false;

	public LoveLetter(Participator writer) {
		this.writer = writer;
	}

	public synchronized Participator getWriter() {
		return writer;
	}

	public synchronized boolean isRejected() {
		return rejected;
	}

	public synchronized void reject() {
		rejected = true;
		synchronized (writer) {
			writer.notify();
		}
	}

	public synchronized String toString() {
		return "LoveLetter[writer:" + writer + ", rejected:" + rejected;
	}
}
