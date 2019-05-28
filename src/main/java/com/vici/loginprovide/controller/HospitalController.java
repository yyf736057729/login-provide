package com.vici.loginprovide.controller;

import com.vici.loginprovide.aop.ControllerLogs;
import com.vici.loginprovide.entity.BaseEntity;
import com.vici.loginprovide.entity.GenericEntity;
import com.vici.loginprovide.model.Hospital;
import com.vici.loginprovide.model.User;
import com.vici.loginprovide.service.impl.HospitalServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName HospitalController
 * @Description
 * @Author vici_yyb
 * @Date 2019/1/21 9:25
 * @Version V1.0
 **/
@RequestMapping("/loginprovide")
@EnableAutoConfiguration
@RestController
@Slf4j
public class HospitalController {

    @Autowired
    private HospitalServiceImpl hospitalService;

    /**
     * 机构注册接口(添加)
     *
     * @param hospital
     * @param code
     * @param request
     * @return
     */
    @ControllerLogs(description = "机构注册")
    @Transactional
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public BaseEntity register(Hospital hospital, String code, User user, HttpServletRequest request) {

        HttpSession session = request.getSession();
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
        String register = hospitalService.addHospital(hospital, session,user);
        if (register.equals("-1")) {
            baseEntity.setReturnCode("-1");
            baseEntity.setRetLog("用户账号已存在！");
        }

        if (register.equals("0")) {
            baseEntity.setReturnCode("0");
            baseEntity.setRetLog("注册成功！");
        }
        return baseEntity;
    }

    /**
     * 根据手机号查询机构信息
     * @param hospital
     * @return
     */
    @ControllerLogs(description = "医院信息查询")
    @ResponseBody
    @RequestMapping(value = "/selectHospitalByPhone", method = RequestMethod.POST)
    public GenericEntity<Hospital> selectHospitalByPhone(Hospital hospital) {
        Hospital hospital1 = hospitalService.selectByPhone(hospital);
        GenericEntity<Hospital> hospitalListEntity = new GenericEntity<>();
        if (hospital1!=null){
            hospitalListEntity.setReturnContent(hospital1);
            hospitalListEntity.setRetLog("医院信息查询成功！");
            hospitalListEntity.setReturnCode("0");
        }else {
            hospitalListEntity.setRetLog("没有此用户信息，请检查手机号是否正确！");
            hospitalListEntity.setReturnCode("-1");
        }
        return hospitalListEntity;
    }

    /**
     * 根据机构名称查询机构信息
     * @param hospital
     * @return
     */
    @ControllerLogs(description = "根据机构名称查询机构信息")
    @ResponseBody
    @RequestMapping(value = "/selectHospitalByName", method = RequestMethod.POST)
    public GenericEntity<Hospital> selectByName(Hospital hospital) {

        Hospital hospital1 = hospitalService.selectByName(hospital);
        GenericEntity<Hospital> hospitalListEntity = new GenericEntity<>();
        if (hospital1!=null){
            hospitalListEntity.setReturnContent(hospital1);
            hospitalListEntity.setRetLog("医院信息查询成功！");
            hospitalListEntity.setReturnCode("0");
        }else {
            hospitalListEntity.setRetLog("没有此用户信息，请检查名称是否正确！");
            hospitalListEntity.setReturnCode("-1");
        }
        return hospitalListEntity;
    }

    /**
     * 修改医院信息
     * @param hospital
     * @return
     */
    @ControllerLogs(description = "修改医院信息")
    @Transactional
    @RequestMapping(value = "/updateHospital",method = RequestMethod.POST)
    public BaseEntity updateHospital(Hospital hospital, HttpServletRequest request){
        BaseEntity baseEntity = new BaseEntity();

        int i = hospitalService.updateHospital(hospital);
        if (i==1){
            baseEntity.setReturnCode("0");
            baseEntity.setRetLog("医院信息修改成功！");
        }else {
            baseEntity.setRetLog("信息有误，修改失败！");
            baseEntity.setReturnCode("-1");
        }
        return baseEntity;
    }

    /**
     * 修改机构手机号
     * @param hospital
     * @param code
     * @return
     */
    @RequestMapping(value = "/modifyUserPhone",method = RequestMethod.POST)
    public BaseEntity modifyUserPhone(Hospital hospital, String code, HttpServletRequest request){
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
        final int i = hospitalService.modifyUserPhone(hospital);
        if (i==-1){
            baseEntity.setRetLog("修改失败！");
            baseEntity.setReturnCode("-1");
        }else if (i==1){
            baseEntity.setReturnCode("0");
            baseEntity.setRetLog("修改成功！");
        }
        return baseEntity;
    }

    /**
     * 验证管理员手机号
     * @param hospital
     * @param code
     * @param request
     * @return
     */
    @RequestMapping(value = "/verifyUserPhone",method = RequestMethod.POST)
    public BaseEntity verifyUserPhone(Hospital hospital, String code, HttpServletRequest request){
        BaseEntity baseEntity = new BaseEntity();
        Object checkCode = request.getSession().getAttribute("checkCode");
        Hospital hospital1 = hospitalService.selectByPhone(hospital);
        if (hospital1==null){
            baseEntity.setReturnCode("-3");
            baseEntity.setRetLog("验证失败");
        }
        if (checkCode == null) {
            baseEntity.setReturnCode("-1");
            baseEntity.setRetLog("无效验证码");
            return baseEntity;
        } else if (!checkCode.toString().equals(code)) {
            baseEntity.setReturnCode("-2");
            baseEntity.setRetLog("验证码错误");
            return baseEntity;
        }else {
            baseEntity.setRetLog("验证成功！");
            baseEntity.setReturnCode("0");
        }
        return baseEntity;
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/verifyUserName",method = RequestMethod.POST)
    public BaseEntity verifyUserName(Hospital hospital){
        BaseEntity baseEntity = new BaseEntity();
        Hospital hospital1 = hospitalService.selectByName(hospital);
        if(hospital1 != null){
            baseEntity.setReturnCode("-1");
            baseEntity.setRetLog("机构名称重复！");
            return baseEntity;
        }
        baseEntity.setRetLog("验证成功！");
        baseEntity.setReturnCode("0");
        return baseEntity;
    }
}
