package wyq.appengine;

import wyq.appengine.component.Repository;

public abstract class AbstractApp {

	public AbstractApp() {
		Repository.get("initFactory", Factory.class).manufacture(this);
	}
}
