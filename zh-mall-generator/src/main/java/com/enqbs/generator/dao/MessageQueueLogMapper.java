package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.MessageQueueLog;

public interface MessageQueueLogMapper {

    int deleteByPrimaryKey(Long messageId);

    int insert(MessageQueueLog record);

    int insertSelective(MessageQueueLog record);

    MessageQueueLog selectByPrimaryKey(Long messageId);

    int updateByPrimaryKeySelective(MessageQueueLog record);

    int updateByPrimaryKey(MessageQueueLog record);

}
