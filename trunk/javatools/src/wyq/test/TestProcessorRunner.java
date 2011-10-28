package wyq.test;

import wyq.tool.util.Processor;
import wyq.tool.util.ProcessorRunner;

public class TestProcessorRunner implements Processor {

    /**
     * @param args
     */
    public static void main(String[] args) {
	ProcessorRunner.run(TestProcessorRunner.class, args);
    }

    @Override
    public void process(String[] args) {
	System.out.println(args);
	for(int i=0;i<10;i++){
	    try {
		Thread.sleep(201);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

}
