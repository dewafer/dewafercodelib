package wyq.infrastructure;

import java.lang.reflect.Method;
import java.sql.ResultSet;

public interface BeanFactory {

	Object produceResult(ResultSet rs, Class<?> daoClass, Method invokedMethod);

	Object produceResult(int updateCount, Class<?> daoClass,
			Method invokedMethod);

	Object produceResult(ResultSet rs, Class<?> rowType, Class<?> wrapperType);

}
