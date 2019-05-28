package com.vici.loginprovide.dao;

import com.vici.loginprovide.model.Log;

public interface LogMapper {

    void addLog(Log log);

    int deleteByPrimaryKey(Long logId);

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);
}