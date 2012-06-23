package wyq.appengine.component.db;

import java.sql.PreparedStatement;

import wyq.appengine.Component;
import wyq.appengine.component.db.DBEngine.DBResult;

public interface DBEngineHandler extends Component {

	public abstract void prepareParameter(PreparedStatement stmt);

	public abstract void processResult(DBResult result);

}
