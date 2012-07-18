package wyq.appengine.component.file;

import java.io.File;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wyq.appengine.component.datamodel.Table;
import wyq.appengine.component.datamodel.TableDataSource;

public class CsvFile extends TextFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9154391434304983457L;

	public CsvFile(File parent, String child) {
		super(parent, child);
	}

	public CsvFile(String parent, String child) {
		super(parent, child);
	}

	public CsvFile(URI uri) {
		super(uri);
	}

	public CsvFile() {
		super();
	}

	public CsvFile(Class<?> c) {
		super(c);
	}

	public CsvFile(String name) {
		super(name);
	}

	public CsvFile(Class<?> c, String name) {
		super(c, name);
	}

	public Table readAllCsv() throws Exception {
		return new CsvTable(this);
	}

	static class CsvTable extends Table implements TableDataSource {

		private static final String SPLITER_REPLACEMENT = "\nÅC\n";

		private static final String SPLITER = ",";

		private static final String REPLACE_PATTERN = "\"[^\"]*,+[^\"]*\"";

		/**
		 * 
		 */
		private static final long serialVersionUID = 108613880044309630L;

		CsvFile file;
		String[] columns;
		String currentLine;
		String[] currentRow;
		int col = -1;
		int row = -1;

		public CsvTable(CsvFile csvFile) throws Exception {
			this.file = csvFile;
			file.reset();
			currentLine = file.readLine();
			columns = lineSpliter(currentLine);
			super.loadData(this);
		}

		private String[] lineSpliter(String line) {
			if (line == null || line.length() == 0)
				return new String[0];
			Pattern pattern = Pattern.compile(REPLACE_PATTERN);
			Matcher matcher = pattern.matcher(line);
			int diff = 0;
			while (matcher.find()) {
				String tmp = matcher.group();
				String replaceStr = tmp.replaceAll(SPLITER, SPLITER_REPLACEMENT);
				line = line.substring(0, matcher.start() + diff) + replaceStr
						+ line.substring(matcher.end() + diff, line.length());
				diff += replaceStr.length() - tmp.length();
			}
			String[] split = line.split(SPLITER);
			for (int i = 0; i < split.length; i++) {
				String value = split[i];
				if (value.indexOf(SPLITER_REPLACEMENT) >= 0) {
					value = value.replaceAll(SPLITER_REPLACEMENT, SPLITER);
					value = value.substring(1, value.length() - 1);
				}
				split[i] = value;
			}
			return split;
		}

		@Override
		public boolean nextColumn() throws Exception {
			return ++col < columns.length;
		}

		@Override
		public String getColumnName() throws Exception {
			return columns[col];
		}

		@Override
		public boolean nextRow() throws Exception {
			boolean next = (currentLine = file.readLine()) != null;
			currentRow = lineSpliter(currentLine);
			row = -1;
			return next;
		}

		@Override
		public Class<?> getColumnType() throws Exception {
			return null;
		}

		@Override
		public boolean nextRowValue() throws Exception {
			return ++row < currentRow.length;
		}

		@Override
		public Object getRowValue() throws Exception {
			return currentRow[row];
		}

	}
}
