package wyq.entity;

public class EntityImplGetter {

	@SuppressWarnings("unchecked")
	public static <T> T getImpl(Class<? super T> cls) {
		String entityName = cls.getName();
		String canonicalName = cls.getCanonicalName();
		String entity = canonicalName.replace(entityName, "impl." + entityName
				+ "EntityImpl");
		try {
			return (T) Class.forName(entity).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
