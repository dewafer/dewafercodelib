package wyq.appengine.test;

import wyq.appengine.component.AbstractFactory;

public class NewFactory extends AbstractFactory<Object, NewFactoryParam> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4801390439149219868L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NewFactory f = new NewFactory();
		System.out.println(f.manufacture("value1", 99));
	}

	@Override
	protected Object build(NewFactoryParam param) {
		return param.getV1() + param.getV2();
	}

	@Override
	protected int paramLength() {
		return 2;
	}

	@Override
	protected Class<?>[] paramTypes() {
		return new Class<?>[] { String.class, int.class };
	}

	private class NParam2 extends NewFactoryParam {

		private NParam2(String v1, int v2) {
			super(v1, v2);
		}

	}

	@Override
	protected Class<? extends NewFactoryParam> factoryParamType() {
		return NParam2.class;
	}
}
