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
	List<DBItem> list = openXlsAndGetDBItems(getResFilePath(xls_file));

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
		if (isDel) {
		    value = "IS NULL";
		} else {
		    value = "NULL";
		}
	    } else {
		if (!isNumeric(value)) {
		    value = "'" + value + "'";
		}
		if (isDel) {
		    value = " = " + value;
		}
	    }

	    String replaceMark = isDel ? "/\\*VALUE\\*/" : "\\?";
	    sql = sql.replaceFirst(replaceMark, value);
	}
	return sql;
    }

    private boolean isNumeric(String value) {
	return Pattern.matches("^[1-9]+\\d*$", value);
    }

    @Override
    protected String getDeleteSql(DBItem item) {
	StringBuilder sb = new StringBuilder("DELETE FROM ");
	sb.append(item.tableName);
	sb.append(" WHERE ");
	for (String colName : item.cols) {
	    // for null compare
	    sb.append(colName + " /*VALUE*/ ");
	    if (item.cols.lastIndexOf(colName) != item.cols.size() - 1) {
		sb.append(" AND ");
	    }
	}
	return sb.toString();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	ProcessorRunner.run(DataInjectorSQLCreator.class, args);
    }
}
