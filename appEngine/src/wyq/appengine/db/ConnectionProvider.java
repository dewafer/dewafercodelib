package wyq.appengine.db;

import wyq.appengine.Component;

public interface ConnectionProvider extends Component {
	public abstract String getSqlConnProviderClass();

	public abstract String getConnStr();

	public abstract String getUser();

	public abstract String getPassword();
}
