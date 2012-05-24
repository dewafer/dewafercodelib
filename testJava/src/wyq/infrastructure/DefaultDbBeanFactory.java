package wyq.infrastructure;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DefaultDbBeanFactory implements BeanFactory {

	private Convertor convertor = new DefaultConvertor();

	public void setConvertor(Convertor convertor) {
		this.convertor = convertor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object produceWrapper(List<Object> beanList, Class<?> beanWrapperType) {
		Object wrapper = null;
		try {
			wrapper = beanWrapperType.newInstance();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		if (wrapper == null) {
			return null;
		}
		if (Collection.class.isAssignableFrom(beanWrapperType)) {
			@SuppressWarnings("rawtypes")
			Collection c = (Collection) wrapper;
			c.addAll(beanList);
		}
		return wrapper;
	}

	@Override
	public Object produceBean(Map<String, Object> mapBean, Class<?> beanType) {
		// TODO Auto-generated method stub
		if (isPrimitiveType(beanType)) {

			Object value = null;
			Iterator<Object> itr = mapBean.values().iterator();
			if (itr.hasNext()) {
				value = itr.next();
			}

			Object result = null;
			try {
				result = convertor.convert(value, value, beanType);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		} else {
			// inject bean
			return null;
		}
	}

	private boolean isPrimitiveType(Class<?> beanType) {
		if (String.class.equals(beanType)) {
			return true;
		} else if (beanType.isPrimitive()) {
			return true;
		} else {
			// 8 elements
			// Boolean.TYPE, Character.TYPE, Byte.TYPE, Short.TYPE,
			// Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE
			if (Boolean.class.equals(beanType)) {
				return true;
			}
			if (Character.class.equals(beanType)) {
				return true;
			}
			if (Byte.class.equals(beanType)) {
				return true;
			}
			if (Short.class.equals(beanType)) {
				return true;
			}
			if (Integer.class.equals(beanType)) {
				return true;
			}
			if (Long.class.equals(beanType)) {
				return true;
			}
			if (Float.class.equals(beanType)) {
				return true;
			}
			if (Double.class.equals(beanType)) {
				return true;
			}
			return false;
		}
	}

}
