package wyq.ui;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import wyq.test.TestBean;

public class MyTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 361040467694716979L;

	private List<? extends Object> dataList = new ArrayList<Object>();
	private Class<? extends Object> classType;
	private List<String> fieldNameList;
	private List<Class<?>> fieldClassList;

	public MyTableModel(List<? extends Object> dataList) {
		this.dataList = dataList;
		// check there is only one type in the list
		Class<? extends Object> type = null;
		for (Object b : dataList) {
			if (type == null) {
				type = b.getClass();
			}
			if (!type.equals(b.getClass())
					&& !b.getClass().isAssignableFrom(type)
					&& !type.getClass().isAssignableFrom(b.getClass())) {
				throw new RuntimeException("Too many types!");
			}
		}
		classType = type;
		List<Method> getMethods = getMethodsStartWith("get");
		List<Method> setMethods = getMethodsStartWith("set");
		for (Method m : getMethods) {
			String fieldName = m.getName().substring(3);
			Class<?> fieldType = m.getReturnType();
//			if()
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
//		return fields.get(columnIndex);
		return null;
	}

	@Override
	public String getColumnName(int column) {
//		return fields.get(column).getName();
		return null;
	}

	private List<Method> getMethodsStartWith(String prefix) {
		Method[] methods = classType.getMethods();
		List<Method> getMethods = new ArrayList<Method>();
		for (Method m : methods) {
			if (m.getName().startsWith(prefix)) {
				getMethods.add(m);
			}
		}
		return getMethods;
	}

	@Override
	public int getColumnCount() {
		return 0;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		TestBean bean = new TestBean();
		List<TestBean> list = new ArrayList<TestBean>();
		list.add(bean);
		new MyTableModel(list);
	}

}
