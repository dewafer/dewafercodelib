package wyq.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {

	private class DataMetaModel {
		private boolean editable;
		private String name;
		private Class<?> type;
	}

	private static final Class<?>[] PRIMITIVE_ARRAY = { Boolean.TYPE,
			Character.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE,
			Float.TYPE, Double.TYPE };

	private static final Class<?>[] PRIMITIVE_MAP = { Boolean.class,
			Character.class, Byte.class, Short.class, Integer.class,
			Long.class, Float.class, Double.class };

	private static final long serialVersionUID = 361040467694716979L;

	private Class<? extends Object> classType;

	private List<? extends Object> dataList = new ArrayList<Object>();

	private List<DataMetaModel> dataMetaModelList;

	private static final String[] IGNORE_METHOD_NAME_LIST = { "getClass" };

	private List<String> ignoreMethodNameList = new ArrayList<String>(
			Arrays.asList(IGNORE_METHOD_NAME_LIST));

	public MyTableModel(List<? extends Object> dataList,
			String... ignoreMethodNames) {
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
		if (ignoreMethodNames != null && ignoreMethodNames.length > 0) {
			Collections.addAll(this.ignoreMethodNameList, ignoreMethodNames);
		}
		dataMetaModelList = getDataMetaModelList();
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
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Class<?> fieldType = dataMetaModelList.get(columnIndex).type;
		if (fieldType.isPrimitive()) {
			fieldType = convertPrimitive(fieldType);
		}
		return fieldType;
	}

	@Override
	public int getColumnCount() {
		return dataMetaModelList.size();
	}

	@Override
	public String getColumnName(int column) {
		return dataMetaModelList.get(column).name;
	}

	private List<DataMetaModel> getDataMetaModelList() {
		List<DataMetaModel> dataMetaModelList = new ArrayList<DataMetaModel>();
		List<Method> getMethods = getMethodsStartWith("get", "is");
		for (Method m : getMethods) {
			String methodName = m.getName();
			if (ignoreMethodNameList.contains(methodName)) {
				continue;
			}
			String fieldName = "";
			if (methodName.startsWith("get")) {
				fieldName = methodName.substring(3);
			} else {
				fieldName = methodName.substring(2);
			}
			Class<?> fieldType = m.getReturnType();
			DataMetaModel model = new DataMetaModel();
			model.name = fieldName;
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
	public int getRowCount() {
		return dataList.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		DataMetaModel meta;
		meta = dataMetaModelList.get(arg1);
		String colName = meta.name;
		Object o = dataList.get(arg0);
		Method getMethod;
		Object value = null;
		String methodRealName = null;
		Class<?> typeOfValue = meta.type;
		if (Boolean.TYPE.equals(typeOfValue)
				|| Boolean.class.equals(typeOfValue)) {
			methodRealName = "is" + colName;
		} else {
			methodRealName = "get" + colName;
		}
		try {
			getMethod = classType.getMethod(methodRealName);
			value = getMethod.invoke(o);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return value;
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return dataMetaModelList.get(arg1).editable;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		DataMetaModel meta;
		meta = dataMetaModelList.get(columnIndex);
		String colName = meta.name;
		Object o = dataList.get(rowIndex);
		Method getMethod;
		String methodRealName = "set" + colName;
		Class<?> typeOfValue = meta.type;
		try {
			getMethod = classType.getMethod(methodRealName, typeOfValue);
			getMethod.invoke(o, aValue);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
