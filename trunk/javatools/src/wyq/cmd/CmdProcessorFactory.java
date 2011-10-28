package wyq.cmd;

public class CmdProcessorFactory {

    public CmdProcessorFactory() {
    }

    public static CmdProcessor getCmdProcessor() {
	return new JavaCmdRunner();
    }
}
