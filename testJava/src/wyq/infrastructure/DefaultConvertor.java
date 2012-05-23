package wyq.infrastructure;

public class DefaultConvertor implements Convertor {

	@Override
	public Object convert(String propValue, Object origValue,
			Class<?> requiredType) {
		if (String.class.equals(requiredType)) {
			return propValue;
		} else if (!isPrimitive(requiredType)) {
			return origValue;
		} else {
			// 8 elements
			// Boolean.TYPE, Character.TYPE, Byte.TYPE, Short.TYPE,
			// Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE
			if (Boolean.TYPE.equals(requiredType)
					|| Boolean.class.equals(requiredType)) {
				return Boolean.parseBoolean(propValue);
			}
			if (Character.TYPE.equals(requiredType)
					|| Boolean.class.equals(requiredType)) {
				return propValue.toCharArray()[0];
			}
			if (Byte.TYPE.equals(requiredType)
					|| Byte.class.equals(requiredType)) {
				return Byte.parseByte(propValue);
			}
			if (Short.TYPE.equals(requiredType)
					|| Short.class.equals(requiredType)) {
				return Short.parseShort(propValue);
			}
			if (Integer.TYPE.equals(requiredType)
					|| Integer.class.equals(requiredType)) {
				return Integer.parseInt(propValue);
			}
			if (Long.TYPE.equals(requiredType)
					|| Long.class.equals(requiredType)) {
				return Long.parseLong(propValue);
			}
			if (Float.TYPE.equals(requiredType)
					|| Float.class.equals(requiredType)) {
				return Float.parseFloat(propValue);
			}
			if (Double.TYPE.equals(requiredType)
					|| Double.class.equals(requiredType)) {
				return Double.parseDouble(propValue);
			}
			return origValue;
		}
	}

	@Override
	public Object convert(Object srcValue, Object origValue,
			Class<?> requiredType) throws Exception {
		if (requiredType.isAssignableFrom(srcValue.getClass())) {
			return srcValue;
		} else {
			return convert(srcValue.toString(), origValue, requiredType);
		}
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
}