package wyq.appengine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Repository implements Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1903248669068799516L;

	private static Repository res = new Repository();

	private Map<String, Component> compNamePool = new HashMap<String, Component>();
	private Map<Class<?>, Component> compClsPool = new HashMap<Class<?>, Component>();

	private String defaultPackageName;

	private String repositorySaveFile;

	protected Repository() {
		Property p = new Property("/conf.properties");
		register(p, "Property", Property.class);
		defaultPackageName = p.getProperty("defaultPackageName");
		register(this, "Repository", Repository.class);
		repositorySaveFile = p.getProperty("repositorySaveFile");
	}

	public static Component getComponent(String name) {
		return res.loadComponent(name);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Component> T getComponent(Class<T> cls) {
		return (T) res.loadComponent(cls);
	}

	protected Component loadComponent(String name) {
		Component component = compNamePool.get(name);
		if (component == null) {
			component = initComp(name, null);
		}
		return component;
	}

	protected Component loadComponent(Class<? extends Component> cls) {
		Component component = compClsPool.get(cls);
		if (component == null) {
			component = initComp(null, cls);
		}
		return component;
	}

	protected Component initComp(String name, Class<?> c) {
		try {
			String keyName;
			Class<?> keyCls;
			Component comp;

			if (c != null) {
				keyCls = c;
			} else {
				String fullName;
				if (defaultPackageName != null
						&& defaultPackageName.length() > 0) {
					fullName = defaultPackageName + "." + name;
				} else {
					fullName = name;
				}

				try {
					keyCls = Class.forName(fullName);
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(e);
				}
			}

			keyName = keyCls.getSimpleName();

			comp = (Component) keyCls.newInstance();
			register(comp, keyName, keyCls);

			return comp;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void register(Component c, String name, Class<?> cls) {
		compNamePool.put(name, c);
		compClsPool.put(cls, c);
	}

	public static void save() {
		try {
			// XMLEncoder encoder = new XMLEncoder(new FileOutputStream(
			// res.repositorySaveFile));
			// encoder.writeObject(res);
			// encoder.flush();
			// encoder.close();
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(res.repositorySaveFile));
			oos.writeObject(res);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void load() {
		try {
			// XMLDecoder decoder = new XMLDecoder(new FileInputStream(
			// res.repositorySaveFile));
			// res = (Repository)decoder.readObject();
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					res.repositorySaveFile));
			res = (Repository) ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
			// throw new RuntimeException(e);
		}
	}
}
