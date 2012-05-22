package wyq.infrastructure;

import java.lang.reflect.Type;

public interface DaoDbSupporter {

	public Object execute(String sql, Type returnType, Object... params);
}
