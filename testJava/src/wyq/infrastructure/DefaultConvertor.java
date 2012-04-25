package wyq.infrastructure;

public class DefaultConvertor implements Convertor {

	@Override
	public Object convert(String propValue, Object origValue,
			Class<?> requiredType) {
		if (String.class.equals(requiredType)) {
			return propValue;
		} else if (!requiredType.isPrimitive()) {
			return origValue;
		} else {
			// 8 elements
			// Boolean.TYPE, Character.TYPE, Byte.TYPE, Short.TYPE,
			// Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE
			if (Boolean.TYPE.equals(requiredType)) {
				return Boolean.parseBoolean(propValue);
			}
			if (Character.TYPE.equals(requiredType)) {
				return propValue.toCharArray()[0];
			}
			if (Byte.TYPE.equals(requiredType)) {
				return Byte.parseByte(propValue);
			}
			if (Short.TYPE.equals(requiredType)) {
				return Short.parseShort(propValue);
			}
			if (Integer.TYPE.equals(requiredType)) {
				return Integer.parseInt(propValue);
			}
			if (Long.TYPE.equals(requiredType)) {
				return Long.parseLong(propValue);
			}
			if (Float.TYPE.equals(requiredType)) {
				return Float.parseFloat(propValue);
			}
			if (Double.TYPE.equals(requiredType)) {
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

}