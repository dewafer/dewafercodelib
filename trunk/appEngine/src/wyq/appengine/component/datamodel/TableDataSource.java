package wyq.appengine.component.datamodel;

public interface TableDataSource {

	boolean nextColumn() throws Exception;

	String getColumnName() throws Exception;

	boolean nextRow() throws Exception;

	Class<?> getColumnType() throws Exception;

	boolean nextRowValue() throws Exception;

	Object getRowValue() throws Exception;

}
