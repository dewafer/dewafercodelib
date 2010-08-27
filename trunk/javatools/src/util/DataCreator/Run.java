package util.DataCreator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Run {

    private static Connection connection;

    /**
     * @param args
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        println("start to connect...");
        connect();
        println("connected.");
//        String sql = "INSERT INTO PERFORMANCE values(";
        String sql = "INSERT INTO ITEM VALUES(";
        long MAX = 99999;
        println("start to insert.");
        long startTime = System.currentTimeMillis();
        for (long i = 0; i < MAX; i++) {
//            sendUpdate(sql + "'" + (i + 100000) + "'" + ", 'test2" + i + "', 'test3" + i + "', 110)");
            sendUpdate(sql + "'" + i +  "'" + ", 'name" + i + "', 'code" + i + "')");
            if (i % 100 == 0) {
                println("....100 records inserted. now i:" + i);
            }
        }
        println("done..");
        println("use time:" + (System.currentTimeMillis() - startTime) / 1000 + "s");
        statement.close();
        connection.commit();
        connection.close();

    }

    public static void connect() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "test", "test");
        statement = connection.createStatement();
    }

    private static Statement statement;

    // 处理查询
    public static ResultSet sendQuery(String sql) {
        try {
            ResultSet m_rs = statement.executeQuery(sql);
            return m_rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 处理数据更新
    public static int sendUpdate(String sql) {
        try {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void println(Object o) {
        System.out.println(o);
    }

}
