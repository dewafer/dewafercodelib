package wyq.algorithm.GS;

public class Boy extends Participator {

	public void lookForLove() {
		while (iterator.hasNext()) {
			Girl girl = (Girl) iterator.next();
			logln(name + ": request love from " + girl);
			myLove = girl;
			if (girl.love(this)) {
				logln(name + ":" + girl + " accepted me!");
				return;
			} else {
				logln(name + ":" + girl + " rejected me.");
				blacknameList.add(girl);
			}
		}
		myLove = null;
		logln(name + ": no more girls.");
	}

	public void breakup() {
		logln(name + ": broke up with ex:" + myLove + ", look for love.");
		blacknameList.add(myLove);
		myLove = null;
		lookForLove();
	}
}
