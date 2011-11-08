package wyq.tool.util;

import java.lang.reflect.Array;

import wyq.infrastructure.Convertor;
import wyq.infrastructure.DefaultConvertor;
import wyq.infrastructure.PropertySupporter;

public class PropertyInjector implements Convertor {

    private static final String SPLIT_REGEX = "\\s*,\\s*";
    private DefaultConvertor defConvertor = new DefaultConvertor();

    @Override
    public Object convert(String propValue, Object origValue,
	    Class<?> requiredType) throws IllegalAccessException,
	    InstantiationException {
	if (requiredType.isArray() && propValue != null
		&& propValue.length() > 0) {
	    Class<?> compType = requiredType.getComponentType();
	    String[] splitedPropValue = propValue.split(SPLIT_REGEX);
	    int length = splitedPropValue.length;
	    Object tmpArr = null;
	    tmpArr = Array.newInstance(compType, length);
	    for (int i = 0; i < length; i++) {
		Object convertedValue = defConvertor.convert(
			splitedPropValue[i], null, compType);
		Array.set(tmpArr, i, convertedValue);
	    }
	    return tmpArr;
	} else {
	    return defConvertor.convert(propValue, origValue, requiredType);
	}
    }

    public static <T> T doPropInject(T target, String propFile) {
	return PropertySupporter.inject(target,
		PropertySupporter.getPropFileInputStream(target, propFile),
		new PropertyInjector());
    }

    public static <T> T doPropInject(T target) {
	return PropertySupporter.inject(target,
		PropertySupporter.getDefaultSource(target),
		new PropertyInjector());
    }

}
