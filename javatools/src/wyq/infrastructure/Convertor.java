package wyq.infrastructure;

public interface Convertor {

    public abstract Object convert(String propValue, Object origValue,
	    Class<?> requiredType) throws Exception;
}
