package com.vici.loginprovide.dao;

import com.vici.loginprovide.model.User;

import java.util.List;

public interface UserMapper {

    //添加普通管理员（超级管理员后台添加）
    void addUser(User user);

    //根据姓名查找
    List<User> selectUserByName(User user);

    //根据手机号查询管理员（登录方法）
    User selectUserByPhone(User user);

    //返回超级管理员和医院信息
    User selectUserLogin(User user);

    //根据id删除一个普通管理员
    int deleteByPrimaryKey(Integer userId);

    //查询本机构内的超级管理员
    User selectAdmin(User user);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectUserByPhone(String s);

    //查询本机构内的全部管理员信息
    List<User> selectAllUser(User user);

    List<User> userTest(User user);

    //查询机构信息
    User selectOneUser(User user);
}