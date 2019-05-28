package com.vici.loginprovide.service.impl;

import com.vici.loginprovide.dao.HospitalMapper;
import com.vici.loginprovide.dao.UserMapper;
import com.vici.loginprovide.model.Hospital;
import com.vici.loginprovide.model.User;
import com.vici.loginprovide.service.HospitalService;
import com.vici.loginprovide.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @ClassName HospitalServiceImpl
 * @Description
 * @Author vici_yyb
 * @Date 2019/1/21 9:25
 * @Version V1.0
 **/
@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalMapper hospitalMapper;

    @Autowired
    private UserMapper userMapper;
    /**
     * 机构注册方法
     * 同时添加医院超级管理员信息
     *
     * @param hospital
     * @param session
     * @return
     */
    @Override
    public String addHospital(Hospital hospital, HttpSession session, User user) {

        Hospital hospital1 = new Hospital();
        if (hospitalMapper.selectByPhone(hospital.getHospitalPhone()) != null) {
            return "-1";
        } else {
            //添加医院信息
            hospital1.setHospitalAddress(hospital.getHospitalAddress());
            hospital1.setHospitalCreateTime(new Date());
            hospital1.setHospitalEmail(hospital.getHospitalEmail());
            hospital1.setHospitalModifyTime(new Date());
            hospital1.setHospitalName(hospital.getHospitalName());
            hospital1.setHospitalPhone(hospital.getHospitalPhone());
            hospital1.setHospitalUser(hospital.getHospitalUser());
            hospitalMapper.addHospital(hospital1);

            //添加超级管理员信息
            Hospital hospital2 = hospitalMapper.selectByPhone(hospital1);
            User user1 = new User();
            user1.setUserPwd(MD5.md5Encrpt(user.getUserPwd()));
            user1.setUserPhone(hospital2.getHospitalPhone());
            user1.setUserName(hospital2.getHospitalUser());
            user1.setUserStatus("1");
            user1.setUserHospitalId(hospital2.getHospitalId());
            user1.setUserCreateTime(new Date());
            user1.setUserModifyTime(hospital2.getHospitalModifyTime());

            userMapper.addUser(user1);
            return "0";
        }
    }


    /**
     * 根据手机号查询机构信息
     * @param hospital
     * @return
     */
    @Override
    public Hospital selectByPhone(Hospital hospital) {

        Hospital hospital1 = hospitalMapper.selectByPhone(hospital);

        return hospital1;
    }

    /**
     * 根据机构姓名查询机构信息
     * @param hospital
     * @return
     */
    @Override
    public Hospital selectByName(Hospital hospital) {

        Hospital hospital1 = hospitalMapper.selectByName(hospital);

        return hospital1;
    }

    /**
     * 修改医院信息方法，同时需要更新user中的超级管理员信息
     * @param hospital
     * @return
     */
    @Override
    public int updateHospital(Hospital hospital) {

        if (hospital.getHospitalId()!=null){
            //修改医院信息
            Hospital hospital1 = new Hospital();
            User user = new User();
            hospital1.setHospitalUser(hospital.getHospitalUser());
            hospital1.setHospitalId(hospital.getHospitalId());
            hospital1.setHospitalName(hospital.getHospitalName());
            hospital1.setHospitalModifyTime(new Date());
            hospital1.setHospitalAddress(hospital.getHospitalAddress());
            hospital1.setHospitalEmail(hospital.getHospitalEmail());
            hospital1.setHospitalPhone(hospital.getHospitalPhone());
            int i = hospitalMapper.updateByPrimaryKeySelective(hospital1);

            //修改超级管理员信息
            User user1 = new User();
            user1.setUserHospitalId(hospital.getHospitalId());
            User user2 = userMapper.selectAdmin(user1);
            user.setUserId(user2.getUserId());
            user.setUserName(hospital.getHospitalUser());
            user.setUserHospitalName(hospital.getHospitalName());
            user.setUserModifyTime(new Date());
            userMapper.updateByPrimaryKeySelective(user);
            return i;
        }else {
            return -1;
        }
    }

    /**
     * 修改机构手机号 同时更新超级管理员手机号
     * @param hospital
     * @return
     */
    @Override
    public int modifyUserPhone(Hospital hospital) {

        if (hospital.getHospitalId()!=null){
            Hospital hospital1 = new Hospital();
            hospital1.setHospitalPhone(hospital.getHospitalPhone());
            hospital1.setHospitalId(hospital.getHospitalId());
            hospital1.setHospitalModifyTime(new Date());

            int i = hospitalMapper.updateByPrimaryKeySelective(hospital1);

            User user = new User();
            user.setUserHospitalId(hospital.getHospitalId());
            User user1 = userMapper.selectAdmin(user);
            User user2 = new User();
            user2.setUserId(user1.getUserId());
            user2.setUserModifyTime(new Date());
            user2.setUserPhone(hospital.getHospitalPhone());

            int i1 = userMapper.updateByPrimaryKeySelective(user2);
            return i;
        }

        return -1;
    }

    /**
     * 续费通道
     * @param hospital
     */
    public void Renew(Hospital hospital){

        Hospital hospital1 = hospitalMapper.selectByPhone(hospital);
        Date hospitalExpirationTime = hospital1.getHospitalExpirationTime();
        Hospital hospital2 = new Hospital();
        Date date = new Date();
        Calendar calendar   =   new GregorianCalendar();

        if (hospitalExpirationTime.getTime()<new Date().getTime()){
            calendar.setTime(date);
            //在当前时间上添加一年
        }else {
            calendar.setTime(hospitalExpirationTime);
        }
        calendar.add(Calendar.YEAR,1);
        Date time = calendar.getTime();
        hospital2.setHospitalExpirationTime(time);
        hospital2.setHospitalId(hospital1.getHospitalId());
        hospitalMapper.updateByPrimaryKeySelective(hospital2);
    }
}
