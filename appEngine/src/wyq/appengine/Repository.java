package wyq.appengine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import wyq.appengine.Factory.FactoryParameter;

public class Repository implements Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1903248669068799516L;

	private static Repository res = new Repository();

	private Factory factory;

	private Map<RepositoryKeyEntry, Component> compPool = new HashMap<RepositoryKeyEntry, Component>();

	private String repositorySaveFile;
	private String usingFactory;

	public static Component get(String name) {
		return get(name, null);
	}

	public static <T extends Component> T get(Class<T> cls) {
		return get(null, cls);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Component> T get(String name, Class<T> cls) {
		Component c = res.findComponent(name, cls);
		if (c == null) {
			c = res.loadComponent(name, cls);
		}
		return (T) c;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Component> T put(String name, Class<T> cls,
			Component c) {
		if (c == null) {
			throw new RuntimeException("Can NOT put null component!");
		}
		return (T) res.register(c, name, cls);
	}

	public static Component find(String name) {
		return find(name, null);
	}

	public static <T extends Component> T find(Class<T> cls) {
		return find(null, cls);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Component> T find(String name, Class<T> cls) {
		return (T) res.findComponent(name, cls);
	}

	public static <T extends Component> boolean contains(String name,
			Class<T> cls) {
		return res.containsKey(name, cls);
	}

	public static boolean contains(Component c) {
		return res.containsValue(c);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Component> T release(String name, Class<T> cls) {
		return (T) res.remove(name, cls);
	}

	public static void save() {
		try {
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
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					res.repositorySaveFile));
			res = (Repository) ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected Repository() {
		Property p = new Property("/repository.properties");
		repositorySaveFile = p.getProperty("repositorySaveFile");
		usingFactory = p.getProperty("usingFactory");

		p = new Property("/conf.properties");
		register(p, "Property", Property.class);
		register(this, "Repository", Repository.class);
	}

	protected Component findComponent(String name,
			Class<? extends Component> cls) {
		RepositoryKeyEntry key = new RepositoryKeyEntry(name, cls);
		return compPool.get(key);
	}

	protected Component loadComponent(String name,
			Class<? extends Component> cls) {
		Component component = null;
		if (factory == null) {
			loadFactory();
		}
		component = (Component) factory
				.factory(new FactoryParameter(name, cls));
		register(component, name, cls);
		return component;
	}

	protected void loadFactory() {
		try {
			Class<?> fclass = Class.forName(usingFactory);
			factory = (Factory) fclass.newInstance();
			register(factory, "Factory", Factory.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Component register(Component c, String name,
			Class<? extends Component> cls) {
		if (c == null) {
			throw new RuntimeException("Register null component!");
		}
		if (name == null && cls == null) {
			throw new RuntimeException("Wrong arguments! NullPointException!");
		}
		RepositoryKeyEntry keyEntry = new RepositoryKeyEntry(name, cls);
		return compPool.put(keyEntry, c);
	}

	protected boolean containsKey(String name, Class<? extends Component> cls) {
		return containsKey(new RepositoryKeyEntry(name, cls));
	}

	protected boolean containsKey(RepositoryKeyEntry key) {
		return compPool.containsKey(key);
	}

	protected boolean containsValue(Component value) {
		return compPool.containsValue(value);
	}

	protected Component remove(String name, Class<? extends Component> cls) {
		RepositoryKeyEntry key = new RepositoryKeyEntry(name, cls);
		return remove(key);
	}

	protected Component remove(RepositoryKeyEntry key) {
		return compPool.remove(key);
	}

	private class RepositoryKeyEntry implements Component {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2494953274083461053L;

		public RepositoryKeyEntry(String name, Class<?> entryClass) {
			super();
			this.name = name;
			this.entryClass = entryClass;
		}

		private String name;
		private Class<?> entryClass;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((entryClass == null) ? 0 : entryClass.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			RepositoryKeyEntry other = (RepositoryKeyEntry) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (entryClass == null) {
				if (other.entryClass != null)
					return false;
			} else if (!entryClass.equals(other.entryClass))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		private Repository getOuterType() {
			return Repository.this;
		}

	}
}
