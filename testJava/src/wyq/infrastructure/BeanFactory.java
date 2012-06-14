package wyq.infrastructure;

import java.util.List;
import java.util.Map;

public interface BeanFactory {

	Object produceWrapper(List<Object> beanList, Class<?> beanWrapperType);

	Object produceBean(Map<String, Object> mapBean, Class<?> beanType);

}
