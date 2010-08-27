package util.sqlTools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConstraintsGeter {
    private static Connection   connection;

    private static final String DRIVE       = "oracle.jdbc.driver.OracleDriver";

    private static final String CONN_STRING = "jdbc:oracle:thin:@172.31.3.86:1521:xe";

    private static final String USRNAME     = "RTGS_CSTMZ_FIN3_WANGYINQIU";

    private static final String PWD         = "RTGS";

    /**
     * @param args
     * @throws IOException 
     * @throws ClassNotFoundException 
     * @throws SQLException 
     */
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        println("start to connect...");
        connect();
        println("connected.");

        println("read sql file.");
        String sql = getSQL();
        println("sql file read:" + sql);

        println("start to execute sql...");
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(sql);
        println("sql executed.");

        println("start to write Tables.csv ...");
        String TablesFile = ConstraintsGeter.class.getResource("Tables.csv").getFile();
        println("file:" + TablesFile);
        BufferedWriter bw = new BufferedWriter(new FileWriter(TablesFile));
        while (results.next()) {
            bw.write(results.getString("table_name"));
            bw.write(",");
            bw.write(results.getString("column_name"));
            bw.newLine();
        }
        println("Tables.csv created! continue to close connections");
        bw.flush();
        bw.close();
        results.close();
        stmt.close();
        connection.close();
        println("done.");

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

    private static String getSQL() throws IOException {
        String tmpsql = readAllLines(ConstraintsGeter.class.getResource("ConstraintsGeter_SQL.sql").getFile());
        return tmpsql;
    }

    public static String readAllLines(String path) throws IOException {
        String line;
        StringBuilder sb = new StringBuilder();
        BufferedReader bfr = new BufferedReader(new FileReader(path));
        while ((line = bfr.readLine()) != null) {
            if (!line.startsWith("#")) {
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
            }
        }
        bfr.close();
        return sb.toString();
    }

}
