package wyq.tool.logic;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import wyq.infrastructure.FileTool;
import wyq.tool.util.AbstractPropertyProcessor;
import wyq.tool.util.ProcessorRunner;

public class FileExtraer extends AbstractPropertyProcessor {

    private String contentFile;
    private String targetFolder;
    private String workspace;
    private FileTool tool = new FileTool();
    private String[] args;

    private static final String[] IGNORE_LINE_PREFIX = { "#", "/", "-", "=" };

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
	    lines = tool.readTxtFileLines(getContentFile(), IGNORE_LINE_PREFIX);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return lines;
    }

    private String getContentFile() {
	String cfile = contentFile;
	if (args != null && args.length > 0) {
	    cfile = args[0];
	    log("using arg:" + cfile);
	}
	File cf = new File(cfile);
	if (cf.exists()) {
	    return cf.getAbsolutePath();
	} else {
	    URL url = this.getClass().getResource(contentFile);
	    if (null != url) {
		return url.getFile();
	    } else {
		url = this.getClass().getResource("/" + contentFile);
		return url.getFile();
	    }
	}
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
