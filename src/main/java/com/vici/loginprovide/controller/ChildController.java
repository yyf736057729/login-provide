package com.vici.loginprovide.controller;

import com.vici.loginprovide.aop.ControllerLogs;
import com.vici.loginprovide.dao.ChildMapper;
import com.vici.loginprovide.dao.HospitalMapper;
import com.vici.loginprovide.entity.*;
import com.vici.loginprovide.model.Child;
import com.vici.loginprovide.model.Hospital;
import com.vici.loginprovide.service.impl.ChildServiceImpl;
import com.vici.loginprovide.util.logUtil.DateUtils;
import com.vici.loginprovide.util.logUtil.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName ChildController
 * @Description
 * @Author vici_yyb
 * @Date 2019/1/21 16:09
 * @Version V2.0
 **/
@RequestMapping("/loginprovide")
@EnableAutoConfiguration
@RestController
@Slf4j
public class ChildController {

    @Autowired
    private ChildServiceImpl childService;

    @Autowired
    private ChildMapper childMapper;

    @Autowired
    private HospitalMapper hospitalMapper;

    /**
     * 查询本机构内全部的用户信息
     *
     * @param hospital
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllChild", method = RequestMethod.POST)
    public ListEntity<Child> selectAllChild(Hospital hospital) {
        Child child = new Child();
        child.setChildHospitalId(hospital.getHospitalId());
        List<Child> children = childService.selectAllChild(child);

        ListEntity<Child> childListEntity = new ListEntity<>();

        childListEntity.setReturnContent(children);
        childListEntity.setReturnCode("0");
        childListEntity.setRetLog("用户信息查询完成！");
        return childListEntity;
    }

    /**
     * 查询本月内新增的用户量
     *
     * @param hospital
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectChildInMonth", method = RequestMethod.POST)
    public ListEntity<Child> selectChildInMonth(Hospital hospital) {
        Child child = new Child();
        child.setChildHospitalId(hospital.getHospitalId());
        List<Child> children = childService.selectChildInMonth(child);

        ListEntity<Child> childListEntity = new ListEntity<>();

        childListEntity.setReturnContent(children);
        childListEntity.setReturnCode("0");
        childListEntity.setRetLog("用户信息查询完成！");
        return childListEntity;
    }

    /**
     * 用户注册
     *
     * @param child
     * @return
     */
    @Transactional
    @ControllerLogs(description = "添加儿童")
    @ResponseBody
    @RequestMapping(value = "/addChild", method = RequestMethod.POST)
    public BaseEntity addChild(Child child, String code, HttpServletRequest request) {

        BaseEntity baseEntity = new BaseEntity();
        Object checkCode = request.getSession().getAttribute("checkCode");
        String id = request.getSession().getId();//打印本次会话的session id
        System.out.println("前端二次请求的sessionID:" + id);
        if (checkCode == null) {
            baseEntity.setReturnCode("-4");
            baseEntity.setRetLog("无效验证码");
            return baseEntity;
        } else if (!checkCode.toString().equals(code)) {
            baseEntity.setReturnCode("-5");
            baseEntity.setRetLog("验证码错误");
            return baseEntity;
        }

        String s = childService.addChild(child);
        if (s == "0") {
            baseEntity.setRetLog("新用户添加成功！");
            baseEntity.setReturnCode("0");
        } else if (s == "-1") {
            baseEntity.setReturnCode("-1");
            baseEntity.setRetLog("手机号已存在！");
        } else if (s == "-2") {
            baseEntity.setRetLog("手机号不能为空！");
            baseEntity.setReturnCode("-2");
        } else if (s == "-3") {
            baseEntity.setReturnCode("-3");
            baseEntity.setRetLog("医院id不能为空！");
        }
        return baseEntity;
    }

