package com.vici.loginprovide.service;

import com.vici.loginprovide.model.Hospital;
import com.vici.loginprovide.model.User;

import javax.servlet.http.HttpSession;

/**
 * @ClassName HospitalService
 * @Description
 * @Author vici_yyb
 * @Date 2019/1/21 9:24
 * @Version V1.0
 **/
public interface HospitalService {

    String addHospital(Hospital hospital, HttpSession session, User user);

    Hospital selectByPhone(Hospital hospital);

    Hospital selectByName(Hospital hospital);

    int updateHospital(Hospital hospital);

    int modifyUserPhone(Hospital hospital);
}
