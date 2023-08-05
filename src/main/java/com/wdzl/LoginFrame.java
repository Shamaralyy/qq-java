package com.wdzl;

import com.chat.entity.Friends;
import com.wdzl.util.Connection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class LoginFrame extends JFrame {
    private String account;
    private String password;
    private JLabel labelAccount, labelPassword;
    private JTextField inputAccount;
    private JPasswordField inputPassword;
    private JButton buttonLogin, buttonRegister;

    private ImageIcon qqIcon;

    Connection cn = new Connection();

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginFrame() throws IOException {

        setTitle("QQ登录");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color blue = new Color(237,245,255);
        getContentPane().setBackground(blue);

        labelAccount = new JLabel("账号：");
        labelPassword = new JLabel("密码：");
        inputAccount = new JTextField(20);
        inputPassword = new JPasswordField(20);
        buttonLogin = new JButton("登录");
        buttonRegister = new JButton("注册");

        Container contentPane = getContentPane();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        /*QQ头像放置位置*/
        Image originalImage = new ImageIcon("src/img/qq.png").getImage();
        int width = 60; // 新的图像宽度
        int height = 60; // 新的图像高度
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        qqIcon = new ImageIcon(scaledImage);
        JLabel iconLabel = new JLabel(qqIcon);
        contentPane.add(iconLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, iconLabel, -25, SpringLayout.HORIZONTAL_CENTER, contentPane);
        layout.putConstraint(SpringLayout.NORTH, iconLabel, 20, SpringLayout.NORTH, contentPane);

        /*QQ文字*/
        JLabel qqTextLabel = new JLabel();
        qqTextLabel.setLayout(new BorderLayout());
        qqTextLabel.setPreferredSize(new Dimension(60, 60));
        Font font = new Font("Arial", Font.PLAIN, 30);
        qqTextLabel.setFont(font);
        qqTextLabel.setText("QQ");

        contentPane.add(qqTextLabel);
        layout.putConstraint(SpringLayout.WEST, qqTextLabel, 10, SpringLayout.EAST, iconLabel);
        layout.putConstraint(SpringLayout.NORTH, qqTextLabel, 20, SpringLayout.NORTH, contentPane);

        contentPane.add(labelAccount);
        contentPane.add(inputAccount);
        contentPane.add(labelPassword);
        contentPane.add(inputPassword);
        contentPane.add(buttonLogin);
        contentPane.add(buttonRegister);

        layout.putConstraint(SpringLayout.WEST, labelAccount, 60, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, labelAccount, 100, SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.WEST, inputAccount, 10, SpringLayout.EAST, labelAccount);
        layout.putConstraint(SpringLayout.NORTH, inputAccount, 100, SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.WEST, labelPassword, 60, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, labelPassword, 40, SpringLayout.NORTH, inputAccount);

        layout.putConstraint(SpringLayout.WEST, inputPassword, 10, SpringLayout.EAST, labelPassword);
        layout.putConstraint(SpringLayout.NORTH, inputPassword, 40, SpringLayout.NORTH, inputAccount);

        layout.putConstraint(SpringLayout.EAST, buttonLogin, -10, SpringLayout.HORIZONTAL_CENTER, contentPane);
        layout.putConstraint(SpringLayout.NORTH, buttonLogin, 30, SpringLayout.SOUTH, inputPassword);

        layout.putConstraint(SpringLayout.WEST, buttonRegister, 10, SpringLayout.HORIZONTAL_CENTER, contentPane);
        layout.putConstraint(SpringLayout.NORTH, buttonRegister, 30, SpringLayout.SOUTH, inputPassword);


        buttonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                account = inputAccount.getText();
                password = new String(inputPassword.getPassword());

                try {
                    /*flag为true时，登录成功*/
                    boolean flag = cn.verifyLogin(account,password);
                    if(flag) {
                        JOptionPane.showMessageDialog(LoginFrame.this, "登录成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
                        List<Friends> list = cn.queryFriends();
                        list.forEach(fr -> {
                            System.out.println(fr);
                        });
                        FriendsFrame frame = new FriendsFrame(list);
                        frame.setVisible(true);
                    }else {
                        JOptionPane.showMessageDialog(LoginFrame.this, "登录失败，请检查账号和密码！", "提示", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                account = inputAccount.getText();
                password = new String(inputPassword.getPassword());
                System.out.println("账号："+account);
                System.out.println("密码："+password);
                cn.insertUser(account,password);
                JOptionPane.showMessageDialog(LoginFrame.this, "注册成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
