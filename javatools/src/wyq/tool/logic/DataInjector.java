package wyq.tool.logic;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import wyq.infrastructure.DBSupporter;
import wyq.tool.util.AbstractProcessor;
import wyq.tool.util.Processor.InjectProperty;
import wyq.tool.util.ProcessorRunner;

@InjectProperty
public class DataInjector extends AbstractProcessor {

    // xls file to input
    private String xls_file;
    // delete data before insert ?
    private boolean delete_data_before_insert;
    private String db_provider_class;
    private String db_conn_str;

    @Override
    public void process(String[] args) throws Exception {
	// read the xls file
	InputStream is = new FileInputStream(xls_file);
	HSSFWorkbook workbook = new HSSFWorkbook(is);

	int numberOfSheets = workbook.getNumberOfSheets();
	List<DBItem> list = new ArrayList<DBItem>();
	for (int i = 0; i < numberOfSheets; i++) {
	    HSSFSheet sheet = workbook.getSheetAt(i);
	    DBItem item = new DBItem();
	    // sheet name as table name
	    item.tableName = sheet.getSheetName();

	    Iterator<Row> rowIterator = sheet.rowIterator();
	    int firstRowNum = sheet.getFirstRowNum();
	    while (rowIterator.hasNext()) {
		HSSFRow row = (HSSFRow) rowIterator.next();
		if (firstRowNum == row.getRowNum()) {
		    // first row as columns definition
		    Iterator<Cell> cellIterator = row.cellIterator();
		    while (cellIterator.hasNext()) {
			HSSFCell cell = (HSSFCell) cellIterator.next();
			item.cols.add(cell.getStringCellValue());
		    }
		} else {
		    // after the first row are the data
		    Iterator<Cell> cellIterator = row.cellIterator();
		    int cellCount = 0;
		    Map<String, String> dbrow = new HashMap<String, String>();
		    while (cellIterator.hasNext()) {
			HSSFCell cell = (HSSFCell) cellIterator.next();
			String colName = item.cols.get(cellCount);
			String celValue = cell.getStringCellValue();
			dbrow.put(colName, celValue);
			cellCount++;
		    }
		    item.rows.add(dbrow);
		}
	    }
	    list.add(item);
	}
	// prepare sql from the xls file
	if (delete_data_before_insert) {
	    for (DBItem item : list) {
		item.DeleteSql = getDeleteSql(item);
	    }
	}

	for (DBItem item : list) {
	    item.InsertSql = getInsertSql(item);
	}

	// prepare DBContorller instance
	DBController controller = new DBController();

	// execute sql
	for (DBItem item : list) {
	    if (delete_data_before_insert) {
		controller.goDel(item);
	    }
	    controller.goInsert(item);
	}
	controller.close();
    }

    private String getDeleteSql(DBItem item) {
	StringBuilder sb = new StringBuilder("DELETE FROM ");
	sb.append(item.tableName);
	sb.append(" WHERE ");
	for (String colName : item.cols) {
	    // for null compare
	    sb.append(colName + " = ? OR (" + colName
		    + " IS NULL AND ? IS NULL)");
	    if (item.cols.lastIndexOf(colName) != item.cols.size() - 1) {
		sb.append(" AND ");
	    }
	}
	return sb.toString();
    }

    private String getInsertSql(DBItem item) {
	StringBuilder sb = new StringBuilder("INSERT INTO ");
	sb.append(item.tableName + " ( ");
	StringBuilder values = new StringBuilder(" VALUES ( ");
	for (String colName : item.cols) {
	    sb.append(colName);
	    values.append(" ? ");
	    if (item.cols.lastIndexOf(colName) != item.cols.size() - 1) {
		sb.append(" , ");
		values.append(" , ");
	    }
	}
	sb.append(" )");
	values.append(" )");
	sb.append(values);
	return sb.toString();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	ProcessorRunner.run(DataInjector.class, args);
    }

    private class DBItem {
	private String tableName;
	private List<String> cols = new ArrayList<String>();
	private List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
	private String InsertSql;
	private String DeleteSql;
    }

    private class DBController extends DBSupporter {

	public DBController() throws ClassNotFoundException, SQLException {
	    connect();
	}

	@Override
	protected String getSqlConnProviderClass() {
	    return DataInjector.this.db_provider_class;
	}

	@Override
	protected String getConnStr() {
	    return DataInjector.this.db_conn_str;
	}

	private DBItem item;

	@Override
	protected void prepareParameter(PreparedStatement stmt)
		throws SQLException {
	    stmt.getConnection().setAutoCommit(false);
	    ParameterMetaData metaData = stmt.getParameterMetaData();
	    for (int i = 0; i < item.rows.size(); i++) {
		Map<String, String> row = item.rows.get(i);
		for (int j = 0; j < item.cols.size(); j++) {
		    String colName = item.cols.get(j);
		    String value = row.get(colName);
		    int sqlType = metaData.getParameterType(j + 1);
		    stmt.setObject(j + 1, value, sqlType);
		    // for NULL compare
		    if (isDelete) {
			stmt.setObject(j + 1, value, sqlType);
		    }
		}
		stmt.addBatch();
	    }
	}

	@Override
	protected void processResult(ResultSet rs) throws SQLException {
	    // Do nothing.
	}

	@Override
	protected void afterSqlExecuted(int updateCount) {
	    rowsAffected = updateCount;
	}

	int rowsAffected = 0;
	boolean isDelete = false;

	public int goDel(DBItem item) throws SQLException {
	    return go(item, true);
	}

	public int goInsert(DBItem item) throws SQLException {
	    return go(item, false);
	}

	private int go(DBItem item, boolean isDel) throws SQLException {
	    rowsAffected = 0;
	    this.item = item;
	    this.isDelete = isDel;
	    String sql;
	    if (isDel) {
		sql = item.DeleteSql;
	    } else {
		sql = item.InsertSql;
	    }
	    this.useExecuteBatch = true;
	    executeSQL(sql);
	    return rowsAffected;
	}

	public void close() throws SQLException {
	    super.close();
	}

    }
}
