package wyq.appengine.component;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import wyq.appengine.ExceptionHandler;
import wyq.appengine.Factory;
import wyq.appengine.FactoryParameter;

public abstract class AbstractFactory<T, R extends FactoryParameter> implements
		Factory<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2836098297625275172L;

	protected ExceptionHandler exceptionHandler;

	@Override
	public T factory(FactoryParameter parameterObject) {
		if (factoryParamType().isInstance(parameterObject)) {
			return build(factoryParamType().cast(parameterObject));
		} else {
			throw new IllegalArgumentException();
		}
	}

	protected abstract T build(R param);

	@Override
	public FactoryParameter prepare(Object... values) {
		if (values == null || values.length != paramLength()) {
			throw new IllegalArgumentException();
		}
		Class<?> fparamType = factoryParamType();
		Class<?>[] fpConsTypes = paramTypes();

		Constructor<?> constructor;
		FactoryParameter p = null;
		Object[] initValues = values;
		try {
			if (fparamType.isMemberClass()) {
				List<Class<?>> fpConsTypeList = new ArrayList<Class<?>>();
				Collections.addAll(fpConsTypeList, paramTypes());
				fpConsTypeList.add(0, this.getClass());
				fpConsTypes = fpConsTypeList.toArray(fpConsTypes);

				List<Object> initValueList = new ArrayList<Object>();
				Collections.addAll(initValueList, values);
				initValueList.add(0, this);
				initValues = initValueList.toArray(initValues);
			}
			constructor = fparamType.getDeclaredConstructor(fpConsTypes);
			constructor.setAccessible(true);
			p = (FactoryParameter) constructor.newInstance(initValues);
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}
		return p;
	}

	protected abstract int paramLength();

	protected abstract Class<?>[] paramTypes();

	protected abstract Class<? extends R> factoryParamType();

	@Override
	public T manufacture(Object... values) {
		return factory(prepare(values));
	}

	public ExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}
}
