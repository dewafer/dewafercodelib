package wyq.infrastructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelOperator {

	public static String getCellStrValue(File xls, String sheetName, int rowNo,
			int colNo) throws IOException {
		InputStream s = new FileInputStream(xls);
		HSSFWorkbook book = new HSSFWorkbook(s);
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
	
}
