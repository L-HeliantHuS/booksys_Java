package com.booksys;

import com.booksys.windows.Login;

public class Main {
    public static void main(String[] args) {
        // 连接数据库
        if (!MySQLManager.connect()) {
            System.exit(0);
        }

        new Login();
    }
}
