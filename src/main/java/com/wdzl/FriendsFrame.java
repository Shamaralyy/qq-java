package com.wdzl;

import com.chat.entity.Friends;
import com.wdzl.util.Connection;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class FriendsFrame extends JFrame {
    private JPanel panelList;
    private JScrollPane scrollPane;

    Connection cn = new Connection();

    public FriendsFrame(List<Friends> list) throws IOException {
        setTitle("好友列表");
        setSize(300, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel lblUsername = new JLabel("admin");
        lblUsername.setFont(new Font("微软雅黑", Font.BOLD, 18));
        lblUsername.setHorizontalAlignment(JLabel.CENTER);
        lblUsername.setBounds(10, 10, 300, 30); //设置标签在面板中的位置和大小
        getContentPane().add(lblUsername);

        JButton btnAddFriend = new JButton("添加好友");
        btnAddFriend.setForeground(Color.WHITE);
        btnAddFriend.setBackground(new Color(76, 175, 80));
        btnAddFriend.setBounds(130, 50, 100, 30); //设置按钮在面板中的位置和大小
        add(btnAddFriend); //将按钮添加到主面板

        btnAddFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 处理添加好友事件
                AddFriendsFrame addFriendPage = null;
                try {
                    addFriendPage = new AddFriendsFrame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                addFriendPage.setVisible(true);
            }
        });

        // 好友列表面板
        panelList = new JPanel();
        panelList.setLayout(new BoxLayout(panelList, BoxLayout.Y_AXIS));
        panelList.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        Color blue = new Color(30, 169, 225);
        panelList.setBackground(blue);
        /*panelList.add(Box.createVerticalStrut(20));*/

        // 添加好友列表项
        list.forEach(fr -> {
            addFriendListItem(fr.getName(), fr.getStatus());
        });

        // 滚动面板
        scrollPane = new JScrollPane(panelList);

        /*头像*/
        //先获取原始图片
        ImageIcon originalIcon = new ImageIcon("src/img/head3.jpg");
        Image originalImage = originalIcon.getImage();

        //缩放后的宽度和高度
        int resizedWidth = 80;
        int resizedHeight = 80;

        //使用Image类对图片进行缩放
        Image resizedImage = originalImage.getScaledInstance(resizedWidth, resizedHeight, Image.SCALE_SMOOTH);

        //将缩放后的图片转换为ImageIcon
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        //将ImageIcon添加到JLabel中并设置位置
        JLabel headLbl = new JLabel(resizedIcon);
        headLbl.setBounds(30, 10, resizedWidth, resizedHeight);
        //设置边框
        Border border = BorderFactory.createLineBorder(new Color(176, 224, 230), 3);
        headLbl.setBorder(border);
        add(headLbl);

        // 布局
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    // 添加好友列表项
    private void addFriendListItem(String name, String status) {
        JPanel listItem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // 设置好友列表项面板背景色
        Color blue = new Color(246, 253, 254);
        listItem.setBackground(blue);
        listItem.setPreferredSize(new Dimension(200, 200));
        listItem.setMaximumSize(new Dimension(300, 80));
        listItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JLabel labelName = new JLabel(name);
        labelName.setFont(labelName.getFont().deriveFont(18.0f));
        JLabel labelStatus = new JLabel(status);
        Dimension dim = new Dimension(230, labelStatus.getPreferredSize().height);
        labelStatus.setPreferredSize(dim);
        labelStatus.setForeground(Color.GRAY);
        JButton buttonChat = new JButton("聊天");
        JButton buttonDelete = new JButton("删除");
        JButton buttonModify = new JButton("修改");

        buttonChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("点击了聊天按钮：" + name);
                MainFrame mf = null;
                try {
                    mf = new MainFrame(name);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                mf.setVisible(true);
            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("点击了删除按钮：" + name);
                cn.deleteFriend(name);
                List<Friends> list = cn.queryFriends();
                list.forEach(fr -> {
                    System.out.println("删除后的好友："+fr);
                });
                JOptionPane.showMessageDialog(FriendsFrame.this, "删除成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出修改对话框进行修改操作
                // ...
                System.out.println("修改"+name);
                FriendModifyFrame friendModifyFrame = new FriendModifyFrame(name);
                friendModifyFrame.setVisible(true);
            }
        });

        listItem.add(labelName);
        listItem.add(Box.createHorizontalStrut(0));
        listItem.add(labelStatus);
        listItem.add(Box.createHorizontalStrut(0));
        listItem.add(buttonChat);
        listItem.add(buttonDelete);
        listItem.add(buttonModify);
        panelList.add(listItem);
    }

    public static void main(String[] args) throws IOException {
        List<Friends> list = Connection.queryFriends();
        list.forEach(fr -> {
            System.out.println(fr);
        });
        FriendsFrame frame = new FriendsFrame(list);
        frame.setVisible(true);
    }
}