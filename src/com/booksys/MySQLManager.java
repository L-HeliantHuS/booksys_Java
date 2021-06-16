package com.booksys;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLManager {
    public static Connection connection;

    public static boolean connect() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("没有找到类！");
            return false;
        }

        try {
            connection = DriverManager.getConnection(
                    Config.mysqlUri, Config.mysqlUsername, Config.mysqlPassword
            );
            return true;
        } catch (java.sql.SQLException e) {
            System.out.println("数据库连接失败!");
            return false;
        }

    }

    public static boolean customConnect(String uri, String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("没有找到类！");
            return false;
        }

        try {
            connection = DriverManager.getConnection(
                    uri, username, password
            );
            return true;
        } catch (java.sql.SQLException e) {
            System.out.println("数据库连接失败!");
            return false;
        }
    }


}
