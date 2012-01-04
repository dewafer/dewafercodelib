package wyq.tool.logic;

import java.util.Enumeration;
import java.util.StringTokenizer;

import wyq.infrastructure.PropertyTool;
import wyq.tool.util.AbstractProcessor;
import wyq.tool.util.Processor.InjectProperty;
import wyq.tool.util.ProcessorRunner;

@InjectProperty
public class PropertyCheck extends AbstractProcessor {

    private String propertyFile;

    static final String KEY_SEP = ".";
    private PropertyTool p;

    @Override
    public void process(String[] args) throws Exception {
	String pf = getResFilePath(propertyFile);

	p = PropertyTool.LoadFromFile(pf);
	Enumeration<Object> keys = p.keys();
	log("property file loaded: " + p.keySet().size() + " key(s).");
	while (keys.hasMoreElements()) {
	    String strKey = keys.nextElement().toString();
	    log("chk key:" + strKey);
	    convKey(strKey);
	}

    }

    /**
     * テーブルキーの生成
     * 
     * @param String
     *            key
     */
    protected Object convKey(String key) throws RuntimeException {
	if (key.startsWith(";")) {
	    return key;
	}
	StringTokenizer token = new StringTokenizer(key, KEY_SEP);
	if (2 != token.countTokens()) {
	    log("key error:" + key);
	    log("vale:" + p.getProperty(key));
	    throw new RuntimeException("Property key error:" + key);
	}
	token.nextToken();
	String ret = token.nextToken().trim();

	return ret;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	ProcessorRunner.run(PropertyCheck.class, args);
    }

}
