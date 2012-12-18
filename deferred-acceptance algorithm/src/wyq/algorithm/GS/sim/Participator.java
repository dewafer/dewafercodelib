package wyq.algorithm.GS.sim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public abstract class Participator implements Runnable {

	private boolean printLog = true;

	private String name;
	private Participator myLove = null;
	private BlockingQueue<SortableParticipator> preference = new PriorityBlockingQueue<SortableParticipator>();
	private Map<Participator, SortableParticipator> scoreMap = Collections
			.synchronizedMap(new HashMap<Participator, SortableParticipator>());

	private List<Participator> blacknameList = new ArrayList<Participator>();

	protected Random rand = new Random();

	public Participator(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public synchronized void meet(Participator e) throws InterruptedException {
		int score = scoreParticipator(e);
		SortableParticipator o = new SortableParticipator(e, score);
		scoreMap.put(e, o);
		preference.put(o);
	}

	protected int scoreParticipator(Participator e) {
		return rand.nextInt(preference.size() + 1);
	}

	protected boolean isBetter(Participator a, Participator b) {
		SortableParticipator as = scoreMap.get(a);
		SortableParticipator bs = scoreMap.get(b);
		if (as == null)
			return false;

		if (bs == null)
			return true;

		return as.compareTo(bs) > 0;

	}

	protected Participator next() throws InterruptedException {
		return preference.take().participator;
	}

	protected synchronized boolean blackname(Participator p) {
		return this.blacknameList.add(p);
	}

	public synchronized Participator getMyLove() {
		return myLove;
	}

	protected synchronized void setMyLove(Participator myLove) {
		this.myLove = myLove;
	}

	public synchronized List<Participator> getBlacknameList() {
		return blacknameList;
	}

	public synchronized BlockingQueue<SortableParticipator> getMyPreference() {
		return preference;
	}

	class SortableParticipator implements Comparable<SortableParticipator> {

		private Participator participator;
		private int score;

		public SortableParticipator(Participator participator, int score) {
			this.participator = participator;
			this.score = score;
		}

		@Override
		public int compareTo(SortableParticipator o) {
			return score - o.score;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof SortableParticipator) {
				return this.participator
						.equals(((SortableParticipator) obj).participator);
			} else {
				return super.equals(obj);
			}
		}

		@Override
		public String toString() {
			return participator.toString();
		}
	}

	protected void println(Object o) {
		if (printLog)
			System.out.println(o);
	}

	protected void print(Object o) {
		if (printLog)
			System.out.print(o);
	}
}