    /**
     * 用户登录 PC端登录
     * 解锁判断交由客户端处理
     * @param child
     * @return
     */
    @ControllerLogs(description = "用户登录")
    @ResponseBody
    @Transactional
    @RequestMapping(value = "/childLogin", method = RequestMethod.POST)
    public GenericEntity childLogin(Child child, Hospital hospital) {
        GenericEntity baseEntity = new GenericEntity();
        String s = childService.childLogin(child, hospital);

        if (s=="-3"){
            baseEntity.setReturnCode("-3");
            baseEntity.setRetLog("医院id为空");
        }else if (s == "-2") {
            baseEntity.setReturnCode("-2");
            baseEntity.setRetLog("用户不存在！");
        } else if (s == "-1") {
            baseEntity.setReturnCode("-1");
            baseEntity.setRetLog("账号密码错误！");
        } else if (s=="0"){
            Child child1 = childMapper.selectByPhone(child);
            Date childTrainTime = child1.getChildTrainTime();
            boolean b = TimeUtils.dateFormat(childTrainTime);
            if (b) {
                baseEntity.setReturnCode("0");
                baseEntity.setRetLog("登录成功，欢迎使用！");
                baseEntity.setReturnContent(child1);
            } else {
                int num = child1.getChildTrain();
                int i = num % 4;
                if (i == 0) {
                    baseEntity.setReturnContent(child1);
                } else {
                    int j = num / 4;
                    child1.setChildTrain((j + 1) * 4);
                }
                baseEntity.setReturnCode("0");
                baseEntity.setRetLog("登录成功，欢迎使用！");
                baseEntity.setReturnContent(child1);
            }
        }else if (s=="1"){
            baseEntity.setRetLog("0");
            baseEntity.setRetLog("首次登陆，信息绑定完成！");
        }
        return baseEntity;
    }

    /**
     * 小程序端登录
     * @param child
     * @return
     */
    @ControllerLogs(description = "用户登录")
    @ResponseBody
    @RequestMapping(value = "/childLoginWeChat", method = RequestMethod.POST)
    public GenericEntity childLoginWeChat(Child child){

        GenericEntity baseEntity = new GenericEntity();
        GenericEntity genericEntity = childService.childLoginWeChat(child);

        if (genericEntity.getReturnCode() == "-2") {
            baseEntity.setReturnCode("-2");
            baseEntity.setRetLog("用户不存在！");
        } else if (genericEntity.getReturnCode() =="-1") {
            baseEntity.setReturnCode("-1");
            baseEntity.setRetLog("账号密码错误！");
        } else if (genericEntity.getReturnCode()=="0"){
            baseEntity.setRetLog("登录成功！");
            baseEntity.setReturnContent(genericEntity.getReturnContent());
            baseEntity.setReturnCode("0");
        }
        return baseEntity;
    }


    /**
     * 根据姓名查询儿童信息
     *
     * @param child
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectChildByName", method = RequestMethod.POST)
    public ListEntity<Child> selectByName(Child child) {

        List<Child> children = childService.selectByName(child);
        final ListEntity<Child> childListEntity = new ListEntity<>();
        if (children.size() == 0) {
            childListEntity.setRetLog("没有找到该小朋友的信息，请检查输入的姓名是否正确！");
            childListEntity.setReturnCode("-1");
            childListEntity.setReturnContent(children);
        } else {
            childListEntity.setReturnCode("0");
            childListEntity.setRetLog("信息查询完成！");
            childListEntity.setReturnContent(children);
        }
        return childListEntity;
    }

    /**
     * 根据手机号查询儿童信息
     *
     * @param child
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectChildByPhone", method = RequestMethod.POST)
    public GenericEntity<Child> selectByPhone(Child child) {

        GenericEntity<Child> childGenericEntity = new GenericEntity<>();

        Child child1 = childService.selectByPhone(child);
        if (child1 != null) {
            childGenericEntity.setReturnCode("0");
            childGenericEntity.setRetLog("用户信息查询成功！");
            childGenericEntity.setReturnContent(child1);
        } else {
            childGenericEntity.setReturnCode("-1");
            childGenericEntity.setRetLog("用户信息查询失败！");
            childGenericEntity.setReturnContent(null);
        }
        return childGenericEntity;
    }

    /**
     * 查询不活跃用户
     *
     * @param child
     * @return
     */
    @RequestMapping(value = "/slumberChild", method = RequestMethod.POST)
    public ListEntity slumberChild(Child child) {
        ListEntity<Child> childListEntity = new ListEntity<>();

        List<Child> children = childService.slumberChild(child);
        if (children.size() != 0) {
            childListEntity.setReturnContent(children);
            childListEntity.setReturnCode("0");
            childListEntity.setRetLog("不活跃用户查询完成");
        } else {
            childListEntity.setRetLog("暂无不活跃用户");
            childListEntity.setReturnCode("0");
            childListEntity.setReturnContent(children);
        }
        return childListEntity;
    }

