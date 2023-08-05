package com.wdzl.util;

import com.chat.entity.Friends;
import com.chat.entity.MessageRecord;
import com.chat.entity.User;
import com.mapper.ChatMapper;
import com.mapper.FriendsMapper;
import com.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Random;


public class Connection {
    static InputStream is;

    static {
        try {
            is = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    static SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
    static SqlSession sqlSession = sqlSessionFactory.openSession();

    public Connection() throws IOException {
    }


    public static int generateSixDigitsRandom() {
        Random random = new Random();
        System.out.println("随机id：" + random.nextInt(900000) + 100000);
        return random.nextInt(900000) + 100000;
    }


    public static List<User> queryUser() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> list = mapper.queryAll();
        return list;
    }

    public static List<User> selectSection(String str) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> list = mapper.selectSection(str);
        return list;
    }

    public static void insertUser(String username, String password) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println("注册的打印值："+mapper.insertUser(generateSixDigitsRandom(), username, password));;
    }

    public static boolean verifyLogin(String account, String psw) throws IOException {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> result = mapper.selectAll(account);
        /*在Java中，return语句只能用于方法中。在你的代码中，return语句
            不能出现在Lambda表达式中，所以用success存储返回值*/
        AtomicBoolean success = new AtomicBoolean(false);
        if (result.isEmpty()) {
            System.out.println("账号不存在！");
            return false;
        }
        result.forEach(user -> {
            System.out.println("Login页面传递的参数account：" + account);
            if (psw.equals(user.getPassword())) {
                System.out.println("登录成功！");
                success.set(true);
            } else {
                System.out.println("接口传递的参数psw：" + user.getPassword());
                System.out.println("Login页面传递的参数psw：" + psw);
                System.out.println("密码错误！");

            }
        });
        return success.get();
    }

    public static List<Friends> queryFriends() {
        FriendsMapper mapper = sqlSession.getMapper(FriendsMapper.class);
        List<Friends> list = mapper.selectAll();
        return list;
    }

    public static void deleteFriend(String name) {
        FriendsMapper mapper = sqlSession.getMapper(FriendsMapper.class);
        mapper.deleteFriend(name);
    }

    public static List<Friends> insertFriend(String name,String status) {
        FriendsMapper mapper = sqlSession.getMapper(FriendsMapper.class);
        Friends fr = new Friends();
        fr.setId(generateSixDigitsRandom());
        fr.setName(name);
        fr.setStatus(status);
        mapper.insertFriend(fr);
        List<Friends> list = mapper.selectAll();
        return list;
    }

    public static List<Friends> updateFriend(String name,String username) {
        FriendsMapper mapper = sqlSession.getMapper(FriendsMapper.class);
        System.out.println("更新前："+username);
        System.out.println("更新后："+name);
        mapper.updateFriend(name,username);
        List<Friends> list = mapper.selectAll();
        return list;
    }

    public static void insertRecord(String sender,String receiver,String content,String create_time) {
        ChatMapper mapper = sqlSession.getMapper(ChatMapper.class);
        MessageRecord mr = new MessageRecord();
        mr.setId(generateSixDigitsRandom());
        mr.setSender(sender);
        mr.setReceiver(receiver);
        mr.setContent(content);
        mr.setCreateTime(create_time);
        mapper.insertRecord(mr);
    }

    public static List<MessageRecord> selectAllRecord(String sender,String receiver) {
        ChatMapper mapper = sqlSession.getMapper(ChatMapper.class);
        MessageRecord mr = new MessageRecord();
        mr.setSender(sender);
        mr.setReceiver(receiver);
        List<MessageRecord> ls = mapper.selectRecord(mr);
        return ls;
    }
}
