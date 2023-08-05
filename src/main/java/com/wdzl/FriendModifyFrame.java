package com.wdzl;

import com.chat.entity.Friends;
import com.wdzl.util.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class FriendModifyFrame extends JFrame implements ActionListener{
    private String currentUser;
    private JLabel friendNameLabel;
    private JTextArea friendNameField;
    private JButton modifyButton;

    public FriendModifyFrame(String currentUser) {
        super("用户名修改");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.currentUser = currentUser;
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        Color blue = new Color(237,245,255);
        panel.setBackground(blue);
        setSize(400, 200);
        friendNameLabel = new JLabel("修改用户名:");
        friendNameField = new JTextArea(5, 20);
        friendNameField.setLineWrap(true);
        friendNameField.setWrapStyleWord(true);
        modifyButton = new JButton("修改");
        modifyButton.addActionListener(this);

        JScrollPane scrollPane = new JScrollPane(friendNameField);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10,10,10,0);
        constraints.anchor = GridBagConstraints.WEST;
        JLabel currentUserLabel = new JLabel("当前用户：" + currentUser);
        currentUserLabel.setFont(new Font("宋体", Font.BOLD, 16)); // 设置字体为16号粗体
        panel.add(currentUserLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(10,10,10,0);
        constraints.anchor = GridBagConstraints.WEST;
        panel.add(friendNameLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10,-60,10,10);
        panel.add(scrollPane, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(modifyButton, constraints);

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == modifyButton) {
            String name = friendNameField.getText();
            // 在这里实现修改好友用户名的逻辑
            Connection cn = null;
            try {
                cn = new Connection();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            List<Friends> ls = cn.updateFriend(name,currentUser);
            ls.forEach(fr -> {
                System.out.println("修改后的friends:"+fr);
            });
            currentUser = name;
            JOptionPane.showMessageDialog(this, "修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        FriendModifyFrame friendModifyFrame = new FriendModifyFrame("天线宝宝");
        friendModifyFrame.setVisible(true);
    }
}