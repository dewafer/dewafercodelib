package wyq.appengine.component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import wyq.appengine.Component;
import wyq.appengine.ExceptionHandler;
import wyq.appengine.Factory;
import wyq.appengine.FactoryParameter;

public class Repository implements Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1903248669068799516L;

	private static Repository res = new Repository();

	private String repositorySaveFile;
	private String usingFactory;
	private String usingInitFactory;
	private ComponentPool compPool = new ComponentPool();
	private Factory<Component> factory;
	private Factory<Component> initFactory;

	private ExceptionHandler exceptionHandler;

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
		Property p = new Property("/conf.properties");
		repositorySaveFile = p.getProperty("Repository.repositorySaveFile");
		usingFactory = p.getProperty("Repository.usingFactory");
		usingInitFactory = p.getProperty("Repository.usingInitFactory");

		String exceptionHandlerName = p
				.getProperty("Repository.ExceptionHandler");
		try {
			exceptionHandler = (ExceptionHandler) Class.forName(
					exceptionHandlerName).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		register(exceptionHandler, "ExceptionHandler", ExceptionHandler.class);
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
		FactoryParameter param = factory.prepare(name, cls);
		component = (Component) factory.factory(param);

		if (component != null) {
			register(component, name, cls);
			initComponent(component);
		}
		return component;
	}

	protected void initComponent(Component component) {
		component = initFactory.manufacture(component);
	}

	@SuppressWarnings("unchecked")
	protected void loadFactory() {
		try {
			Class<?> fclass = Class.forName(usingFactory);
			factory = (Factory<Component>) fclass.newInstance();
			register(factory, "Factory", Factory.class);

			initFactory = (Factory<Component>) factory
					.manufacture(usingInitFactory);
			register(initFactory, "initFactory", Factory.class);
		} catch (Exception e) {
			exceptionHandler.handle(e);
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

	private class ComponentPool {
		private Map<RepositoryKeyEntry, Component> compPool = new HashMap<RepositoryKeyEntry, Component>();

		public boolean containsKey(Object arg0) {
			return compPool.containsKey(arg0);
		}

		public boolean containsValue(Object arg0) {
			return compPool.containsValue(arg0);
		}

		public Component get(Object arg0) {
			return compPool.get(arg0);
		}

		public Component put(RepositoryKeyEntry arg0, Component arg1) {
			return compPool.put(arg0, arg1);
		}

		public Component remove(Object arg0) {
			return compPool.remove(arg0);
		}
	}
}
