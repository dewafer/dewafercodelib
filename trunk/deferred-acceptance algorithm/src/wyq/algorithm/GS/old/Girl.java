package wyq.algorithm.GS.old;

public class Girl extends Participator {

	public boolean love(Participator boy) {
		print(name + ":" + boy);
		if (myLove == null) {
			myLove = boy;
			println(" is my first love, accept.");
			return true;
		} else {
			int myLoveIndex = this.preferenceList.indexOf(myLove);
			int newLoveIndex = this.preferenceList.indexOf(boy);

			if (newLoveIndex >= 0 && newLoveIndex < myLoveIndex) {
				println(" is better then my ex:" + myLove
						+ ", breakup with ex.");
				Boy ex = (Boy) myLove;
				blacknameList.add(ex);
				myLove = boy;
				ex.breakup();
				return true;
			} else {
				println(" is NO better then my bf:" + myLove + ", reject.");
				return false;
			}
		}
	}

}
