package com.mapper;

import com.chat.entity.Friends;
import com.chat.entity.MessageRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatMapper {
    List<MessageRecord> selectRecord(@Param("mr") MessageRecord mr);
    void insertRecord(@Param("mr") MessageRecord mr);
}
