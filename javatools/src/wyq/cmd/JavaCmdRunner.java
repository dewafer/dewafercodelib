package wyq.cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import wyq.tool.util.Processor;
import wyq.tool.util.ProcessorRunner;

public class JavaCmdRunner implements CmdProcessor {
    public class Cmd {

	private String cmdName;
	private String args[];
    }

    public JavaCmdRunner() {
    }

    public boolean process(String cmd) {
	if ("exit".equalsIgnoreCase(cmd))
	    return false;
	Cmd c = getCmd(cmd);
	try {
	    Class<?> clz = Class.forName(c.cmdName);
	    if (Processor.class.isAssignableFrom(clz)) {
		Class<? extends Processor> pclz = clz
			.asSubclass(Processor.class);
		ProcessorRunner.run(pclz, c.args);
	    } else {
		println((new StringBuilder("Can't run the cmd:")).append(cmd)
			.toString());
	    }
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
	return true;
    }

    protected void println(String o) {
	System.out.println(o);
    }

    protected Cmd getCmd(String cmd) {
	Scanner s = new Scanner(cmd);
	List<String> args = new ArrayList<String>();
	Cmd c = new Cmd();
	if (s.hasNext()) {
	    c.cmdName = s.next();
	    while (s.hasNext()) {
		args.add(s.next());
	    }
	    c.args = args.toArray(new String[0]);
	}
	return c;
    }
}
