package wyq.infrastructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

/**
 * A properties auto inject supporter. Extend this class or use
 * <code>PropertySupporter.inject</code> method.
 * 
 * Override <code>PropertySupporter.getConvertor</code> and implements
 * <code>Convertor</code> to customize property converting.
 * 
 * @author dewafer
 * @version 0.5 2011/11/8
 * 
 */
public class PropertySupporter {

    private static final String PROPERTIES_PREFIX = "/";

    private static final String PROPERTIES_SUFFIX = ".properties";

    protected PropertySupporter() {
	Class<?> clz = this.getClass();
	if (!PropertySupporter.class.equals(clz)) {
	    inject(this, getConvertor());
	}
    }

    protected <T> PropertySupporter(T target, String pfile) {
	inject(target, getPropFileInputStream(target, pfile), getConvertor());
    }

    protected <T> PropertySupporter(String pfile) {
	inject(this, getPropFileInputStream(this, pfile), getConvertor());
    }

    protected <T> PropertySupporter(T target, File pfile) {
	try {
	    inject(target, new FileInputStream(pfile), getConvertor());
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    protected <T> PropertySupporter(T target) {
	inject(target, getConvertor());
    }

    public static <T> T inject(T target, InputStream in) {
	return inject(target, in, new DefaultConvertor());
    }

    public static <T> T inject(T target, InputStream in, Convertor convertor) {
	PropertySupporter ps = new PropertySupporter();
	return ps.doInject(target, in, convertor);
    }

    public static <T> T inject(T target) {
	return inject(target, getDefaultSource(target), new DefaultConvertor());
    }

    public static <T> T inject(T target, Convertor convertor) {
	return inject(target, getDefaultSource(target), convertor);
    }

    public static <T> InputStream getDefaultSource(T target) {
	Class<?> clz = target.getClass();
	String clzName = clz.getSimpleName();
	InputStream is = clz.getResourceAsStream(PROPERTIES_PREFIX + clzName
		+ PROPERTIES_SUFFIX);
	if (is == null) {
	    is = clz.getResourceAsStream(clzName + PROPERTIES_SUFFIX);
	}
	return is;
    }

    public static <T> InputStream getPropFileInputStream(T target, String pfile) {
	File f = new File(pfile);
	if (!f.isAbsolute()) {
	    InputStream is = target.getClass().getResourceAsStream(pfile);
	    if (is == null) {
		is = target.getClass().getResourceAsStream(
			PROPERTIES_PREFIX + pfile);
	    }
	    return is;
	} else {
	    try {
		return new FileInputStream(f);
	    } catch (FileNotFoundException e) {
		e.printStackTrace();
		throw new RuntimeException(e);
	    }
	}
    }

    protected Convertor getConvertor() {
	return new DefaultConvertor();
    }

    protected <T> T doInject(T target, InputStream inStream, Convertor convertor) {
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
		Object convertedValue = convertor.convert(propValue, origValue,
			f.getType());
		if (f.isAccessible() && !Modifier.isStatic(f.getModifiers())
			&& !Modifier.isFinal(f.getModifiers())) {
		    f.set(target, convertedValue);
		}
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

    protected void handleException(Exception e) {
	e.printStackTrace();
    }

}
