package wyq.tool.util;

import wyq.infrastructure.PropertySupporter;

public abstract class AbstractPropertyProcessor extends PropertySupporter
	implements Processor {

    protected void log(String o){
	Logger.log(o);
    }
}
