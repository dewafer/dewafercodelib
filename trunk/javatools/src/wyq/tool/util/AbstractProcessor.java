package wyq.tool.util;

import java.io.File;
import java.net.URL;


public abstract class AbstractProcessor implements Processor {

    protected void log(String o) {
	Logger.log(o);
    }

    protected String getResFilePath(String filePath) {
    
        File cf = new File(filePath);
        if (cf.exists()) {
            return cf.getAbsolutePath();
        } else {
            URL url = this.getClass().getResource(filePath);
            if (null != url) {
        	return url.getFile();
            } else {
        	url = this.getClass().getResource("/" + filePath);
        	return url.getFile();
            }
        }
    }
}
