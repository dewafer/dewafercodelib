package wyq.tool.logic;

import wyq.tool.util.AbstractProcessor;
import wyq.tool.util.Processor.InjectProperty;

@InjectProperty
public class TestProcessor extends AbstractProcessor {

    @Override
    public void process(String[] args) throws Exception {
	// TODO Auto-generated method stub
	log(this.toString());
    }

    private String test1;
    private String test2;
    private String file;

    @Override
    public String toString() {
	return "TestProcessor:[test1:" + test1 + " , test2:" + test2 + " , "
		+ "file:" + getResFilePath(file) + ", " + super.toString()
		+ "]";
    }
}
