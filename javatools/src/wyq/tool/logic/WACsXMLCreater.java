package wyq.tool.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import wyq.infrastructure.FileTool;
import wyq.tool.util.Processor;
import wyq.tool.util.ProcessorRunner;

public class WACsXMLCreater implements Processor {

    private String file = "C:\\output\\file.xls";
    private String targetFile = "C:\\output\\out.txt";
    private String[] sheetNames = { "受付明細照会結果 取引履歴詳細_画面仕様書", "KOSHOSHO46（画面仕様）" };
    private int startRow = 12;
    private int[] nameCols = { 3, 10, 11 };
    private String template = "<PageItem description=\"\" fieldName=\"[1][2]\" name=\"[0]\" tagType=\"0\"><Text value=\"[1][2]\"><DataAttribute dataType=\"0\"><CharacterAttribute outHtmlFlag=\"true\"/></DataAttribute></Text></PageItem>";

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
	ProcessorRunner.run(WACsXMLCreater.class, args);
    }

    public void process(String[] args) throws IOException {
	File f = new File(file);
	InputStream s = new FileInputStream(f);
	HSSFWorkbook book = new HSSFWorkbook(s);
	StringBuilder sb = new StringBuilder();

	for (String sheetName : sheetNames) {
	    HSSFSheet sheet = book.getSheet(sheetName);
	    for (int i = startRow; i < sheet.getPhysicalNumberOfRows(); i++) {
		String[] names = new String[nameCols.length];
		HSSFRow row = sheet.getRow(i);
		if (row == null)
		    continue;
		for (int j = 0; j < nameCols.length; j++) {
		    HSSFCell cell = row.getCell(nameCols[j]);
		    if (cell == null)
			continue;
		    try {
			names[j] = cell.getStringCellValue();
		    } catch (Exception e) {
			e.printStackTrace();
			continue;
		    }
		}
		String tmp = template;
		for (int k = 0; k < names.length; k++) {
		    if (names[k] == null)
			continue;
		    tmp = tmp.replace("[" + k + "]", names[k]);
		}
		sb.append(tmp + "\r\n");
	    }
	}

	FileTool file = new FileTool();
	file.writeTxtFile(sb.toString(), targetFile);
    }
}
