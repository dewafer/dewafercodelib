package wyq.tool.util;

import java.util.Arrays;

import wyq.tool.util.Processor.InjectProperty;

public abstract class ProcessorRunner implements Runnable {

    public static void runStart(final Class<? extends Processor> clz,
	    final String[] args) {
	Thread t = new Thread(new ProcessorRunner() {

	    @Override
	    public void run() {
		runProcessor(clz, args);
	    }
	});
	t.start();
    }

    public static void run(final Class<? extends Processor> clz, String[] args) {
	new ProcessorRunner() {

	    @Override
	    public void run() {
		// Do nothing.
	    }
	}.runProcessor(clz, args);
    }

    /**
     * @param args
     */
    protected void runProcessor(Class<? extends Processor> clz, String[] args) {
	long start = System.currentTimeMillis();
	log("Init class:[" + clz + "] with args:[" + Arrays.toString(args)
		+ "]");
	Processor target = null;
	try {
	    target = clz.newInstance();
	} catch (IllegalArgumentException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (SecurityException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (InstantiationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    if (target == null) {
		log("Init failed, exit.");
		return;
	    }
	    InjectProperty ano = clz.getAnnotation(InjectProperty.class);
	    if (ano != null) {
		String propFile = ano.value();
		if (propFile.length() > 0) {
		    log("inject process with properties file:[" + propFile
			    + "]");
		    PropertyInjector.doPropInject(target, propFile);
		} else {
		    log("inject process with default properties file");
		    PropertyInjector.doPropInject(target);
		}
	    }
	    try {
		log("start process with args:[" + Arrays.toString(args) + "]");
		target.process(args);
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } finally {
		log("process finished");
		long end = System.currentTimeMillis();
		log((end - start) + "ms used.");
	    }
	}
    }

    private void log(String string) {
	Logger.log(string);
    }

}
