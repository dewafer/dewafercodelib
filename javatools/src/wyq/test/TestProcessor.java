package wyq.test;

import java.util.Arrays;

import wyq.infrastructure.PropertySupporter;
import wyq.tool.util.Processor;
import wyq.tool.util.ProcessorRunner;

public class TestProcessor extends PropertySupporter implements Processor {

    private String a1;
    private String a2;

    @Override
    public void process(String[] args) throws Exception {
	println("Test processor....");
	println("args:" + Arrays.toString(args));
	println("a1:" + a1);
	println("a2:" + a2);
	Thread.sleep(1000);
    }

    private void println(Object o) {
	System.out.println(o);
    }

    public static void main(String[] args) {
	ProcessorRunner.run(TestProcessor.class, args);
    }

}
