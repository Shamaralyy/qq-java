package com.mapper;

import com.chat.entity.Friends;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendsMapper {
    List<Friends> selectAll();
    void deleteFriend(@Param("username") String username);
    void insertFriend(@Param("fr") Friends fr);
    void updateFriend(@Param("newName") String newName,@Param("username") String username);
}
