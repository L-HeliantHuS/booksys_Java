package com.booksys.windows;

import com.booksys.Config;
import com.booksys.MySQLManager;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
    JFrame userFrame;

    public User() {
        userFrame = new JFrame("电商购物平台-图书列表页面");

        // 设置窗口大小
        userFrame.setSize(600, 500);

        // 设置窗口出现的位置
        userFrame.setLocation(700, 400);

        // 设置窗口禁止缩放
        userFrame.setResizable(false);

        // 点击关闭按钮结束程序
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();


        // 添加组件
        userFrame.add(jPanel);
        userExtends(jPanel);

        userFrame.setVisible(true);
    }

    public int getRows() {
        try {
            Statement statement = MySQLManager.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(*) from book");
            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException e) {
            return 0;
        }
    }

    public void getData(Object[][] data) {
        // 获取表格数据
        try {
            PreparedStatement preparedStatement = MySQLManager.connection.prepareStatement(
                    "select bid, name, author, number from book");
            ResultSet resultSet = preparedStatement.executeQuery();

            int index = 0;
            while (resultSet.next()) {
                data[index] = new Object[]{resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4)};
                index++;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void userExtends(JPanel panel) {
        panel.setLayout(null);

        // 用户名提示label
        JLabel userNameLabel = new JLabel("你好, " + Config.username);
        userNameLabel.setBounds(80, 30, 200, 50);
        panel.add(userNameLabel);

        // 城市label
        JLabel userCityLabel = new JLabel("来自于：" + Config.city);
        userCityLabel.setBounds(400, 30, 200, 50);
        panel.add(userCityLabel);


        Object[][] data = new Object[getRows()][4];
        getData(data);

        // 表格
        JTable table=new JTable(data, new Object[]{"书籍编号", "书籍名称", "书籍作者", "库存"});
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 600, 300);
        panel.add(jsp);
    }
}
