package wyq.appengine.test;

import wyq.appengine.component.Property;
import wyq.appengine.component.Repository;
import wyq.appengine.component.bean.Bean;
import wyq.appengine.component.bean.BeanDataSource;
import wyq.appengine.component.bean.BeanFactory;
import wyq.appengine.component.bean.BeanField;

@Bean
public class BeanFactoryTest implements BeanDataSource {

	@BeanField(value = "beanfield1")
	private String beanfield1;

	@BeanField(value = "beanfield1")
	private String beanfield2;

	@Override
	public Object getValue(String fieldName) {
		// TODO Auto-generated method stub
		return "#Value:" + fieldName + "#";
	}

	@Override
	public String toString() {
		return "BeanFactoryTest [beanfield1=" + beanfield1 + ", beanfield2="
				+ beanfield2 + "]";
	}

	public static void main(String[] args) {
		Property.get().put("wyq.appengine.component.bean.BeanDataSource.impl",
				BeanFactoryNewDataSource.class.getName());
		BeanFactory f = Repository.get(BeanFactory.class);

		System.out.println(f.manufacture(BeanFactoryTest.class, null));

		System.out.println(f.manufacture(BeanFactoryTest.class,
				new BeanFactoryTest()));

		Repository.release(null, BeanFactory.class);
		f = Repository.get(BeanFactory.class);

		Property.get().put("wyq.appengine.component.bean.BeanDataSource.impl",
				NDS2.class.getName());

		System.out.println(f.manufacture(BeanFactoryTest.class, null));

	}

	public static class BeanFactoryNewDataSource implements BeanDataSource {

		@Override
		public Object getValue(String fieldName) {
			return "#ValueFromNewDS:" + fieldName + "#";
		}

	}

	public static class NDS2 extends BeanFactoryNewDataSource {
		@Override
		public Object getValue(String fieldName) {
			return "HHHHH" + super.getValue(fieldName) + "HHHH";
		}
	}
}
