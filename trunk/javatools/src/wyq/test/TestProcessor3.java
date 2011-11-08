package wyq.test;

import java.util.Arrays;

import wyq.infrastructure.Convertor;
import wyq.infrastructure.PropertySupporter;
import wyq.tool.util.Processor;
import wyq.tool.util.ProcessorRunner;
import wyq.tool.util.PropertyInjector;

public class TestProcessor3 extends PropertySupporter implements Processor {

    @Override
    protected Convertor getConvertor() {
	return new Convertor() {
	    @Override
	    public Object convert(String propValue, Object origValue,
		    Class<?> requiredType) throws Exception {
		return new PropertyInjector().convert(propValue, origValue,
			requiredType);
	    }
	};
    };

    private String a1;
    private String a2;
    private String[] testArr;
    private int[] testArr2;

    @Override
    public void process(String[] args) throws Exception {
	println("Test processor....");
	println("args:" + Arrays.toString(args));
	println("a1:" + a1);
	println("a2:" + a2);
	println("testArr:" + testArr);
	println("testArr2:" + testArr2);
	Thread.sleep(1000);
    }

    private void println(Object o) {
	System.out.println(o);
    }

    public static void main(String[] args) {
	ProcessorRunner.run(TestProcessor3.class, args);
    }

}
