package com.vici.loginprovide.service.impl;

import com.vici.loginprovide.dao.HospitalMapper;
import com.vici.loginprovide.dao.UserMapper;
import com.vici.loginprovide.entity.DateEntity;
import com.vici.loginprovide.model.Hospital;
import com.vici.loginprovide.model.User;
import com.vici.loginprovide.service.UserService;
import com.vici.loginprovide.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description
 * @Author vici_yyb
 * @Date 2019/1/23 17:56
 * @Version V2.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HospitalMapper hospitalMapper;


    /**
     * 管理员登录（返回使用时间，判断是否过期）
     * @param user
     * @return
     */
    @Override
    public DateEntity userLogin(User user) {
        User user1 = userMapper.selectUserByPhone(user);
        DateEntity dateEntity = new DateEntity();
        if (user1 == null) {
            dateEntity.setReturnCode("-1");
            return dateEntity;
        }
        Hospital hospital = new Hospital();
        hospital.setHospitalId(user1.getUserHospitalId());
        Hospital hospital1 = hospitalMapper.selectByPrimaryKey(hospital.getHospitalId());
        Date hospitalExpirationTime = hospital1.getHospitalExpirationTime();
        if (hospitalExpirationTime==null){
            dateEntity.setReturnCode("-4");
            return dateEntity;
        }
        Date date = new Date();
        long dataUtils = dataUtils(hospitalExpirationTime,date);
        if (!user1.getUserPwd().equals(MD5.md5Encrpt(user.getUserPwd()))) {
            dateEntity.setReturnCode("-2");
            return dateEntity;
        } else if (dataUtils < 0) {
            dateEntity.setReturnCode("-3");
            return dateEntity;
        }
        dateEntity.setDate(dataUtils);
        dateEntity.setReturnCode("0");
        return dateEntity;
    }



    /**
     * 平台管理时，判断是否是超级管理员，进行短信发送
     * @param user
     * @return
     */
    @Override
    public String hospitalManage(User user) {
        User user1 = userMapper.selectUserByPhone(user);
        if (user1.getUserStatus()=="1"){
            return "0";
        }else {
//            User user2 = userMapper.selectAdmin(user1);
//            String s = null;
//            try {
//                s = doPost1(user2.getUserPhone(), request);
//            } catch (ServletException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println(s);
//            return s;
            return null;
        }
    }

    @Override
    public List<User> selectAllUser(User user) {

        List<User> userList = userMapper.selectAllUser(user);
        return userList;
    }

    @Override
    public User selectOneUser(User user) {


        return null;
    }

    /**
     * 计算是否过期
     * @param d1
     * @param d2
     * @return
     */
    public long dataUtils(Date d1, Date d2) {
        long diff = d1.getTime() - d2.getTime();
//        System.out.println(diff/(1000 * 60 * 60));
        long days = diff / (1000 * 60 * 60 * 24);
        return days;
    }


    /**
     * 添加管理员的时候，判断手机号是否为空和手机号是否已经存在
     *
     * @param user
     * @return
     */
    @Override
    public String addUser(User user) {
        User user1 = new User();
        user1.setUserPhone(user.getUserPhone());
        if (user.getUserPhone() == "" || user.getUserPhone() == null) {
            return "-2";
        } else {
            if (userMapper.selectUserByPhone(user1) == null) {
                user1.setUserCreateTime(new Date());
                user1.setUserHospitalId(user.getUserHospitalId());
                user1.setUserModifyTime(new Date());
                user1.setUserName(user.getUserName());
                user1.setUserPwd(MD5.md5Encrpt(user.getUserPwd()));
                user1.setUserHospitalName(user.getUserHospitalName());
                user1.setUserStatus("2");

                userMapper.addUser(user1);
                return "0";
            } else {
                return "-1";//手机号已存在
            }
        }
    }

    @Override
    public List<User> selectUserByName(User user) {
        List<User> userList = userMapper.selectUserByName(user);
        return userList;
    }

    @Override
    public User selectUserByPhone(User user) {
        User user1 = userMapper.selectUserByPhone(user);
        return user1;
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    @Override
    public User updateUser(User user) {
        User user1 = new User();
        if (userMapper.selectByPrimaryKey(user.getUserId()) == null) {
            return null;
        } else {
            if (user.getUserPwd()!=null){
                user1.setUserPwd(MD5.md5Encrpt(user.getUserPwd()));
            }

            user1.setUserName(user.getUserName());
            user1.setUserPhone(user.getUserPhone());
            user1.setUserModifyTime(new Date());
            user1.setUserId(user.getUserId());
            userMapper.updateByPrimaryKeySelective(user1);
        }
        return user1;
    }

    /**
     * 删除一个用户
     * @param user
     * @return
     */
    @Override
    public String deleteUser(User user) {

        if (user.getUserId() == null) {
            return "-1";
        } else {
            int i = userMapper.deleteByPrimaryKey(user.getUserId());
            if (i == 1) {
                return "0";
            } else {
                return "-2";
            }
        }
    }

    //扫码登录

}
