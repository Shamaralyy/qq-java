package com.wdzl;

import com.chat.entity.MessageRecord;
import com.wdzl.util.Connection;
import com.wdzl.util.VerticalFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 1. 目标 界面设计
 * a.窗体创建
 * b.
 */
public class MainFrame extends JFrame implements ActionListener {
    private ImageIcon qqIcon;
    private ImageIcon headIcon;
    private DatagramSocket socket;
    private String hostIp;
    private int hostPort;
    private JTextField ipTfd;
    private JTextField sendTfd;
    private JTextField portTfd;
    private JPanel msgPanel;
    /*传递过来的参数name（显示在最上方的用户名）*/
    private String name;
    private Connection cn;

    //构造方法
    public MainFrame(String name) throws IOException {
        initSocket();
        loadImg();
        initHead();
        initIpPort();
        initSendMsg();
        initMsgPanel();
        this.name = name;
        initFrame();  //初始化窗体 显示
        cn = new Connection();
    }

    public MainFrame(ImageIcon qqIcon, ImageIcon headIcon, DatagramSocket socket, String hostIp, int hostPort, JTextField ipTfd, JTextField sendTfd, JTextField portTfd, JPanel msgPanel,String name) {
        this.qqIcon = qqIcon;
        this.headIcon = headIcon;
        this.socket = socket;
        this.hostIp = hostIp;
        this.hostPort = hostPort;
        this.ipTfd = ipTfd;
        this.sendTfd = sendTfd;
        this.portTfd = portTfd;
        this.msgPanel = msgPanel;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 加载图像资源
     */
    private void loadImg() {
        qqIcon = new ImageIcon("src/img/qq.png");
        headIcon = new ImageIcon("src/img/head4.jpg");
    }

    /**
     * 初始化窗体
     */
    private void initFrame() {
        //设置窗体大小
        setSize(550, 450);
        //默认居中显示
        setLocationRelativeTo(null);
        //设置绝对布局
        setLayout(null);
        //设置标题
        setTitle("聊天" + hostIp + ":" + hostPort);
        //创建一个标签用于显示用户名
        JLabel usernameLabel = new JLabel(name+"(2670602083)");
        //设置标签的字体和颜色
        usernameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        usernameLabel.setForeground(Color.black);
        //创建一个面板来容纳标签
        JPanel usernamePanel = new JPanel();
        Color blue = new Color(30, 169, 225);
        getContentPane().setBackground(blue);
        usernamePanel.setBackground(blue);
        usernamePanel.setLayout(new BorderLayout());
        usernamePanel.add(usernameLabel, BorderLayout.CENTER);
        //将面板添加到窗体中，并设置位置和大小
        getContentPane().add(usernamePanel);
        usernamePanel.setBounds(0, 0, getWidth(), 30);
        //设置关闭窗体时，退出程序
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //设置禁止改变窗体大小
        setResizable(false);
        //设置窗体图标
        setIconImage(qqIcon.getImage());
        //设置背景
        getContentPane().setBackground(Color.decode("#C8E0F6"));
    }

    /*初始化socket*/
    private void initSocket() {
        //udp协议数据报套接字
        try {
            socket = new DatagramSocket();
            hostPort = socket.getLocalPort();

            hostIp = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //启动线程 后台等待接收消息
        new RecevierThread().start();
    }

    /**
     * 初始化头像
     */
    private void initHead() {
        // 创建文本标签
        JLabel headLbl = new JLabel(headIcon);
        //设置大小
        headLbl.setSize(headIcon.getIconWidth(), headIcon.getIconHeight());
        //设置位置
        headLbl.setLocation(390, 60);
        //添加到窗体中
        add(headLbl);
    }

    /**
     * 初始化ip和port
     */
    private void initIpPort() {
        //创建文本标签
        JLabel ipLbl = new JLabel("IP:");
        ipLbl.setBounds(380, 200, 140, 25);
        add(ipLbl);

        //创建输入框
        ipTfd = new JTextField("10.20.69.54");
        ipTfd.setBounds(380, 225, 150, 25);
        add(ipTfd);

        //创建文本标签
        JLabel portLbl = new JLabel("PORT:");
        portLbl.setBounds(380, 260, 140, 25);
        add(portLbl);

        //创建输入框
        portTfd = new JTextField("");
        portTfd.setBounds(380, 286, 150, 25);
        add(portTfd);
    }

    /**
     * 发送消息框
     */
    private void initSendMsg() {
        // 发送消息输入框
        sendTfd = new JTextField();
        sendTfd.setBounds(5, 370, 350, 30);
        add(sendTfd);

        //发送消息按钮
        JButton sendBtn = new JButton("发送消息");
        sendBtn.setBounds(385, 370, 140, 30);
        add(sendBtn);

        //给按钮绑定监听
        sendBtn.addActionListener(this);
    }

    /**
     * 初始化消息面板
     */
    private void initMsgPanel() {
        msgPanel = new JPanel();
        msgPanel.setBounds(5, 5, 370, 330);
        Color blue = new Color(230, 246, 245);
        msgPanel.setBackground(blue);
        msgPanel.setLayout(new VerticalFlowLayout()); // 设置自定义的垂直布局

        JScrollPane jScrollPane = new JScrollPane(msgPanel);
        /*设置聊天框位置*/
        jScrollPane.setBounds(5, 35, 350, 330);

        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(jScrollPane);
    }

    /**
     * 监听到被点击时，代码执行这里
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // 当按钮被点击时，发送消息
        new Thread(() -> {
            sendMsg();
        }).start();
    }

    /*发送端*/
    private void sendMsg() {
        // 获取ip
        String ip = ipTfd.getText();
        String portStr = portTfd.getText();
        int port = Integer.parseInt(portStr);
        // 获取要发送的内容
        String msg = sendTfd.getText();
        byte[] data = msg.getBytes();
        // 获取当前时间
        LocalDateTime currentDateTime = LocalDateTime.now();
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 将日期格式化为字符串
        String formattedDateTime = currentDateTime.format(formatter);
        try {
            //地址
            InetAddress addr = InetAddress.getByName(ip);
            //包裹对象
            DatagramPacket packet = new DatagramPacket(data, data.length, addr, port);
            // 把包裹发出去
            socket.send(packet);
            System.out.println(">>>>>====发出消息" + ip + "===" + port);
            System.out.println("发送者"+"admin");
            System.out.println("接收者："+name);
            System.out.println("发送内容："+msg);
            System.out.println("发送时间："+formattedDateTime);
            cn.insertRecord("admin",name,msg,formattedDateTime);
            //发送的消息添加到消息面板
            msgAddToMsgPanel(msg, FlowLayout.RIGHT);
            /*查询出所有消息记录*/
            List<MessageRecord> ls = cn.selectAllRecord("alice","Bob");
            ls.forEach(item -> {
                System.out.println("消息记录："+item);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 把消息显示添加到消息面板中
     */
    private void msgAddToMsgPanel(String msg, int align) {
        System.out.println("========msgAddToMsgPanel========" + msg);
        JLabel msgLbl = new JLabel(msg, SwingConstants.CENTER);
        Color white = new Color(255, 255, 255);
        msgLbl.setForeground(white);
        Color blue = new Color(18, 183, 245);
        msgLbl.setBackground(blue);
        msgLbl.setSize(50, 25);
        msgLbl.setOpaque(true);

        JPanel itemPanel = new JPanel();
        //设置每一条消息所在面板的宽度 ，和 消息面板宽度一致。撑满了
        itemPanel.setPreferredSize(new Dimension(msgPanel.getWidth() - 10, 25));
        itemPanel.add(msgLbl);

        //设置左右对齐
        FlowLayout layout = (FlowLayout) itemPanel.getLayout();
        layout.setAlignment(align);

        msgPanel.add(itemPanel);
        msgPanel.updateUI();
    }

    /**
     * 获取
     *
     * @return qqIcon
     */
    public ImageIcon getQqIcon() {
        return qqIcon;
    }

    /**
     * 设置
     *
     * @param qqIcon
     */
    public void setQqIcon(ImageIcon qqIcon) {
        this.qqIcon = qqIcon;
    }

    /**
     * 获取
     *
     * @return headIcon
     */
    public ImageIcon getHeadIcon() {
        return headIcon;
    }

    /**
     * 设置
     *
     * @param headIcon
     */
    public void setHeadIcon(ImageIcon headIcon) {
        this.headIcon = headIcon;
    }

    /**
     * 获取
     *
     * @return socket
     */
    public DatagramSocket getSocket() {
        return socket;
    }

    /**
     * 设置
     *
     * @param socket
     */
    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    /**
     * 获取
     *
     * @return hostIp
     */
    public String getHostIp() {
        return hostIp;
    }

    /**
     * 设置
     *
     * @param hostIp
     */
    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    /**
     * 获取
     *
     * @return hostPort
     */
    public int getHostPort() {
        return hostPort;
    }

    /**
     * 设置
     *
     * @param hostPort
     */
    public void setHostPort(int hostPort) {
        this.hostPort = hostPort;
    }

    /**
     * 获取
     *
     * @return ipTf
     */
    public JTextField getIpTfd() {
        return ipTfd;
    }

    /**
     * 设置
     *
     * @param ipTfd
     */
    public void setIpTfd(JTextField ipTfd) {
        this.ipTfd = ipTfd;
    }

    /**
     * 获取
     *
     * @return sendTfd
     */
    public JTextField getSendTfd() {
        return sendTfd;
    }

    /**
     * 设置
     *
     * @param sendTfd
     */
    public void setSendTfd(JTextField sendTfd) {
        this.sendTfd = sendTfd;
    }

    /**
     * 获取
     *
     * @return portTfd
     */
    public JTextField getPortTfd() {
        return portTfd;
    }

    /**
     * 设置
     *
     * @param portTfd
     */
    public void setPortTfd(JTextField portTfd) {
        this.portTfd = portTfd;
    }

    /**
     * 获取
     *
     * @return msgPanel
     */
    public JPanel getMsgPanel() {
        return msgPanel;
    }

    /**
     * 设置
     *
     * @param msgPanel
     */
    public void setMsgPanel(JPanel msgPanel) {
        this.msgPanel = msgPanel;
    }

    public String toString() {
        return "MainFrame{qqIcon = " + qqIcon + ", headIcon = " + headIcon + ", socket = " + socket + ", hostIp = " + hostIp + ", hostPort = " + hostPort + ", ipTfd = " + ipTfd + ", sendTfd = " + sendTfd + ", portTfd = " + portTfd + ", msgPanel = " + msgPanel + "}";
    }

    /*接收端*/
    class RecevierThread extends Thread {
        @Override
        public void run() {
            System.out.println("======启动后台接收消息线程====");
            byte[] data = new byte[1024];
            while (true) {
                //创建包裹对象
                DatagramPacket packet = new DatagramPacket(data, data.length);
                try {
                    //等待接收消息
                    socket.receive(packet);
                    //如果接收到消息后，上面的阻塞会解除
                    int len = packet.getLength();
                    String msg = new String(data, 0, len);
                    System.out.println("接收到的消息：" + msg);
                    //接收的消息添加到消息面板
                    msgAddToMsgPanel(msg, FlowLayout.LEFT);
                    // 获取对方的ip和端口
                    int port = packet.getPort();
                    String ip = packet.getAddress().getHostAddress();
                    //回显到界面
                    ipTfd.setText(ip);
                    portTfd.setText(port + "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

/*    public static void main(String[] args) {
        new MainFrame().setVisible(true);
    }*/
}