    /**
     * 查询在训用户
     *
     * @param child
     * @return
     */
    @RequestMapping(value = "/selectTrainingChild", method = RequestMethod.POST)
    public ListEntity selectTrainingChild(Child child) {
        ListEntity<Child> childListEntity = new ListEntity<>();

        List<Child> children = childService.selectTrainingChild(child);
        if (children.size() != 0) {
            childListEntity.setReturnContent(children);
            childListEntity.setReturnCode("0");
            childListEntity.setRetLog("在训用户查询完成");
        } else {
            childListEntity.setRetLog("暂无在训用户");
            childListEntity.setReturnCode("0");

        }
        return childListEntity;
    }

    /**
     * 设置训练方案
     *
     * @param child
     * @return
     */
    @ControllerLogs(description = "设置用户训练方案")
    @Transactional
    @RequestMapping(value = "/addChildTrain", method = RequestMethod.POST)
    public BaseEntity addChildTrain(Child child) throws ParseException {

        Integer s = childService.addChildTrain(child);
        BaseEntity baseEntity = new BaseEntity();
        if (s == 1) {
            baseEntity.setReturnCode("0");
            baseEntity.setRetLog("方案设置成功！");
        } else {
            baseEntity.setRetLog("用户不存在");
            baseEntity.setReturnCode("-1");
        }
        return baseEntity;
    }

    /**
     * 解锁用户使用权限
     *
     * @param child
     * @return
     */
    @RequestMapping(value = "/updateChildUnlock", method = RequestMethod.POST)
    public BaseEntity updateChildUnlock(Child child) {
        BaseEntity baseEntity = new BaseEntity();
        int i = childService.updateChildUnlock(child);

        if (i != 0) {
            baseEntity.setReturnCode("0");
            baseEntity.setRetLog("用户已解锁，欢迎使用");
        } else {
            baseEntity.setRetLog("解锁失败");
            baseEntity.setReturnCode("-1");
        }
        return baseEntity;
    }

    /**
     * 修改手机号
     *
     * @param child
     * @return
     */
    @ControllerLogs(description = "修改手机号")
    @Transactional
    @ResponseBody
    @RequestMapping(value = "/changeChildPhone", method = RequestMethod.POST)
    public BaseEntity changeChildPhone(Child child, String code, String newPhone, HttpServletRequest request) {

        BaseEntity baseEntity = new BaseEntity();
        Object checkCode = request.getSession().getAttribute("checkCode");
        String id = request.getSession().getId();//打印本次会话的session id
        System.out.println("前端二次请求的sessionID:" + id);
        if (checkCode == null) {
            baseEntity.setReturnCode("-3");
            baseEntity.setRetLog("无效验证码");
            return baseEntity;
        } else if (!checkCode.toString().equals(code)) {
            baseEntity.setReturnCode("-4");
            baseEntity.setRetLog("验证码错误");
            return baseEntity;
        }
        String s = childService.changeChildPhone(child, newPhone);
        if (s == "0") {
            baseEntity.setReturnCode("0");
            baseEntity.setRetLog("用户手机号修改成功！");
        } else if (s == "-1") {
            baseEntity.setReturnCode("-1");
            baseEntity.setRetLog("用户不存在，手机号修改失败！");
        } else if (s == "-2") {
            baseEntity.setReturnCode("-2");
            baseEntity.setRetLog("医院id为空，手机号修改失败！");
        }
        return baseEntity;
    }

    /**
     * 查询儿童的训练和量表信息
     *
     * @param child
     * @return
     */
    @RequestMapping(value = "/selectTrainAndScale", method = RequestMethod.POST)
    public ListEntity selectTrainAndScale(Child child) {

        ListEntity<Child> childListEntity = new ListEntity();
        List<Child> children = childService.selectTrainAndScale(child);

        childListEntity.setReturnCode("0");
        childListEntity.setRetLog("训练和量表信息查询完成！");
        childListEntity.setReturnContent(children);

        return childListEntity;
    }

