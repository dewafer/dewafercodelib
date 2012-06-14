package wyq.infrastructure;

import java.lang.reflect.Method;

public interface DaoDbSupporter {

	public Object execute(String sql, Method invokedMethod, Class<?> daoClass,
			Object... params);
}
