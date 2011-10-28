package wyq;

import java.util.Scanner;

import wyq.cmd.CmdProcessor;
import wyq.cmd.CmdProcessorFactory;

public class ConsoleEntry {

    /**
     * @param args
     */
    public static void main(String[] args) {
	CmdProcessor processor = CmdProcessorFactory.getCmdProcessor();
	boolean result = true;
	while (result) {
	    Scanner in = new Scanner(System.in);
	    String cmd = in.nextLine();
	    result = processor.process(cmd);
	}
    }

}
