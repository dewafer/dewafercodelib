package wyq.appengine.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import wyq.appengine.Component;

public interface DBEngineHandler extends Component {

    public abstract void prepareParameter(PreparedStatement stmt)
	    throws SQLException;

    public abstract void processResult(ResultSet rs) throws SQLException;

    public abstract void processResult(int updateCount);

}
