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

	private Factory factory;

	private Map<RepositoryKeyEntry, Component> compPool = new HashMap<RepositoryKeyEntry, Component>();

	private String repositorySaveFile;

	protected Repository() {
		Property p = Property.get("/repository.properties");
		repositorySaveFile = p.getProperty("repositorySaveFile");

		p = Property.get("/conf.properties");
		register(p, "Property", Property.class);
		register(this, "Repository", Repository.class);
	}

	public static Component get(String name) {
		return res.loadComponent(name);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Component> T get(Class<T> cls) {
		return (T) res.loadComponent(cls);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Component> T get(String name, Class<T> cls) {
		return (T) res.loadComponent(name, cls);
	}

	protected Component loadComponent(String name) {
		return loadComponent(name, null);
	}

	protected Component loadComponent(Class<? extends Component> cls) {
		return loadComponent(null, cls);
	}

	protected Component loadComponent(String name,
			Class<? extends Component> cls) {
		RepositoryKeyEntry key = new RepositoryKeyEntry(name, cls);
		Component component = compPool.get(key);
		if (component == null) {
			if (factory == null) {
				loadFactory();
			}
			component = factory.factory(name, cls);
			register(component, name, cls);
		}
		return component;
	}

	protected void loadFactory() {
		factory = new Factory();
	}

	protected void register(Component c, String name, Class<?> cls) {
		if (c == null) {
			throw new RuntimeException("Register null component!");
		}
		if (name == null && cls == null) {
			throw new RuntimeException("Wrong arguments! NullPointException!");
		}
		RepositoryKeyEntry keyEntry = new RepositoryKeyEntry(name, cls);
		compPool.put(keyEntry, c);
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
