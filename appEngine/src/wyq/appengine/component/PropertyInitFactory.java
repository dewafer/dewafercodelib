package wyq.appengine.component;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import wyq.appengine.Convertor;
import wyq.appengine.FactoryParameter;
import wyq.appengine.PropertyField;
import wyq.appengine.component.PropertyInitFactory.PropertyInitFactoryParam;

public class PropertyInitFactory extends
		AbstractFactory<Object, PropertyInitFactoryParam> {

	private Convertor convertor;
	/**
	 * 
	 */
	private static final long serialVersionUID = -1740371298282094781L;

	@Override
	protected Object build(PropertyInitFactoryParam fparam) {
		Object component = fparam.compObject;

		if (component == null) {
			return null;
		}

		// ignore Proxy
		if (Proxy.isProxyClass(component.getClass())) {
			return component;
		}

		Class<?> compClass = component.getClass();
		for (Field f : compClass.getDeclaredFields()) {
			PropertyField meta = f.getAnnotation(PropertyField.class);
			if (meta != null && meta.ignore()) {
				continue;
			}
			String propName = f.getName();
			if (meta != null && meta.name() != null && meta.name().length() > 0) {
				propName = meta.name();
			}
			String propValue;
			if (fparam.prop != null) {
				propValue = fparam.prop.getProperty(propName);
			} else {
				propValue = Property.get().getProperty(propName);
			}

			if (propValue != null) {
				try {
					Class<? extends Object> targetValueClass = f.getType();
					Object tarValue = propValue;
					if (convertor != null) {
						tarValue = convertor.convert(propValue,
								targetValueClass);
					}
					f.setAccessible(true);
					f.set(component, tarValue);
				} catch (Exception e) {
					exceptionHandler.handle(e);
				}
			}

		}
		return component;
	}

	@Override
	protected Class<? extends PropertyInitFactoryParam> factoryParamType() {
		return PropertyInitFactoryParam.class;
	}

	public Convertor getConvertor() {
		return convertor;
	}

	public void setConvertor(Convertor convertor) {
		this.convertor = convertor;
	}

	class PropertyInitFactoryParam implements FactoryParameter {
		private Object compObject;
		private Property prop;

		public PropertyInitFactoryParam(Object compObject) {
			this.compObject = compObject;
			this.prop = null;
		}

		public PropertyInitFactoryParam(Object compObject, Property prop) {
			this.compObject = compObject;
			this.prop = prop;
		}

	}

}
