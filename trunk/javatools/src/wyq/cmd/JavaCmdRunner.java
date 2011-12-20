package wyq.cmd;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import wyq.infrastructure.PropertySupporter;
import wyq.tool.util.Processor;
import wyq.tool.util.ProcessorRunner;

public class JavaCmdRunner extends PropertySupporter implements CmdProcessor {

    public class Cmd {
	private String cmdName;
	private String args[];
    }

    private String default_package;

    public boolean process(String cmd) {
	if ("exit".equalsIgnoreCase(cmd))
	    return false;
	if (cmd == null || cmd.length() == 0 || "help".equalsIgnoreCase(cmd)) {
	    println(getCmdProcessorHelp());
	    return true;
	}
	Cmd c = getCmd(cmd);
	try {
	    String clzName = c.cmdName;
	    if (default_package != null && default_package.length() > 0
		    && !clzName.contains(".")) {
		clzName = default_package + "." + clzName;
	    }
	    Class<?> clz = Class.forName(clzName);
	    if (Processor.class.isAssignableFrom(clz)) {
		Class<? extends Processor> pclz = clz
			.asSubclass(Processor.class);
		ProcessorRunner.run(pclz, c.args);
		println("Cmd Finished.");
		println(getCmdProcessorHelp());
	    } else if (isClassWithMain(clz)) {
		// run main(String[] args)
		Method mainMethod = clz.getMethod("main", String[].class);
		mainMethod.invoke(null, new Object[] { c.args });
	    } else {
		println((new StringBuilder("Can't run the cmd:")).append(cmd)
			.toString());
	    }
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (SecurityException e) {
	    e.printStackTrace();
	} catch (NoSuchMethodException e) {
	    e.printStackTrace();
	} catch (IllegalArgumentException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    e.printStackTrace();
	}
	return true;
    }

    private boolean isClassWithMain(Class<?> clz) {
	Method[] ms = clz.getMethods();
	for (Method m : ms) {
	    if ("main".equals(m.getName())
		    && Modifier.isStatic(m.getModifiers())
		    && equalsParamTypes(m.getParameterTypes(),
			    new Class<?>[] { String[].class })) {
		return true;
	    }
	}
	return false;
    }

    private boolean equalsParamTypes(Class<?>[] parameterTypes,
	    Class<?>[] classes) {
	return Arrays.equals(parameterTypes, classes);
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

    @Override
    public String getCmdProcessorHelp() {
	return "Enter class name to run the class.";
    }
}
