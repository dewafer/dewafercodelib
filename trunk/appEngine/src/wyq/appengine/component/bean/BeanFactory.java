package wyq.appengine.component.bean;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import wyq.appengine.ExceptionHandler;
import wyq.appengine.Factory;
import wyq.appengine.FactoryParameter;

public class BeanFactory implements Factory<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1073671067251961068L;

	private ExceptionHandler exceptionHandler;

	@Override
	public Object factory(FactoryParameter parameterObject) {
		if (parameterObject instanceof BeanFactoryParameter) {
			BeanFactoryParameter param = (BeanFactoryParameter) parameterObject;
			Class<?> beanClass = param.getBeanClass();
			BeanDataSource dataSource = param.getDataSource();

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
					String fieldName = entity.getKey();
					Object value = dataSource.getValue(fieldName);
					Field f = entity.getValue();
					f.setAccessible(true);
					try {
						f.set(bean, value);
					} catch (Exception e) {
						exceptionHandler.handle(e);
					}
				}
			}
			return bean;
		} else {
			throw new RuntimeException(new IllegalArgumentException());
		}
	}

	@Override
	public FactoryParameter buildParameter(Object... values) {
		if (values == null || values.length != 2) {
			throw new IllegalArgumentException();
		}
		Class<?> beanClass = (Class<?>) values[0];
		BeanDataSource source = (BeanDataSource) values[1];
		return new BeanFactoryParameter(beanClass, source);
	}

	public class BeanFactoryParameter implements FactoryParameter {

		private Class<?> beanClass;

		private BeanDataSource dataSource;

		public Class<?> getBeanClass() {
			return beanClass;
		}

		public void setBeanClass(Class<?> beanClass) {
			this.beanClass = beanClass;
		}

		public BeanDataSource getDataSource() {
			return dataSource;
		}

		public void setDataSource(BeanDataSource dataSource) {
			this.dataSource = dataSource;
		}

		protected BeanFactoryParameter(Class<?> beanClass,
				BeanDataSource dataSource) {
			this.beanClass = beanClass;
			this.dataSource = dataSource;
		}
	}

	public ExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

}
