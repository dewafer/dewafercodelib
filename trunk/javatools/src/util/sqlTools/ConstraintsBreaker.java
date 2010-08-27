package util.sqlTools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConstraintsBreaker {

    private static Connection    connection;

    private static final boolean SKIP_FIRST_LINE           = false;

    private static final boolean BACKUP_TABLE_BEFORE_TOUCH = true;

    private static final boolean TABLE_BACKUP_QUOTED       = true;

    private static final String  DRIVE                     = "oracle.jdbc.driver.OracleDriver";

    private static final String  CONN_STRING               = "jdbc:oracle:thin:@172.31.3.86:1521:xe";

    private static final String  USRNAME                   = "RTGS_CSTMZ_FIN3_WANGYINQIU";

    private static final String  PWD                       = "RTGS";

    /**
     * @param args
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws IOException 
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        println("start to connect...");
        connect();
        println("connected.");

        println("read conf file.");
        String[] parms = readLines(ConstraintsBreaker.class.getResource("Tables.csv").getFile());
        println("conf file read, count:" + parms.length);

        println("start to process...");
        int MAX = parms.length;
        String updateSql = "UPDATE {0} SET {1} = NULL";
        String truncateSql = "TRUNCATE TABLE {0}";
        Statement pstmt = connection.createStatement();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            if (SKIP_FIRST_LINE && i == 0)
                continue;
            String[] p = parms[i].split(",");
            String s = updateSql.replace("{0}", p[0]);
            s = s.replace("{1}", p[1]);
            println("execute:" + s);
            int r = -1;
            if (BACKUP_TABLE_BEFORE_TOUCH) {
                try {
                    backup_tableData(pstmt, p[0], ConstraintsBreaker.class.getResource("").getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                r = pstmt.executeUpdate(s);
            } catch (Exception e) {
                e.printStackTrace();
                println("execute:" + s);
                s = truncateSql.replace("{0}", p[0]);
                try {
                    r = pstmt.executeUpdate(s);
                } catch (Exception e2) {
                    e.printStackTrace();
                }
            }
            println("result:" + r);
        }

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

    public static int backup_tableData(Statement statement, String table, String backupPath)
        throws SQLException, IOException {

        ResultSet result = statement.executeQuery("SELECT * FROM " + table);
        ResultSetMetaData meta = result.getMetaData();
        int max = meta.getColumnCount();
        int r = 0;

        BufferedWriter out = new BufferedWriter(new FileWriter(backupPath + "\\" + table + "_databackup.csv"));

        for (int i = 1; i <= max; i++) {
            out.write(meta.getColumnName(i));
            if (i != max - 1)
                out.write(",");
        }
        out.newLine();
        while (result.next()) {
            for (int i = 1; i <= max; i++) {
                String tmp = result.getString(i);
                tmp = (tmp != null) ? tmp : "";
                if (TABLE_BACKUP_QUOTED) {
                    tmp = "\"" + tmp + "\"";
                }
                out.write(tmp);
                if (i != max - 1)
                    out.write(",");
            }
            out.newLine();
            r++;
        }

        out.flush();
        out.close();
        return r;
    }

}
