package wyq.tool.util;


public abstract class AbstractProcessor implements Processor {

    protected void log(String o) {
	Logger.log(o);
    }
}
