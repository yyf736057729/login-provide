package com.vici.loginprovide.service;

import com.vici.loginprovide.entity.GenericEntity;
import com.vici.loginprovide.entity.SevenMonth;
import com.vici.loginprovide.model.Child;
import com.vici.loginprovide.model.Hospital;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName ChildService
 * @Description
 * @Author vici_yyb
 * @Date 2019/1/21 16:01
 * @Version V2.0
 **/
public interface ChildService {
    //添加儿童
    String addChild(Child child);

    //根据姓名查询儿童
    List<Child> selectByName(Child child);

    //根据手机号查询儿童
    Child selectByPhone(Child child);

    String changeChildPwd(Child child, String oldPwd);

    String changeChildPhone(Child child, String newPhone);

    //上传金币数和训练时间
    void addGold(Child child);

    //更新已训练次数
    int updateChildTrain(Child child);

    Child selectById(Child child);

    List<Child> selectAllChild(Child child);

    List<Child> selectChildInMonth(Child child);

    String childLogin(Child child, Hospital hospital);

    int updateChildUnlock(Child child);

    //查询不活跃用户
    List<Child> slumberChild(Child child);

    //查询在训用户selectTrainingChild
    List<Child> selectTrainingChild(Child child);

    Integer addChildTrain(Child child) throws ParseException;

    List<Child> selectTrainAndScale(Child child);

    String forgetChildPwd(Child child);

    HashMap selectSevenMonth(Hospital hospital, Integer num);

    SevenMonth selectSevenMonth1(Hospital hospital);

    //查询本机构本周已经训练的人数
    int selectTrainedOnWeek(Child child);

    //小程序端登录
    GenericEntity childLoginWeChat(Child child);
}

