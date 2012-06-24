package wyq.appengine;

public interface Factory<T> extends Component {

	public abstract T factory(FactoryParameter parameterObject);

	public abstract FactoryParameter prepare(Object... values);

	public abstract T manufacture(Object... values);

}