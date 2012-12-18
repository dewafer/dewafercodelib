package wyq.algorithm.GS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class Participator {

	protected List<Participator> preferenceList = new ArrayList<Participator>();

	public List<Participator> getPreferenceList() {
		return preferenceList;
	}

	protected Iterator<Participator> iterator;
	protected static boolean printLog = true;

	public List<Participator> createPreferenceList(
			Collection<? extends Participator> allObjects) {
		preferenceList.addAll(allObjects);
		Collections.shuffle(preferenceList);
		iterator = preferenceList.iterator();
		return preferenceList;
	}

	protected String name;
	protected Participator myLove = null;

	public synchronized Participator getMyLove() {
		return myLove;
	}

	public synchronized void setMyLove(Participator myLove) {
		this.myLove = myLove;
	}

	protected List<Participator> blacknameList = new ArrayList<Participator>();

	public synchronized List<Participator> getBlacknameList() {
		return blacknameList;
	}

	@Override
	public String toString() {
		return name;
	}

	protected void println(Object o) {
		System.out.println(o);
	}

	protected void logln(Object o) {
		if (printLog)
			println(o);
	}

	protected void log(Object o) {
		if (printLog)
			print(o);
	}

	protected void print(Object o) {
		System.out.print(o);
	}

	public void setName(String name) {
		this.name = name;
	}

	public float happiness() {
		return (preferenceList.contains(myLove)) ? ((float) (preferenceList
				.size() - preferenceList.indexOf(myLove)) / (float) preferenceList
				.size()) * 100f
				: 0f;
	}
}
