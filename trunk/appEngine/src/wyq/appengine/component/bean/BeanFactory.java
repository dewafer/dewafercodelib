package wyq.appengine.component.bean;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import wyq.appengine.Convertor;
import wyq.appengine.FactoryParameter;
import wyq.appengine.component.AbstractFactory;
import wyq.appengine.component.bean.BeanFactory.BeanFactoryParameter;

public class BeanFactory extends AbstractFactory<Object, BeanFactoryParameter> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1073671067251961068L;

	private BeanDataSource dataSource;

	private Convertor convertor;

	@Override
	protected Object build(BeanFactoryParameter param) {
		Class<?> beanClass = param.beanClass;
		if (param.dataSource != null) {
			dataSource = param.dataSource;
		}

		// check bean class
		if (!beanClass.isAnnotationPresent(Bean.class)) {
			throw new RuntimeException(new IllegalArgumentException());
		}

		// get bean fields
		Map<String, Field> beanFields = new HashMap<String, Field>();
		for (Field f : beanClass.getDeclaredFields()) {
			if (f.isAnnotationPresent(BeanField.class)) {
				BeanField fieldMeta = f.getAnnotation(BeanField.class);
				String fieldName = fieldMeta.name();
				if (fieldName == null || fieldName.length() == 0) {
					fieldName = f.getName();
				}
				beanFields.put(fieldName, f);
			}
		}

		// create bean object
		Object bean = null;
		try {
			bean = beanClass.newInstance();
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}

		if (bean != null) {
			// set values
			for (Map.Entry<String, Field> entity : beanFields.entrySet()) {
				try {
					String fieldName = entity.getKey();
					Object value = dataSource.getValue(fieldName);
					Field f = entity.getValue();
					if (convertor != null) {
						value = convertor.convert(value, f.getType());
					}
					f.setAccessible(true);
					f.set(bean, value);
				} catch (Exception e) {
					exceptionHandler.handle(e);
				}
			}
		}
		return bean;
	}

	@Override
	protected Class<BeanFactoryParameter> factoryParamType() {
		return BeanFactoryParameter.class;
	}

	class BeanFactoryParameter implements FactoryParameter {

		private Class<?> beanClass;

		private BeanDataSource dataSource;

		protected BeanFactoryParameter(Class<?> beanClass,
				BeanDataSource dataSource) {
			this.beanClass = beanClass;
			this.dataSource = dataSource;
		}
	}

	public BeanDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BeanDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Convertor getConvertor() {
		return convertor;
	}

	public void setConvertor(Convertor convertor) {
		this.convertor = convertor;
	}

}
