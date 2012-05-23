package wyq.infrastructure;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DefaultDbBeanFactory implements BeanFactory {

	private Convertor convertor;

	public void setConvertor(Convertor convertor) {
		this.convertor = convertor;
	}

	@Override
	public Object produceResult(ResultSet rs, Class<?> daoClass,
			Method invokedMethod) {
		Class<?> returnType = invokedMethod.getReturnType();
		// return type is void
		if (void.class.equals(returnType)) {
			return null;
		}

		// return type is String or primitive types
		if (isPrimitive((Class<?>) returnType)) {
			Object result;
			Object convertedResult = null;
			try {
				result = rs.getObject(1);
				convertedResult = convertor.convert(result, result, returnType);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return convertedResult;
		}

		// return type is Array
		Class<?> componentTypeOfArr = returnType.getComponentType();
		if (returnType.isArray()) {
			if (isPrimitive(componentTypeOfArr)) {
				// with primitive types

			} else {
				// with Beans

			}
		}

		// return type is List(generic type)
		if (List.class.equals(returnType)) {
			ParameterizedType genericType = (ParameterizedType) invokedMethod
					.getGenericReturnType();
			Class<?> realType = (Class<?>) genericType.getActualTypeArguments()[0];
			if (isPrimitive(realType)) {
				// with primitive types

			} else {
				// with Beans

			}
		}
		// 7.return type is only a bean

		// 8.other situations
		return null;
	}

	private boolean isPrimitive(Class<?> returnClass) {
		if (String.class.equals(returnClass)) {
			return true;
		} else if (returnClass.isPrimitive()) {
			return true;
		} else {
			// 8 elements
			// Boolean.TYPE, Character.TYPE, Byte.TYPE, Short.TYPE,
			// Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE
			if (Boolean.class.equals(returnClass)) {
				return true;
			}
			if (Character.class.equals(returnClass)) {
				return true;
			}
			if (Byte.class.equals(returnClass)) {
				return true;
			}
			if (Short.class.equals(returnClass)) {
				return true;
			}
			if (Integer.class.equals(returnClass)) {
				return true;
			}
			if (Long.class.equals(returnClass)) {
				return true;
			}
			if (Float.class.equals(returnClass)) {
				return true;
			}
			if (Double.class.equals(returnClass)) {
				return true;
			}
			return false;
		}

	}

	@Override
	public Object produceResult(int updateCount, Class<?> daoClass,
			Method invokedMethod) {
		Class<?> returnType = invokedMethod.getReturnType();
		if (void.class.equals(returnType)) {
			return null;
		} else {
			Object convertedResult = null;
			try {
				convertedResult = convertor.convert(updateCount, null,
						returnType);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return convertedResult;
		}
	}

}
