package wyq.appengine.component.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wyq.appengine.Component;
import wyq.appengine.component.db.DBEngine.DBResult;

public class DaoResult implements Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 797784647329137009L;

	private List<List<Object>> resultList = new ArrayList<List<Object>>();

	private List<String> columnNames = new ArrayList<String>();

	private int updateCount;

	private boolean hasResult;

	private int current = -1;

	protected DaoResult(DBResult result) throws SQLException {
		loadData(result);
	}

	protected void loadData(DBResult result) throws SQLException {
		updateCount = result.getRowsCount();
		hasResult = result.hasResultSet();

		if (hasResult) {
			ResultSet resultSet = result.getResultSet();
			ResultSetMetaData metaData = resultSet.getMetaData();
			for (int i = 0; i < metaData.getColumnCount(); i++) {
				columnNames.add(metaData.getColumnLabel(i + 1));
			}
			while (resultSet.next()) {
				List<Object> resultValue = new ArrayList<Object>();
				for (int i = 0; i < columnNames.size(); i++) {
					resultValue.add(resultSet.getObject(i + 1));
				}
				resultList.add(resultValue);
			}
		}
	}

	public Object getValue(String columnName) {
		if (!hasResult)
			return null;
		if (!isOutRange(current, -1, resultList.size()))
			return null;
		int col = columnNames.indexOf(columnName);
		List<Object> currentRow = resultList.get(current);
		if (!isOutRange(col, -1, currentRow.size()))
			return null;
		return currentRow.get(col);
	}

	public Object getValue(int i) {
		if (!hasResult)
			return null;
		if (isOutRange(current, 0, resultList.size()))
			return null;
		List<Object> currentRow = resultList.get(current);
		if (isOutRange(i, 0, currentRow.size()))
			return null;
		return currentRow.get(i);
	}

	public String getColumnName(int i) {
		if (isOutRange(i, 0, columnNames.size()))
			return null;
		return columnNames.get(i);
	}

	public int getColumnCount() {
		return columnNames.size();
	}

	private boolean isOutRange(int num, int low, int max) {
		return num < low || max <= low;
	}

	public boolean next() {
		current++;
		if (current > resultList.size())
			current = resultList.size();
		return !isOutRange(current, 0, resultList.size());
	}

	public int size() {
		if (hasResult) {
			return resultList.size();
		} else {
			return updateCount;
		}
	}

	public boolean absolute(int row) {
		current = row;
		if (current > resultList.size())
			current = resultList.size();
		if (current < 0)
			current = -1;
		return !isOutRange(current, 0, resultList.size());
	}

	public void afterLast() {
		current = resultList.size();
	}

	public void beforeFirst() {
		current = -1;
	}

	public boolean first() {
		current = 0;
		return !isOutRange(current, 0, resultList.size());
	}

	public boolean isAfterLast() {
		return current == resultList.size();
	}

	public boolean isBeforeFirst() {
		return current == -1;
	}

	public boolean isFirst() {
		return current == 0;
	}

	public boolean isLast() {
		return current == resultList.size();
	}

	public boolean last() {
		current = resultList.size() - 1;
		return !isOutRange(current, 0, resultList.size());
	}

	public boolean previous() {
		current--;
		if (current < 0)
			current = -1;
		return !isOutRange(current, 0, resultList.size());
	}

	public boolean relative(int rows) {
		current += rows;
		if (current > resultList.size())
			current = resultList.size();
		if (current < 0)
			current = -1;
		return !isOutRange(current, 0, resultList.size());
	}

}
