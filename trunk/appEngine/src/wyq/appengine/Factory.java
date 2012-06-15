package wyq.appengine;

public interface Factory {

	public abstract Component factory(String name, Class<?> c);

}