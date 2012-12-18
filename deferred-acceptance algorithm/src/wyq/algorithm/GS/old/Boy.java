package wyq.algorithm.GS.old;

public class Boy extends Participator {

	public void lookForLove() {
		while (iterator.hasNext()) {
			Girl girl = (Girl) iterator.next();
			println(name + ": request love from " + girl);
			myLove = girl;
			if (girl.love(this)) {
				println(name + ":" + girl + " accepted me!");
				return;
			} else {
				println(name + ":" + girl + " rejected me.");
				blacknameList.add(girl);
			}
		}
		myLove = null;
		println(name + ": no more girls.");
	}

	public void breakup() {
		println(name + ": broke up with ex:" + myLove + ", look for love.");
		blacknameList.add(myLove);
		myLove = null;
		lookForLove();
	}
}
