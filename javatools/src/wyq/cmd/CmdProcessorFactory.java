package wyq.cmd;

import wyq.infrastructure.PropertySupporter;

public class CmdProcessorFactory extends PropertySupporter {

    private String default_cmdProcessor_name;
    private CmdProcessor processor;
    private static CmdProcessorFactory factory = new CmdProcessorFactory();

    public static CmdProcessor getCmdProcessor() {
	if (factory.processor == null) {
	    factory.processor = factory.createCmdProcessor();
	}
	return factory.processor;
    }

    private CmdProcessor createCmdProcessor() {
	Class<?> clzz = null;
	CmdProcessor processor = null;
	try {
	    clzz = Class.forName(factory.default_cmdProcessor_name);
	    processor = (CmdProcessor) clzz.newInstance();
	} catch (IllegalAccessException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (InstantiationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    if (processor == null)
		throw new RuntimeException("Can't get CmdProcessor!");
	}
	return processor;
    }
}
