package com.vici.loginprovide.service;

import com.vici.loginprovide.entity.DateEntity;
import com.vici.loginprovide.model.User;

import java.util.List;

/**
 * @ClassName UserService
 * @Description
 * @Author vici_yyb
 * @Date 2019/1/23 17:56
 * @Version V2.0
 **/
public interface UserService {

    String addUser(User user);

    List<User> selectUserByName(User user);

    User selectUserByPhone(User user);

    User updateUser(User user);

    String deleteUser(User user);

    DateEntity userLogin(User user);

    String hospitalManage(User user);

    List<User> selectAllUser(User user);

    User selectOneUser(User user);
}
