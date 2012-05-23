package wyq.infrastructure;

import java.lang.reflect.Method;
import java.sql.ResultSet;

public class DefaultDbBeanFactory implements BeanFactory {

	private Convertor convertor;

	public void setConvertor(Convertor convertor) {
		this.convertor = convertor;
	}

	@Override
	public Object produceResult(ResultSet rs, Class<?> daoClass,
			Method invokedMethod) {
		throw new RuntimeException();
	}

	@Override
	public Object produceResult(int updateCount, Class<?> daoClass,
			Method invokedMethod) {
		Class<?> returnType = invokedMethod.getReturnType();
		if (void.class.equals(returnType)) {
			return null;
		} else {
			Object convertedResult = null;
			try {
				convertedResult = convertor.convert(updateCount, null,
						returnType);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return convertedResult;
		}
	}

	@Override
	public Object produceResult(ResultSet rs, Class<?> rowType,
			Class<?> wrapperType) {
		// TODO implements
		// return type is null : no need of result;
		if (rowType == null) {
			return null;
		}

		if (wrapperType == null) {
			// only one record is needed
			// Object result = null;
			// try {
			// if (rs.next()) {
			// Object value = rs.getObject(0);
			// result = convertor.convert(value, value, rowType);
			// }
			// } catch (SQLException e) {
			// e.printStackTrace();
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// return result;
		} else {
			// lots of them
			// only one record is needed
			// List<Object> result = new ArrayList<Object>();
			// try {
			// while (rs.next()) {
			// Object value = rs.getObject(0);
			// result.add(convertor.convert(value, value, rowType));
			// }
			// } catch (SQLException e) {
			// e.printStackTrace();
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// return result;
		}

		return null;
	}

}
