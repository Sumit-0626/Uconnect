//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyJDBC {
    private static final String URL = "jdbc:mysql://localhost:3306/login_schema";
    private static final String USER = "root";
    private static final String PASSWORD = "sumit@2602i";

    public MyJDBC() {
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found!");
            e.printStackTrace();
        }

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/login_schema", "root", "sumit@2602i");
    }
}
