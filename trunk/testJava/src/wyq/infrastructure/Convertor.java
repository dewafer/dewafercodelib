package wyq.infrastructure;

public interface Convertor {

	public abstract Object convert(String propValue, Object origValue,
			Class<?> requiredType) throws Exception;

	public abstract Object convert(Object srcValue, Object origValue,
			Class<?> requiredType) throws Exception;
}
