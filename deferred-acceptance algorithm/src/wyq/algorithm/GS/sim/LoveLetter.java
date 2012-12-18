package wyq.algorithm.GS.sim;

public class LoveLetter {

	private Boy writer;
	private boolean rejected = false;

	public LoveLetter(Boy writer) {
		this.writer = writer;
	}

	public Boy getWriter() {
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
}
