package wyq.appengine;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Property extends Properties implements Component {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2323409154240652900L;

    public static Property get() {
	return Repository.get("Property", Property.class);
    }

    public static Property get(String name) {
	return Repository.get(name, Property.class);
    }

    public static Property get(Class<?> c) {
	return Repository.get(c.getName(), Property.class);
    }

    protected Property(Properties p) {
	super(p);
    }

    protected Property(String p) {
	super();
	loadPropertyFile(p);
    }

    protected Property() {
	super();
	loadPropertyFile(this.getClass());
    }

    protected Property(Class<?> c) {
	loadPropertyFile(c);
    }

    protected void loadPropertyFile(String name) {
	loadPropertyFile(null, name);
    }

    protected void loadPropertyFile(Class<?> c) {
	loadPropertyFile(c, null);
    }

    protected void loadPropertyFile(Class<?> c, String name) {
	Class<?> clazz;
	if (c == null) {
	    clazz = this.getClass();
	} else {
	    clazz = c;
	}
	if (name == null) {
	    name = clazz.getSimpleName();
	}
	InputStream resourceAsStream = clazz.getResourceAsStream(name);
	if (resourceAsStream != null) {
	    try {
		loadAction(resourceAsStream);
	    } catch (IOException e) {
		throw new RuntimeException(e);
	    }
	}
    }

    public void load(String file) {
	loadPropertyFile(file);
    }

    public void load(Class<?> c) {
	loadPropertyFile(c);
    }

    protected void loadAction(InputStream in) throws IOException {
	load(in);
    }
}
