package wyq.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 361040467694716979L;

	private List<? extends Object> dataList = new ArrayList<Object>();
	private Class<? extends Object> classType;
	private List<DataMetaModel> dataMetaModelList;

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
		dataMetaModelList = getDataMetaModelList();
	}

	private List<DataMetaModel> getDataMetaModelList() {
		List<DataMetaModel> dataMetaModelList = new ArrayList<DataMetaModel>();
		List<Method> getMethods = getMethodsStartWith("get", "is");
		// List<Method> setMethods = getMethodsStartWith("set");
		for (Method m : getMethods) {
			String fieldName = m.getName().substring(3);
			Class<?> fieldType = m.getReturnType();
			DataMetaModel model = new DataMetaModel();
			model.name = fieldName;
			if (fieldType.isPrimitive()) {
				fieldType = convertPrimitive(fieldType);
			}
			model.type = fieldType;
			try {
				classType.getMethod("set" + fieldName, fieldType);
				model.editable = true;
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				model.editable = false;
			}
			dataMetaModelList.add(model);
		}
		return dataMetaModelList;
	}

	private Class<?> convertPrimitive(Class<?> primType) {
		// 8 elements
		// Boolean.TYPE, Character.TYPE, Byte.TYPE, Short.TYPE,
		// Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE
		for (int i = 0; i < PRIMITIVE_ARRAY.length; i++) {
			if (PRIMITIVE_ARRAY[i].equals(primType)) {
				return PRIMITIVE_MAP[i];
			}
		}
		return String.class;
	}

	private static final Class<?>[] PRIMITIVE_MAP = { Boolean.class,
			Character.class, Byte.class, Short.class, Integer.class,
			Long.class, Float.class, Double.class };

	private static final Class<?>[] PRIMITIVE_ARRAY = { Boolean.TYPE,
			Character.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE,
			Float.TYPE, Double.TYPE };

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// synchronized (dataMetaModelList) {
		// return String.class;
		return dataMetaModelList.get(columnIndex).type;
		// }
	}

	@Override
	public String getColumnName(int column) {
		// synchronized (dataMetaModelList) {
		// return "col";
		return dataMetaModelList.get(column).name;
		// }
	}

	private List<Method> getMethodsStartWith(String... prefixes) {
		Method[] methods = classType.getMethods();
		List<Method> getMethods = new ArrayList<Method>();
		for (Method m : methods) {
			for (String prefix : prefixes) {
				String mName = m.getName();
				if (mName.startsWith(prefix) && !getMethods.contains(m)) {
					getMethods.add(m);
					break;
				}
			}
		}
		return getMethods;
	}

	@Override
	public int getColumnCount() {
		// synchronized (dataMetaModelList) {
		// return 5;
		return dataMetaModelList.size();
		// }
	}

	@Override
	public int getRowCount() {
		// synchronized (dataList) {
		// return 3;
		return dataList.size();
		// }
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		DataMetaModel meta;
		// synchronized (dataMetaModelList) {
		meta = dataMetaModelList.get(arg1);
		// }
		String colName = meta.name;
		Class<?> type = meta.type;
		Object o;
		// synchronized (dataList) {
		o = dataList.get(arg0);
		// }
		Method getMethod;
		Object value = null;
		try {
			getMethod = classType.getMethod("get" + colName);
			value = getMethod.invoke(o);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			try {
				getMethod = classType.getMethod("is" + colName);
				value = getMethod.invoke(o);
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				e.printStackTrace();
			} catch (IllegalAccessException e1) {
				e.printStackTrace();
			} catch (InvocationTargetException e1) {
				e.printStackTrace();
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return type.cast(value);
		// return "test";
	}

	private class DataMetaModel {
		private String name;
		private Class<?> type;
		private boolean editable;
	}

}
