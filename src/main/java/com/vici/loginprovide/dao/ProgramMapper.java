package com.vici.loginprovide.dao;

import com.vici.loginprovide.model.Program;

public interface ProgramMapper {
    int deleteByPrimaryKey(Integer programId);

    int insert(Program record);

    int insertSelective(Program record);

    Program selectByPrimaryKey(Integer programId);

    int updateByPrimaryKeySelective(Program record);

    int updateByPrimaryKey(Program record);
}