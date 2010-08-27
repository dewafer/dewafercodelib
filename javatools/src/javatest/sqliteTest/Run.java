package javatest.sqliteTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Run {

    private static Connection connection;

    /**
     * @param args
     * @throws ClassNotFoundException 
     * @throws SQLException 
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //        if(args.length < 1){
        //            return ;
        //        }
        connect();
        println("connected.");
        Statement stmt = connection.createStatement();
//        stmt.execute("INSERT INTO dataInfo(dataid,info) VALUES(2,'abc')");
        ResultSet result = stmt.executeQuery("Select * from dataInfo");
        while (result.next()) {
            println(result.getString("INFO"));
        }
        stmt.close();
        connection.close();
    }

    public static void connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:./src/javatest/sqliteTest/db1.db3");
    }

    public static void println(Object o) {
        System.out.println(o);
    }
}
