package wyq.tool.util;

import java.util.Arrays;


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
	    log("start process with args:[" + Arrays.toString(args) + "]");
	    try {
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
