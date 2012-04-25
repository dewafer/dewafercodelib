package wyq.infrastructure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import wyq.test.TestBean;

public class CopyOfInjectManager {

	public void setterInject(String fieldName, Object value,
			Class<?> typeOfValue, Object targetObject, Convertor convertor) {

		Class<? extends Object> targetObjCls = targetObject.getClass();
		// get set-method
		String setMethodName = "set" + fieldName;
		String getMethodName = "get" + fieldName;
		if (Boolean.TYPE.equals(typeOfValue)
				|| Boolean.class.equals(typeOfValue)) {
			getMethodName = "is" + fieldName;
		}

		List<Method> setMethods = new ArrayList<Method>();
		Method getMethod = null;
		try {
			Method setMethod = targetObjCls.getMethod(setMethodName,
					typeOfValue);
			setMethods.add(setMethod);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			if (isWrapperType(typeOfValue)) {
				typeOfValue = getPrimitiveType(typeOfValue);
				try {
					Method setMethod = targetObjCls.getMethod(setMethodName,
							typeOfValue);
					setMethods.add(setMethod);
				} catch (SecurityException e1) {
					e1.printStackTrace();
				} catch (NoSuchMethodException e1) {
					e1.printStackTrace();
				}
			}
		}
		try {
			getMethod = targetObjCls.getMethod(getMethodName);
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}

		if (setMethods.isEmpty()) {
			// another way to get setMethods
			Method[] allMethods = targetObjCls.getMethods();
			for (Method m : allMethods) {
				if (setMethodName.equals(m.getName())) {
					setMethods.add(m);
				}
			}
		}

		// run inject
		for (Method m : setMethods) {
			Class<?>[] parameterTypes = m.getParameterTypes();
			// convert value
			Object convertedValue = value;
			if (parameterTypes.length > 0 && convertor != null) {
				Object origValue = null;
				if (getMethod != null) {
					try {
						origValue = getMethod.invoke(targetObject);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				Class<?> requiredType = parameterTypes[0];
				try {
					convertedValue = convertor.convert(value, origValue,
							requiredType);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// invoke set method
			try {
				m.invoke(targetObject, convertedValue);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	public void setterInject(String fieldName, Object value,
			TestBean targetObject, Convertor c) {
		Class<?> typeOfValue = value.getClass();
		setterInject(fieldName, value, typeOfValue, targetObject, c);
	}

	private static final List<Class<?>> WRAPPER_TYPES = getWrapperTypes();
	private static final List<Class<?>> PRIMITIVE_TYPES = getPrimitiveTypes();

	private static boolean isWrapperType(Class<?> clazz) {
		return WRAPPER_TYPES.contains(clazz);
	}

	private static List<Class<?>> getPrimitiveTypes() {
		List<Class<?>> ret = new ArrayList<Class<?>>();
		ret.add(Boolean.TYPE);
		ret.add(Character.TYPE);
		ret.add(Byte.TYPE);
		ret.add(Short.TYPE);
		ret.add(Integer.TYPE);
		ret.add(Long.TYPE);
		ret.add(Float.TYPE);
		ret.add(Double.TYPE);
		ret.add(Void.TYPE);
		return ret;
	}

	private static List<Class<?>> getWrapperTypes() {
		List<Class<?>> ret = new ArrayList<Class<?>>();
		ret.add(Boolean.class);
		ret.add(Character.class);
		ret.add(Byte.class);
		ret.add(Short.class);
		ret.add(Integer.class);
		ret.add(Long.class);
		ret.add(Float.class);
		ret.add(Double.class);
		ret.add(Void.class);
		return ret;
	}

	private Class<?> getPrimitiveType(Class<?> type) {
		int pos = WRAPPER_TYPES.indexOf(type);
		return PRIMITIVE_TYPES.get(pos);
	}
}
