package wyq.cmd;

public interface CmdProcessor {

    public abstract boolean process(String s);
    
    public abstract String getCmdProcessorHelp();
}
