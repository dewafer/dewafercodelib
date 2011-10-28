package wyq;

import java.util.Scanner;

import wyq.cmd.CmdProcessor;
import wyq.cmd.CmdProcessorFactory;

public class ConsoleEntry {

    private class ProcessResult {
	private boolean result;

	/**
	 * @return the result
	 */
	public synchronized boolean getResult() {
	    return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public synchronized void setResult(boolean result) {
	    this.result = result;
	}

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	new ConsoleEntry().run(args);
    }

    public void start(final String[] args) {
	final CmdProcessor processor = CmdProcessorFactory.getCmdProcessor();
	println("Using CmdProcessor:" + processor.getClass());
	println(processor.getCmdProcessorHelp());
	boolean result = true;
	final ProcessResult presult = new ProcessResult();
	while (result) {
	    Scanner in = new Scanner(System.in);
	    final String cmd = in.nextLine();
	    presult.result = true;
	    Thread t = new Thread(new Runnable() {

		@Override
		public void run() {
		    boolean r = processor.process(cmd);
		    presult.setResult(r);
		}
	    });
	    t.start();
	    // FIXME exit problem
	    result = presult.getResult();
	}
    }

    public void run(String[] args) {
	CmdProcessor processor = CmdProcessorFactory.getCmdProcessor();
	println("Using CmdProcessor:" + processor.getClass());
	println(processor.getCmdProcessorHelp());
	boolean result = true;
	while (result) {
	    Scanner in = new Scanner(System.in);
	    String cmd = in.nextLine();
	    result = processor.process(cmd);
	}
    }

    protected void println(Object o) {
	System.out.println(o);
    }

}
