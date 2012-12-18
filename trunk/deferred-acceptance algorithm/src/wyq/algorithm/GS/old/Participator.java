package wyq.algorithm.GS.old;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class Participator {

	protected List<Participator> preferenceList = new ArrayList<Participator>();
	protected Iterator<Participator> iterator;
	private boolean printLog = true;

	public List<Participator> createPreferenceList(
			Collection<Participator> allObjects) {
		preferenceList.addAll(allObjects);
		Collections.shuffle(preferenceList);
		iterator = preferenceList.iterator();
		return preferenceList;
	}

	protected String name;
	protected Participator myLove = null;
	protected List<Participator> blacknameList = new ArrayList<Participator>();

	@Override
	public String toString() {
		return name;
	}

	protected void println(Object o) {
		if (printLog)
			System.out.println(o);
	}

	protected void print(Object o) {
		if (printLog)
			System.out.print(o);
	}

	public void setName(String name) {
		this.name = name;
	}
}
