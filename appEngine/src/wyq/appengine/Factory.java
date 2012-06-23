package wyq.appengine;

public interface Factory extends Component {

	public abstract Object factory(FactoryParameter parameterObject);

	public abstract FactoryParameter buildParameter(Object... values);

}