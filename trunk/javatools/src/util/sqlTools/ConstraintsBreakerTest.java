package util.sqlTools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConstraintsBreakerTest {
    private static Connection    connection;

    private static final boolean SKIP_FIRST_LINE = false;

    private static final String  DRIVE           = "oracle.jdbc.driver.OracleDriver";

    private static final String  CONN_STRING     = "jdbc:oracle:thin:@172.31.3.86:1521:xe";

    private static final String  USRNAME         = "RTGS_CSTMZ_FIN3_WANGYINQIU";

    private static final String  PWD             = "RTGS";

    /**
     * @param args
     * @throws IOException 
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        println("start to connect...");
        connect();
        println("connected.");

        println("read conf file.");
        String[] parms = readLines(ConstraintsBreaker.class.getResource("Tables.csv").getFile());
        println("conf file read, count:" + parms.length);

        println("start to process...");
        int MAX = parms.length;
        //        String updateSql = "UPDATE {0} SET {1} = NULL";
        //        String truncateSql = "TRUNCATE TABLE {0}";
        Statement pstmt = connection.createStatement();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            if (SKIP_FIRST_LINE && i == 0)
                continue;
            String[] p = parms[i].split(",");
            //            String s = updateSql.replace("{0}", p[0]);
            //            s = s.replace("{1}", p[1]);
            //            pstmt.addBatch(s);
            //            println("execute:" + s);
            //            int r = -1;
            //            try {
            //                r = pstmt.executeUpdate(s);
            //            } catch (Exception e) {
            //                e.printStackTrace();
            //                println("execute:" + s);
            //                s = truncateSql.replace("{0}", p[0]);
            //                try {
            //                    r = pstmt.executeUpdate(s);
            //                } catch (Exception e2) {
            //                    e.printStackTrace();
            //                }
            //            }
            println("backup...");
            int r =
                ConstraintsBreaker.backup_tableData(
                                                    pstmt,
                                                    p[0],
                                                    ConstraintsBreaker.class.getResource("").getPath());
            println("result:" + r);
        }

        //        println("batch added...start to execute.");
        //        int[] result = pstmt.executeBatch();
        //        print("executed. result:");
        //        for (int r : result) {
        //            print(r);
        //        }
        println("");

        pstmt.close();
        connection.commit();
        connection.close();

        println("done..");
        println("use time:" + (System.currentTimeMillis() - startTime) / 1000 + "s");
    }

    public static void connect() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVE);
        connection = DriverManager.getConnection(CONN_STRING, USRNAME, PWD);
    }

    public static void println(Object o) {
        System.out.println(o);
    }

    public static void print(Object o) {
        System.out.print(o);
    }

    public static String[] readLines(String path) throws IOException {
        String line;
        List<String> lines = new ArrayList<String>();
        BufferedReader bfr = new BufferedReader(new FileReader(path));
        while ((line = bfr.readLine()) != null) {
            if (!line.startsWith("#")) {
                lines.add(line);
            }
        }
        bfr.close();
        return lines.toArray(new String[0]);
    }
}
