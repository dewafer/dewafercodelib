package wyq.appengine;

public interface Factory {

	public static class FactoryParameter {
		private String componentName;
		private Class<?> componentClass;

		public FactoryParameter(String componentName, Class<?> componentClass) {
			this.componentName = componentName;
			this.componentClass = componentClass;
		}

		public String getComponentName() {
			return componentName;
		}

		public void setComponentName(String componentName) {
			this.componentName = componentName;
		}

		public Class<?> getComponentClass() {
			return componentClass;
		}

		public void setComponentClass(Class<?> componentClass) {
			this.componentClass = componentClass;
		}
	}

	public abstract Component factory(FactoryParameter parameterObject);

}