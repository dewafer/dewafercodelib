package util.SQLArgReadAndReplace;

import java.io.IOException;
import java.util.List;

public class SQLFileReCreate {

    private static List<String[]> argMap;

    private static String         FILE   = "D:\\LO27028_DB4test\\LO27028.sql";

    private static String         TARGET = "D:\\LO27028_DB4test\\LO27028_runable.sql";

    private static String         ARGMAP = "D:\\LO27028_DB4test\\ÉRÉsÅ[ Å` LO27028_ARGMAP.CSV";

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        String _buff = "";
        _buff = CoM.read(FILE);
        argMap = CoM.initArgMap(ARGMAP);
        _buff = CoM.process(_buff, argMap);
        _buff = CoM.removeBeginEnd(_buff);
        CoM.write(TARGET, _buff);
        System.out.println("Done.");
        System.out.println(argMap.size() + " args replaced.");
    }

}
