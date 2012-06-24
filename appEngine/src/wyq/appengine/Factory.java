package wyq.appengine;

public interface Factory<T> extends Component {

	public abstract T factory(FactoryParameter parameterObject);

	public abstract FactoryParameter buildParameter(Object... values);

}