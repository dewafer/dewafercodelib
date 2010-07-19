package util.SQLArgReadAndReplace;

import java.io.IOException;
import java.util.List;

public class SQLFileReadArgs {

    ////    private static String FILE   = "D:\\LO27028_DB4test\\LO27028.sql";
    //    private static String FILE   = "D:\\LO7307\\LO7307.sql";
    //
    ////    private static String TARGET = "D:\\LO27028_DB4test\\LO27028_ARGMAP.CSV";
    //    private static String TARGET = "D:\\LO7307\\LO7307_param.csv";

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        String conf_resource = SQLFileReadArgs.class.getResource("SQLFileReadArgsConfig").getFile();
        String[] params = CoM.readLines(conf_resource);
        String FILE = params[0];
        String TARGET = params[1];
        String _buff = "";
        _buff = CoM.read(FILE);
        List<String> argList = CoM.guessArg(_buff);
        CoM.write(TARGET, CoM.toStr(argList));
        System.out.println("Done");
        System.out.println(argList.size() + " args found.");
    }

}
