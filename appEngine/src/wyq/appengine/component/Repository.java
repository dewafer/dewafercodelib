package wyq.appengine.component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import wyq.appengine.Component;
import wyq.appengine.ExceptionHandler;
import wyq.appengine.Factory;

/**
 * This is the Repository. Get components from this Repository.
 * 
 * @version 1
 * @author dewafer
 * 
 */
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

	/**
	 * Same as {@code get(name, null)}.
	 * 
	 * @see wyq.appengine.component.Repository#get(String, Class)
	 * @param name
	 * @return
	 */
	public static Component get(String name) {
		return get(name, null);
	}

	/**
	 * Same as {@code get(null, class}.
	 * 
	 * @see wyq.appengine.component.Repository#get(String, Class)
	 * @param cls
	 * @return
	 */
	public static <T extends Component> T get(Class<T> cls) {
		return get(null, cls);
	}

	/**
	 * Get a component. If the component doesn't exist, load it first.
	 * 
	 * @param name
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Component> T get(String name, Class<T> cls) {
		Component c = res.findComponent(name, cls);
		if (c == null) {
			c = res.loadComponent(name, cls);
		}
		return (T) c;
	}

	/**
	 * Put a component into the Repository. Can NOT put null into it.
	 * 
	 * @param name
	 * @param cls
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Component> T put(String name, Class<T> cls,
			Component c) {
		return (T) res.register(c, name, cls);
	}

	/**
	 * Same as {@code find(name, null)}
	 * 
	 * @see wyq.appengine.component.Repository#find(String, Class)
	 * @param name
	 * @return
	 */
	public static Component find(String name) {
		return find(name, null);
	}

	/**
	 * Same as {@code find(null, class)}
	 * 
	 * @see wyq.appengine.component.Repository#find(String, Class)
	 * @param cls
	 * @return
	 */
	public static <T extends Component> T find(Class<T> cls) {
		return find(null, cls);
	}

	/**
	 * Get a component. Won't load it if does not exist.
	 * 
	 * @see wyq.appengine.component.Repository#findComponent(String, Class)
	 * @param name
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Component> T find(String name, Class<T> cls) {
		return (T) res.findComponent(name, cls);
	}

	/**
	 * See
	 * {@link wyq.appengine.component.Repository#hasComponent(String, Class)}
	 * 
	 * @param name
	 * @param cls
	 * @return
	 */
	public static <T extends Component> boolean contains(String name,
			Class<T> cls) {
		return res.hasComponent(name, cls);
	}

	/**
	 * See {@link wyq.appengine.component.Repository#hasComponent(Component)}
	 * 
	 * @param c
	 * @return
	 */
	public static boolean contains(Component c) {
		return res.hasComponent(c);
	}

	/**
	 * see {@link wyq.appengine.component.Repository#unregister(String, Class)}
	 * 
	 * @param name
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Component> T release(String name, Class<T> cls) {
		return (T) res.unregister(name, cls);
	}

	/**
	 * @deprecated
	 */
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

	/**
	 * @deprecated
	 */
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

		// Can't load factory at instance initialization
		// loadFactory();
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

	protected Component loadComponent(String name,
			Class<? extends Component> cls) {

		// lazy factory load
		if (factory == null) {
			loadFactory();
			// if the user is finding factory
			Component theFactory = findComponent(name, cls);
			if (theFactory != null) {
				return theFactory;
			}
		}

		Component component = factory.manufacture(name, cls);
		if (component != null) {
			register(component, name, cls);
			component = initFactory.manufacture(component);
		}

		return component;
	}

	/**
	 * See
	 * {@link wyq.appengine.component.ComponentPool#register(Component, String, Class)}
	 * 
	 * @param name
	 * @param cls
	 * @return
	 */
	protected Component register(Component c, String name,
			Class<? extends Component> cls) {
		return compPool.register(c, name, cls);
	}

	/**
	 * See {@link wyq.appengine.component.ComponentPool#retrieve(String, Class)}
	 * 
	 * @param name
	 * @param cls
	 * @return
	 */
	protected Component findComponent(String name,
			Class<? extends Component> cls) {
		return compPool.retrieve(name, cls);
	}

	/**
	 * See {@link wyq.appengine.component.ComponentPool#contains(String, Class)}
	 * 
	 * @param name
	 * @param cls
	 * @return
	 */
	protected boolean hasComponent(String name, Class<? extends Component> cls) {
		return compPool.contains(name, cls);
	}

	/**
	 * See {@link wyq.appengine.component.ComponentPool#contains(Component)}
	 * 
	 * @param c
	 * @return
	 */
	protected boolean hasComponent(Component c) {
		return compPool.contains(c);
	}

	/**
	 * See
	 * {@link wyq.appengine.component.ComponentPool#unregister(String, Class)}
	 * 
	 * @param name
	 * @param cls
	 * @return
	 */
	protected Component unregister(String name, Class<? extends Component> cls) {
		return compPool.unregister(name, cls);
	}

}
