package wyq.infrastructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * A properties auto inject supporter. Extend this class or use
 * <code>PropertySupporter.inject</code> method.
 * 
 * Override <code>PropertySupporter.convert</code> to customize property
 * converting.
 * 
 * @author dewafer
 * @version 0.3 2011/10/21
 * 
 */
public class PropertySupporter {

    private static final String PROPERTIES_PREFIX = "/";

    private static final String PROPERTIES_SUFFIX = ".properties";

    protected PropertySupporter() {
	Class<?> clz = this.getClass();
	if (!PropertySupporter.class.equals(clz)) {
	    inject(this);
	}
    }

    protected <T> PropertySupporter(T target, String pfile) {
	inject(target, getPropFileInputStream(target, pfile));
    }

    protected <T> PropertySupporter(String pfile) {
	inject(this, getPropFileInputStream(this, pfile));
    }

    protected <T> InputStream getPropFileInputStream(T target, String pfile) {
	File f = new File(pfile);
	if (!f.isAbsolute()) {
	    return target.getClass().getResourceAsStream(pfile);
	} else {
	    try {
		return new FileInputStream(f);
	    } catch (FileNotFoundException e) {
		e.printStackTrace();
		throw new RuntimeException(e);
	    }
	}
    }

    protected <T> PropertySupporter(T target, File pfile) {
	try {
	    inject(target, new FileInputStream(pfile));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    protected <T> PropertySupporter(T target) {
	inject(target);
    }

    public static <T> T inject(T target, InputStream in) {
	PropertySupporter ps = new PropertySupporter();
	return ps.doInject(target, in);
    }

    public static <T> T inject(T target) {
	Class<?> clz = target.getClass();
	String clzName = clz.getSimpleName();
	InputStream is = clz.getResourceAsStream(PROPERTIES_PREFIX + clzName
		+ PROPERTIES_SUFFIX);
	if (is == null) {
	    is = clz.getResourceAsStream(clzName + PROPERTIES_SUFFIX);
	}
	if (is != null) {
	    target = inject(target, is);
	}
	return target;
    }

    private <T> T doInject(T target, InputStream inStream) {
	Properties p = new Properties();
	try {
	    p.load(inStream);
	} catch (FileNotFoundException e) {
	    handleException(e);
	} catch (IOException e) {
	    handleException(e);
	}

	Class<?> clz = target.getClass();
	Field[] declaredFields = clz.getDeclaredFields();
	for (Field f : declaredFields) {
	    String propValue = null;
	    Object origValue = null;
	    try {
		String fieldName = f.getName();
		propValue = p.getProperty(fieldName);
		f.setAccessible(true);
		origValue = f.get(target);
		Object convertedValue = convert(propValue, origValue,
			f.getType());
		f.set(target, convertedValue);
	    } catch (IllegalArgumentException e) {
		handleException(e);
	    } catch (IllegalAccessException e) {
		handleException(e);
	    } catch (Exception e) {
		handleException(e);
	    }
	}

	return target;
    }

    protected Object convert(String propValue, Object origValue,
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

    protected void handleException(Exception e) {
	e.printStackTrace();
    }

}
