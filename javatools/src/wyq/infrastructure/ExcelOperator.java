package wyq.infrastructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class ExcelOperator {

    public static String getCellStrValue(File xls, String sheetName, int rowNo,
	    int colNo) throws IOException {
	HSSFWorkbook book = getWorkbook(xls);
	HSSFSheet sheet = book.getSheet(sheetName);
	if (sheet == null) {
	    return null;
	}
	HSSFRow row = sheet.getRow(rowNo);
	if (row == null) {
	    return null;
	}
	HSSFCell cell = row.getCell(colNo);
	if (cell == null) {
	    return null;
	}
	return cell.getStringCellValue();
    }

    public static String[] getRowStrings(File xls, String sheetName, int colNo)
	    throws IOException {
	HSSFWorkbook book = getWorkbook(xls);
	HSSFSheet sheet = book.getSheet(sheetName);
	List<String> lines = new ArrayList<String>();
	if (sheet == null) {
	    return null;
	}
	Iterator<Row> rowIterator = sheet.rowIterator();
	while (rowIterator.hasNext()) {
	    HSSFRow row = (HSSFRow) rowIterator.next();
	    HSSFCell cell = row.getCell(colNo);
	    if (cell != null) {
		lines.add(cell.getStringCellValue());
	    }
	}
	return lines.toArray(new String[lines.size()]);
    }

    protected static HSSFWorkbook getWorkbook(File xls) throws IOException {
	InputStream s = new FileInputStream(xls);
	HSSFWorkbook book = new HSSFWorkbook(s);
	return book;
    }
}
