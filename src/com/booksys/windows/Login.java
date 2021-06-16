package com.booksys.windows;

import com.booksys.Config;
import com.booksys.MySQLManager;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {
    JFrame mainFrame;
    // 为了更方便的获取用户名和密码 写在外面
    JTextField userText;            // 用户名
    JPasswordField userPassword;    // 密码

    public Login() {
        mainFrame = new JFrame("电商购物平台");

        // 设置窗口大小
        mainFrame.setSize(620, 320);

        // 设置窗口出现的位置
        mainFrame.setLocation(600, 400);

        // 设置窗口禁止缩放
        mainFrame.setResizable(false);


        JPanel jPanel = new JPanel();

        // 点击关闭按钮结束程序
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // 添加组件
        mainFrame.add(jPanel);
        myExtends(jPanel);


        mainFrame.setVisible(true);
    }

    // LoginCheck 登录函数
    public boolean LoginCheck(String username, String password) throws Exception {
        PreparedStatement sql = MySQLManager.connection.prepareStatement(
                "select * from user where uid = ? and password = ?"
        );

        sql.setString(1, username);
        sql.setString(2, password);

        ResultSet resultSet = sql.executeQuery();
        // 获取用户的信息到Config
        if (resultSet.next()) {
            Config.username = resultSet.getString("name");
            Config.city = resultSet.getString("city");
            return true;
        }
        return false;
    }

    public void myExtends(JPanel panel) {
        panel.setLayout(null);

        // 用户名label
        JLabel userLabel = new JLabel("用户账号: ");
        userLabel.setBounds(100, 60, 80, 30);
        panel.add(userLabel);

        // 密码label
        JLabel passwdLabel = new JLabel("用户密码: ");
        passwdLabel.setBounds(100, 90, 80, 30);
        panel.add(passwdLabel);

        // 用户名input
        userText = new JTextField();
        userText.setBounds(180, 60, 200, 30);
        panel.add(userText);

        // 密码input
        userPassword = new JPasswordField();
        userPassword.setBounds(180, 90, 200, 30);
        panel.add(userPassword);

        // 登录按钮
        JButton submit = new JButton("登录");
        submit.setBounds(160, 150, 100, 20);
        submit.addActionListener(e -> {
            // 获得username 和 password中输入的内容
            String username = userText.getText();

            // JPasswordField的getText已经弃用 使用getPassword然后new String()
            String password = new String(userPassword.getPassword());
            if (username.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(
                        submit,
                        "用户名或密码不能为空",
                        "错误提示",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                // 判断是否登录成功
                try {
                    if (this.LoginCheck(username, password)) {
                        mainFrame.setVisible(false);
                        new User();
                    } else {
                        JOptionPane.showMessageDialog(
                                submit,
                                "登录失败, 检查用户名密码是否正确",
                                "登录失败",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }

                // 触发异常
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(
                            submit,
                            "登录异常, 请检查MySQL连接",
                            "错误提示",
                            JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                }
            }
        });
        panel.add(submit);

        // 重置按钮
        JButton reset = new JButton("重置");
        reset.setBounds(270, 150, 100, 20);
        // 添加事件
        reset.addActionListener(e -> {
            userText.setText("");
            userPassword.setText("");
            userText.requestFocus();
        });
        panel.add(reset);
    }
}