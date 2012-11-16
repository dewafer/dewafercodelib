package wyq.appengine.component.db;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Types {

	public static int getJDBCType(Class<?> c) {
		if (Void.TYPE.equals(c) || Void.class.equals(c)) {
			return java.sql.Types.NULL;
		} else if (String.class.equals(c)) {
			return java.sql.Types.VARCHAR;
		} else if (BigDecimal.class.equals(c)) {
			return java.sql.Types.NUMERIC;
		} else if (Boolean.TYPE.equals(c) || Boolean.class.equals(c)) {
			return java.sql.Types.BIT;
		} else if (Byte.TYPE.equals(c) || Byte.class.equals(c)) {
			return java.sql.Types.TINYINT;
		} else if (Short.TYPE.equals(c) || Short.class.equals(c)) {
			return java.sql.Types.SMALLINT;
		} else if (Integer.TYPE.equals(c) || Integer.class.equals(c)) {
			return java.sql.Types.INTEGER;
		} else if (Long.TYPE.equals(c) || Long.class.equals(c)) {
			return java.sql.Types.BIGINT;
		} else if (Float.TYPE.equals(c) || Float.class.equals(c)) {
			return java.sql.Types.REAL;
		} else if (Double.TYPE.equals(c) || Double.class.equals(c)) {
			return java.sql.Types.DOUBLE;
		} else if (c.isArray()
				&& (Byte.TYPE.equals(c.getComponentType()) || Byte.class
						.equals(c.getComponentType()))) {
			return java.sql.Types.VARBINARY;
		} else if (Date.class.equals(c)) {
			return java.sql.Types.DATE;
		} else if (Time.class.equals(c)) {
			return java.sql.Types.TIME;
		} else if (Timestamp.class.equals(c)) {
			return java.sql.Types.TIMESTAMP;
		} else {
			return java.sql.Types.OTHER;
		}
	}

	public static int getJDBCType(Object o) {
		if (o == null) {
			return getJDBCType(void.class);
		} else {
			return getJDBCType(o.getClass());
		}
	}

	public static Class<?> getJavaType(int sqlType) {
		if (sqlType == java.sql.Types.NULL) {
			return Void.class;
		} else if (sqlType == java.sql.Types.VARCHAR) {
			return String.class;
		} else if (sqlType == java.sql.Types.NUMERIC) {
			return BigDecimal.class;
		} else if (sqlType == java.sql.Types.BIT) {
			return Boolean.class;
		} else if (sqlType == java.sql.Types.TINYINT) {
			return Byte.class;
		} else if (sqlType == java.sql.Types.SMALLINT) {
			return Short.class;
		} else if (sqlType == java.sql.Types.INTEGER) {
			return Integer.class;
		} else if (sqlType == java.sql.Types.BIGINT) {
			return Long.class;
		} else if (sqlType == java.sql.Types.REAL) {
			return Float.class;
		} else if (sqlType == java.sql.Types.DOUBLE) {
			return Double.class;
		} else if (sqlType == java.sql.Types.VARBINARY) {
			return Byte[].class;
		} else if (sqlType == java.sql.Types.DATE) {
			return Date.class;
		} else if (sqlType == java.sql.Types.TIME) {
			return Time.class;
		} else if (sqlType == java.sql.Types.TIMESTAMP) {
			return Timestamp.class;
		} else {
			return Object.class;
		}
	}

}
