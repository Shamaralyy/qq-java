package com.wdzl;

import com.chat.entity.Friends;
import com.chat.entity.User;
import com.wdzl.util.Connection;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class AddFriendsFrame extends JFrame {
    static Connection cn;

    static {
        try {
            cn = new Connection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JTextField userInputField;
    private JButton searchButton;
    private JTable userListTable;

    public AddFriendsFrame() throws IOException {
        // 设置窗口标题和大小
        setTitle("添加好友");
        setSize(500, 400);
        // 创建界面元素
        JLabel userLabel = new JLabel("请输入用户名：");
        userInputField = new JTextField(20);
        searchButton = new JButton("搜索");
        userListTable = new JTable(new Object[][]{}, new Object[]{"用户名", "状态", "添加"});

        // 设置“添加”列的渲染器和编辑器
        userListTable.getColumnModel().getColumn(2).setCellRenderer((TableCellRenderer) new ButtonRenderer());
        userListTable.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JCheckBox()));

        // 创建搜索按钮的监听器
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("搜索："+userInputField.getText());
                final Object[][][] data = {{}};
                List<User> list = cn.selectSection(userInputField.getText());
                list.forEach(user -> {
                    // 定义新行数据
                    Object[] newRow = {user.getUsername(), user.getStatus(), new JButton("添加")};
                    // 将新行数据添加到二维数组
                    Object[][] newData = new Object[data[0].length + 1][3];
                    System.arraycopy(data[0], 0, newData, 0, data[0].length);
                    newData[newData.length - 1] = newRow;
                    // 更新原始的二维数组
                    data[0] = newData;
                });
            }
        });

        renderFriendsList();

        // 添加界面元素到容器中
        JPanel contentPane = new JPanel();
        Color blue = new Color(246, 253, 254);
        contentPane.setBackground(blue);
        contentPane.setLayout(new BorderLayout());
        JPanel userInputPanel = new JPanel(new FlowLayout());
        userInputPanel.add(userLabel);
        userInputPanel.add(userInputField);
        userInputPanel.add(searchButton);
        contentPane.add(userInputPanel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(userListTable);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        setContentPane(contentPane);
    }

    public void renderFriendsList() {
        // TODO: 根据输入框的内容进行用户查询，并更新用户列表
        final Object[][][] data = {{}};
        List<User> users = cn.queryUser();
        users.forEach(user -> {
            // 定义新行数据
            Object[] newRow = {user.getUsername(), user.getStatus(), new JButton("添加")};
            // 将新行数据添加到二维数组
            Object[][] newData = new Object[data[0].length + 1][3];
            System.arraycopy(data[0], 0, newData, 0, data[0].length);
            newData[newData.length - 1] = newRow;
            // 更新原始的二维数组
            data[0] = newData;
        });

        userListTable.setModel(new javax.swing.table.DefaultTableModel(
                data[0],
                new String[]{"用户名", "状态", "添加"}
        ));

        // 重新设置“添加”列的渲染器和编辑器
        userListTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        userListTable.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    // 自定义渲染器，用于将“添加”列的单元格渲染成按钮
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : "添加");
            return this;
        }
    }

    // 自定义编辑器，用于将“添加”列的单元格编辑成按钮（即添加按钮的监听器）
    static class ButtonEditor extends DefaultCellEditor {
        protected JButton button;

        private String label;

        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : "添加";
            button.setText(label);
            isPushed = true;
            // 获取当前行用户的信息
            String username = (String) table.getValueAt(row, 0);
            String status = (String) table.getValueAt(row, 1);
            System.out.println("当前点击添加按钮的用户：" + username+"  " + status);
            cn.insertFriend(username,status);
            /*List<Friends> ls = cn.insertFriend(username,status);*/
/*            ls.forEach(fr -> {
                System.out.println("添加后的fr:"+fr);
            });*/
            // 在添加成功后显示提示框
            JOptionPane.showMessageDialog(table, "添加成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                // TODO: 在此处添加好友
                System.out.println(label + " 被添加为好友！");
            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}