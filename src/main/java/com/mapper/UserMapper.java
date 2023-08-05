package com.mapper;

import com.chat.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    List<User> queryAll();
    List<User> selectAll(@Param("username") String username);
    List<User> selectSection(@Param("str") String str);
    boolean insertUser(@Param("uuid") int uuid, @Param("username") String username, @Param("password") String password);
}
