package wyq.test;

import java.util.Arrays;

import wyq.tool.util.Processor;

public class TestProcessor implements Processor {

    @Override
    public void process(String[] args) throws Exception {
	println("Test processor....");
	println("args:" + Arrays.toString(args));
	Thread.sleep(1000);
    }

    private void println(Object o) {
	System.out.println(o);
    }

}
