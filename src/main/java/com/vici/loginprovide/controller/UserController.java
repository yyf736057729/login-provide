package com.vici.loginprovide.controller;

import com.vici.loginprovide.aop.ControllerLogs;
import com.vici.loginprovide.common.R;
import com.vici.loginprovide.dao.UserMapper;
import com.vici.loginprovide.entity.*;
import com.vici.loginprovide.model.User;
import com.vici.loginprovide.service.impl.UserServiceImpl;
import com.vici.loginprovide.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @ClassName UserController
 * @Description
 * @Author vici_yyb
 * @Date 2019/1/24 9:55
 * @Version V2.0
 **/
@RequestMapping("/loginprovide")
@EnableAutoConfiguration
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录接口
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public DateEntity userLogin(User user){
        DateEntity baseEntity = new DateEntity();
        DateEntity dateEntity = userService.userLogin(user);
        if (dateEntity.getReturnCode()=="-4"){
            baseEntity.setReturnCode("-4");
            baseEntity.setRetLog("该账号尚未缴费，请联系管理员");
        }else if (dateEntity.getReturnCode()=="-3"){
            baseEntity.setRetLog("使用权限已过期，续费后继续使用！");
            baseEntity.setReturnCode("-3");
        }else if (dateEntity.getReturnCode()=="-2"){
            baseEntity.setRetLog("账号或密码错误！");
            baseEntity.setReturnCode("-2");
        }else if (dateEntity.getReturnCode()=="-1"){
            baseEntity.setReturnCode("-1");
            baseEntity.setRetLog("管理员信息不存在！");
        }else {
            User user1 = userMapper.selectUserByPhone(user);
            User user2 = userMapper.selectUserLogin(user1);
            baseEntity.setRetLog("登录成功！");
            baseEntity.setReturnCode("0");
            baseEntity.setReturnContent(user2);
            baseEntity.setDate(dateEntity.getDate());
        }
        return baseEntity;
    }

    /**
     * 查询机构信息
     * @param user
     * @return
     */
    @RequestMapping(value = "/selectOneUser", method = RequestMethod.POST)
    public GenericEntity selectOneUser(User user){
        User user2 = userMapper.selectUserByPhone(user);
        User user1 = userMapper.selectOneUser(user2);
        GenericEntity<User> userGenericEntity = new GenericEntity<>();
        userGenericEntity.setReturnContent(user1);
        userGenericEntity.setReturnCode("0");
        userGenericEntity.setRetLog("机构信息查询完成");

        return userGenericEntity;
    }

    /**
     * 用户修改密码接口（根据手机号）
     * @param user
     * @param code
     * @param request
     * @return
     */
    @ControllerLogs(description = "修改管理员密码")
    @ResponseBody
    @RequestMapping(value = "/forgetPwd", method = RequestMethod.POST)
    public BaseEntity modifyPwd(User user, String code, HttpServletRequest request){
        BaseEntity baseEntity = new BaseEntity();
        Object checkCode = request.getSession().getAttribute("checkCode");
        String id = request.getSession().getId();//打印本次会话的session id
        System.out.println("前端二次请求的sessionID:" + id);
        if (checkCode == null) {
            baseEntity.setReturnCode("-2");
            baseEntity.setRetLog("无效验证码");
            return baseEntity;
        } else if (!checkCode.toString().equals(code)) {
            baseEntity.setReturnCode("-3");
            baseEntity.setRetLog("验证码错误");
            return baseEntity;
        }
        User user1 = new User();
        User user2 = userMapper.selectUserByPhone(user.getUserPhone());
        if (user2==null){
            baseEntity.setReturnCode("-1");
            baseEntity.setRetLog("用户信息修改失败！");
            return baseEntity;
        }
        user1.setUserId(user2.getUserId());
        user1.setUserModifyTime(new Date());
        user1.setUserPwd(MD5.md5Encrpt(user.getUserPwd()));
        int i = userMapper.updateByPrimaryKeySelective(user1);
        if (i!=0){
            baseEntity.setReturnCode("0");
            baseEntity.setRetLog("用户密码修改成功！");
        }
        return baseEntity;
    }


    /**
     * 添加新管理员
     * @param user
     * @param userEntiy
     * @return
     */
    @Transactional
    @ResponseBody
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public BaseEntity addUser(User user, UserEntiy userEntiy){
        User user1 = userMapper.selectByPrimaryKey(userEntiy.getAdminId());
        BaseEntity baseEntity = new BaseEntity();
        if (user1.getUserStatus().equals("1")){
            String s = userService.addUser(user);
            if (s=="0"){
                baseEntity.setReturnCode("0");
                baseEntity.setRetLog("管理员添加成功");
            }else if (s=="-1"){
                baseEntity.setReturnCode("-1");
                baseEntity.setRetLog("手机号已存在，管理员添加失败！");
            }else if (s=="-2"){
                baseEntity.setReturnCode("-2");
                baseEntity.setRetLog("手机号为空！管理员添加失败！");
            }
            return baseEntity;
        }else {
            baseEntity.setReturnCode("-3");
            baseEntity.setRetLog("非超级管理员，不能添加普通管理员！");
            return baseEntity;
        }
    }

    /**
     * 查询本机构内所有管理员信息
     * @param user
     * @param userEntiy
     * @return
     */
    @RequestMapping(value = "/selectAllUser",method = RequestMethod.POST)
    public ListEntity selectAllUser(User user,UserEntiy userEntiy){
        ListEntity<User> userListEntity = new ListEntity<>();
        List<User> userList = userService.selectAllUser(user);
        User user1 = userMapper.selectByPrimaryKey(userEntiy.getAdminId());
        if (user1.getUserStatus().equals("1")){
            userListEntity.setReturnCode("0");
            userListEntity.setRetLog("管理员信息查询成功");
            userListEntity.setReturnContent(userList);
        }else {
            userListEntity.setRetLog("没有操作权限");
            userListEntity.setReturnCode("-1");
        }
        return userListEntity;
    }

    /**
     * 根据姓名查询信息
     * @param user
     * @param userEntiy
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectUserByName", method = RequestMethod.POST)
    public ListEntity<User> selectUserByName(User user,UserEntiy userEntiy){
        User user1 = userMapper.selectByPrimaryKey(userEntiy.getAdminId());
        ListEntity<User> userListEntity = new ListEntity<>();
        if(user1.getUserStatus().equals("1")){
            List<User> userList = userService.selectUserByName(user);
            if (userList.size()==0){
                userListEntity.setReturnCode("-1");
                userListEntity.setRetLog("没有该管理员的信息，请检查输入信息！");
            }else {
                userListEntity.setReturnCode("0");
                userListEntity.setRetLog("管理员信息查询成功！");
                userListEntity.setReturnContent(userList);
            }
            return userListEntity;

        }else {
            userListEntity.setRetLog("非超级管理员，不能执行查询操作！");
            userListEntity.setReturnCode("-2");
            return userListEntity;
        }
    }

    /**
     * 自定义异常，测试方法
     * @param user
     * @param userEntiy
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectUserByName1", method = RequestMethod.POST)
    public R selectUserByName1(User user, UserEntiy userEntiy){
        User user1 = userMapper.selectByPrimaryKey(userEntiy.getAdminId());
        if(user1.getUserStatus().equals("1")){
            List<User> userList = userService.selectUserByName(user);
            if (userList.size()==0){
               return R.error(-1,"没有此人信息");
            }else {
               return R.ok().put("values",userList);
            }
        }else {
           return R.error(-2,"没有权限");
        }
    }

    /**
     * 根据手机号查询管理员信息
     * @param user
     * @param userEntiy
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectUserByPhone1", method = RequestMethod.POST)
    public GenericEntity<User> selectUserByPhone1(User user, UserEntiy userEntiy){
        User user1 = userMapper.selectByPrimaryKey(userEntiy.getAdminId());
        GenericEntity<User> userGenericEntity = new GenericEntity<>();
        if(user1.getUserStatus().equals("1")){
            User user2 = userService.selectUserByPhone(user);
            if (user2!=null){
                userGenericEntity.setReturnCode("0");
                userGenericEntity.setRetLog("管理员信息查询成功！");
                userGenericEntity.setReturnContent(user2);
            }else{
                userGenericEntity.setReturnCode("-1");
                userGenericEntity.setRetLog("没有该管理员的信息，请检查输入信息！");
            }
            return userGenericEntity;
        }else {
            userGenericEntity.setRetLog("非超级管理员，不能执行查询操作！");
            userGenericEntity.setReturnCode("-2");
            return userGenericEntity;
        }
    }

    /**
     * 根据手机号查询管理员信息
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectUserByPhone", method = RequestMethod.POST)
    public GenericEntity<User> selectUserByPhone(User user){
        GenericEntity<User> userGenericEntity = new GenericEntity<>();
            User user2 = userService.selectUserByPhone(user);
            if (user2!=null){
                userGenericEntity.setReturnCode("0");
                userGenericEntity.setRetLog("管理员信息查询成功！");
                userGenericEntity.setReturnContent(user2);
            }else{
                userGenericEntity.setReturnCode("-1");
                userGenericEntity.setRetLog("没有该管理员的信息，请检查输入信息！");
            }
            return userGenericEntity;
    }

    /**
     * 删除一个用户
     * @param user
     * @param userEntiy
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public BaseEntity deleteUser(User user, UserEntiy userEntiy){
        User user1 = userMapper.selectByPrimaryKey(userEntiy.getAdminId());
        BaseEntity baseEntity = new BaseEntity();
        if(user1.getUserStatus().equals("1")){
            int i = userMapper.deleteByPrimaryKey(user.getUserId());
            baseEntity.setRetLog("删除成功！");
            baseEntity.setReturnCode("0");
        }else {
            baseEntity.setRetLog("没有删除权限！");
            baseEntity.setReturnCode("-1");
        }
        return baseEntity;
    }

    /**
     * 修改普通管理员信息
     * @param user
     * @param userEntiy
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modifyUser", method = RequestMethod.POST)
    public BaseEntity modifyUser(User user, UserEntiy userEntiy){
        User user1 = userMapper.selectByPrimaryKey(userEntiy.getAdminId());
        BaseEntity baseEntity = new BaseEntity();
        if(user1.getUserStatus().equals("1")){
            User user2 = userService.updateUser(user);
            if (user2==null){
                baseEntity.setRetLog("信息修改失败！");
                baseEntity.setReturnCode("-1");
            }else {
                baseEntity.setRetLog("信息修改成功！");
                baseEntity.setReturnCode("0");
            }
            return baseEntity;
        }else{
            baseEntity.setRetLog("非超级管理员，不能执行查询操作！");
            baseEntity.setReturnCode("-2");
            return baseEntity;
        }
    }

    /**
     * 判断一个管理员是不是超级管理员
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/adminUser", method = RequestMethod.POST)
    public boolean adminUser(User user){
        User user1 = userMapper.selectByPrimaryKey(user.getUserId());
        if (user1.getUserStatus().equals("1")){
            return true;
        }else {
            return false;
        }
    }

    @RequestMapping(value = "/userTest", method = RequestMethod.POST)
    public ListEntity userTest(User user){

        List<User> userList = userMapper.userTest(user);
        ListEntity<User> userListEntity = new ListEntity<>();
        userListEntity.setReturnContent(userList);
        return userListEntity;
    }


    //扫码登录



}
