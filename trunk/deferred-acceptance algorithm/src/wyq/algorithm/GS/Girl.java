package wyq.algorithm.GS;

public class Girl extends Participator {

	public boolean love(Participator boy) {
		log(name + ":" + boy);
		if (myLove == null) {
			myLove = boy;
			log(" is my first love, accept.");
			return true;
		} else {
			int myLoveIndex = this.preferenceList.indexOf(myLove);
			int newLoveIndex = this.preferenceList.indexOf(boy);

			if (newLoveIndex >= 0 && newLoveIndex < myLoveIndex) {
				logln(" is better then my ex:" + myLove + ", breakup with ex.");
				Boy ex = (Boy) myLove;
				blacknameList.add(ex);
				myLove = boy;
				ex.breakup();
				return true;
			} else {
				logln(" is NO better then my bf:" + myLove + ", reject.");
				return false;
			}
		}
	}

}
