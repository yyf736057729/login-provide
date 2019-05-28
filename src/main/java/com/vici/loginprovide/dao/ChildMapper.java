package com.vici.loginprovide.dao;

import com.vici.loginprovide.model.Child;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChildMapper {

    //添加儿童
    void addChild(Child child);

    //根据姓名查询儿童
    List<Child> selectByName(Child child);

    //根据手机号查询儿童
    Child selectByPhone(Child child);

    //修改儿童信息
    void changeChild(Child child);

    //查询本机构内全部的用户信息
    List<Child> selectAllChild(Child child);

    //查询本机构本月新增的用户信息
    List<Child> selectChildInMonth(Child child);

    //用户登录
    Child childLogin(Child child);

    //查询在训用户
    List<Child> selectTrainingChild(Child child);

    //查询不活跃用户
    List<Child> slumberChild(Child child);

    //查询本机构本周已经训练的人数
    int selectTrainedOnWeek(Child child);

    int deleteByPrimaryKey(Integer childId);

    int insert(Child record);

    int insertSelective(Child record);

    Child selectByPrimaryKey(Integer childId);

    int updateByPrimaryKeySelective(Child record);

    int updateByPrimaryKey(Child record);

    //根据儿童id查询训练和量表信息
    List<Child> selectTrainAndScale(Child child);

    //根据手机号和医院机构id查询用户数据
    Child selectByPhoneHospitalId(Child child);

    //根据医院id查询每月新增人数
    int selectAddOneMonth(@Param("childHospitalId") Integer childHospitalId, @Param("monthNum") Integer monthNum);
}