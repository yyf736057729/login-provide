package com.vici.loginprovide.dao;

import com.vici.loginprovide.model.Hospital;

public interface HospitalMapper {

    //机构注册
    void addHospital(Hospital hospital);

    //根据手机号查询信息
    Hospital selectByPhone(String hospitalPhone);

    //跟据手机号获取超级管理员信息
    Hospital selectByPhone(Hospital hospitalPhone);

    //根据名称查询机构信息
    Hospital selectByName(Hospital hospital);

    int deleteByPrimaryKey(Integer hospitalId);

    int insert(Hospital record);

    int insertSelective(Hospital record);

    Hospital selectByPrimaryKey(Integer hospitalId);

    int updateByPrimaryKeySelective(Hospital record);

    int updateByPrimaryKey(Hospital record);

    //获取机构已注册的月数
    int getMonthNum(Hospital hospital);
}