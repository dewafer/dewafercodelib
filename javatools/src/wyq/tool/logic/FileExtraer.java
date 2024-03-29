package wyq.tool.logic;

import java.io.File;
import java.io.IOException;

import wyq.infrastructure.ExcelOperator;
import wyq.infrastructure.FileTool;
import wyq.tool.util.AbstractProcessor;
import wyq.tool.util.Processor.InjectProperty;
import wyq.tool.util.ProcessorRunner;

@InjectProperty
public class FileExtraer extends AbstractProcessor {

    private String contentFile;
    private String targetFolder;
    private String workspace;
    private boolean usingXls;
    private FileTool tool = new FileTool();
    private String[] args;
    private String sheetName;
    private int colNo;

    private static final String[] IGNORE_LINE_PREFIX = { "#", "//", "/*", "-",
	    "=" };

    /**
     * @param args
     */
    public static void main(String[] args) {
	ProcessorRunner.run(FileExtraer.class, args);
    }

    public void process(String[] args) {
	this.args = args;
	String[] strOrgFiles = openFile();
	for (String f : strOrgFiles) {
	    if (f.isEmpty()) {
		continue;
	    }
	    File tarFile = getTargetFile(f);
	    File orgFile = getOriginalFile(f);
	    if (!orgFile.exists()) {
		log("file not found, skipped:" + orgFile);
		continue;
	    }
	    try {
		if (!tarFile.exists()) {
		    tarFile.getParentFile().mkdirs();
		    tarFile.createNewFile();
		}
		log("copy File:" + f);
		tool.copyFile(orgFile, tarFile);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	tool.openExplorer(targetFolder);
    }

    private String[] openFile() {
	String[] lines = null;
	try {
	    if (usingXls) {
		File xls = new File(getContentFile());
		lines = ExcelOperator.getRowStrings(xls, sheetName, colNo);
	    } else {
		lines = tool.readTxtFileLines(getContentFile(),
			IGNORE_LINE_PREFIX);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return lines;
    }

    private String getContentFile() {
	if (args != null && args.length > 0) {
	    contentFile = args[0];
	    log("using arg:" + contentFile);
	}
	return getResFilePath(contentFile);
    }

    private File getTargetFile(String file) {
	File t = new File(targetFolder, file);
	return t;
    }

    private File getOriginalFile(String filePath) {
	File orgFile = new File(workspace, filePath);
	return orgFile;
    }
}
