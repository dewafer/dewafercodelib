package wyq.tool.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import wyq.infrastructure.FileTool;
import wyq.tool.util.Processor.InjectProperty;
import wyq.tool.util.ProcessorRunner;

@InjectProperty
public class DataInjectorSQLCreator extends DataInjector {

    private String sql_file_path;
    // xls file to input
    private String xls_file;
    // delete data before insert ?
    private boolean delete_data_before_insert;

    private FileTool tool = new FileTool();

    @Override
    public void process(String[] args) throws Exception {
	// read the xls file
	List<DBItem> list = openXlsAndGetDBItems(xls_file);

	// prepare sql from the xls file
	prepareSqlForDBItems(list);

	createSqls(list);

	tool.openExplorer(sql_file_path);
    }

    protected void createSqls(List<DBItem> list) throws IOException {
	StringBuilder insertSql = new StringBuilder();
	StringBuilder deleteSql = new StringBuilder();
	for (DBItem item : list) {
	    StringBuilder insSqlsb = new StringBuilder();
	    StringBuilder delSqlsb = new StringBuilder();
	    List<String> cols = item.cols;
	    String delSql = null;
	    if (this.delete_data_before_insert) {
		delSql = this.getDeleteSql(item);
	    }
	    String insSql = this.getInsertSql(item);
	    for (Map<String, String> row : item.rows) {
		List<String> values = new ArrayList<String>();
		for (String colName : cols) {
		    String value = row.get(colName);
		    values.add(value);
		}
		if (this.delete_data_before_insert) {
		    delSqlsb.append(setValues(values, delSql, true));
		    delSqlsb.append(System.getProperty("line.separator"));
		}
		insSqlsb.append(setValues(values, insSql, false));
		insSqlsb.append(System.getProperty("line.separator"));
	    }
	    insertSql.append(insSqlsb);
	    deleteSql.append(delSqlsb);
	}

	// write file
	tool.writeTxtFile(insertSql.toString(), sql_file_path
		+ "\\insert_sqls.txt");
	if (delete_data_before_insert) {
	    tool.writeTxtFile(deleteSql.toString(), sql_file_path
		    + "\\delete_sqls.txt");
	}
    }

    private String setValues(List<String> values, String sql, boolean isDel) {
	for (String value : values) {
	    if (value == null || value.length() == 0) {
		value = "NULL";
	    } else if (!isNumeric(value)) {
		value = "'" + value + "'";
	    }
	    sql = sql.replaceFirst("\\?", value);
	    if (isDel) {
		sql = sql.replaceFirst("\\?", value);
	    }
	}
	return sql;
    }

    private boolean isNumeric(String value) {
	return Pattern.matches("^\\d+$", value);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	ProcessorRunner.run(DataInjectorSQLCreator.class, args);
    }
}