    /**
     * 修改用户密码
     *
     * @param child
     * @return
     */
    @ControllerLogs(description = "修改用户密码")
    @Transactional
    @ResponseBody
    @RequestMapping(value = "/changeChildPwd", method = RequestMethod.POST)
    public BaseEntity changeChildPwd(Child child, String oldPwd) {

        String s = childService.changeChildPwd(child, oldPwd);
        BaseEntity baseEntity = new BaseEntity();

        if (s.equals("-3")) {
            baseEntity.setReturnCode("-3");
            baseEntity.setRetLog("原密码输入错误！");
        } else if (s.equals("-2")) {
            baseEntity.setRetLog("医院id为空");
            baseEntity.setReturnCode("-2");
        } else if (s.equals("-1")) {
            baseEntity.setReturnCode("-1");
            baseEntity.setRetLog("用户不存在");
        } else {
            baseEntity.setRetLog("用户密码修改成功");
            baseEntity.setReturnCode("0");
        }
        return baseEntity;
    }

    /**
     * 用户忘记密码
     *
     * @param child
     * @param code
     * @param request
     * @return
     */
    @ControllerLogs(description = "用户忘记密码")
    @Transactional
    @ResponseBody
    @RequestMapping(value = "/forgetChildPwd", method = RequestMethod.POST)
    public BaseEntity forgetChildPwd(Child child, String code, HttpServletRequest request) {
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
        String s = childService.forgetChildPwd(child);
        if (s == "-1") {
            baseEntity.setReturnCode("-1");
            baseEntity.setRetLog("用户不存在");
        } else if (s == "0") {
            baseEntity.setRetLog("密码修改成功");
            baseEntity.setReturnCode("0");
        }
        return baseEntity;
    }


    /**
     * 查询最近12个月每月新增人数
     *
     * @param hospital
     * @return
     */
    @Transactional
    @RequestMapping(value = "/selectTwelveMonth", method = RequestMethod.POST)
    public ListGenericEtity selectTwelveMonth(Hospital hospital) throws ParseException {
        ListGenericEtity sevenMonthGenericEntity = new ListGenericEtity();
        Hospital hospital1 = hospitalMapper.selectByPrimaryKey(hospital.getHospitalId());

        //1.获取机构已存在的月数
        String dateTime = DateUtils.formatDateTime(new Date());
        String s = DateUtils.formatDate(hospital1.getHospitalCreateTime());
        List<String> monthBetween = TimeUtils.getMonthBetween(s,dateTime);

        SevenMonth sevenMonth = childService.selectSevenMonth1(hospital);
        //2.获取最近n个月的月份
        Calendar c = Calendar.getInstance();
        //c.add(Calendar.MONTH, -monthNum);
        c.add(Calendar.MONTH, -10);//获取最近12个月的月份
        String before_six = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH);//六个月前
        LinkedList<String> result = new LinkedList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        try {
            min.setTime(sdf.parse(before_six));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
            max.setTime(sdf.parse(sdf.format(new Date())));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }
        System.out.println(result);

        sevenMonthGenericEntity.setReturnCode("0");
        sevenMonthGenericEntity.setRetLog("新增人数查询完成！");
        sevenMonthGenericEntity.setReturnContent(sevenMonth);
        sevenMonthGenericEntity.setListDate(result);
        sevenMonthGenericEntity.setMonthNum(monthBetween.size());
        return sevenMonthGenericEntity;
    }

    /**
     * 查询本周已经训练的人数
     * @param child
     * @return
     */
    @RequestMapping(value = "/selectTrainedOnWeek", method = RequestMethod.POST)
    public  GenericEntity selectTrainedOnWeek(Child child) {
        GenericEntity genericEntity = new GenericEntity();
        int i = childService.selectTrainedOnWeek(child);
        genericEntity.setRetLog("查询完成");
        genericEntity.setReturnCode("0");
        genericEntity.setReturnContent(i);
        return genericEntity;
    }



    /**
     * 修改训练次数（测试使用）
     *
     * @param child
     * @return
     */
    @RequestMapping(value = "/modifyChildTrain", method = RequestMethod.GET)
    public String modifyChildTrain(Child child) {

        final int i = childMapper.updateByPrimaryKeySelective(child);
        if (i != 0) {
            return "训练次数修改成功";
        } else {
            return "训练次数修改失败";
        }
    }


}
