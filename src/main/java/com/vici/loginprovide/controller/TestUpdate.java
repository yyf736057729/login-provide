//package com.vici.loginprovide.controller;
//
//import com.vici.loginprovide.dao.ChildMapper;
//import com.vici.loginprovide.dao.HospitalMapper;
//import com.vici.loginprovide.model.Child;
//import com.vici.loginprovide.model.Hospital;
//import com.vici.loginprovide.util.logUtil.DateUtils;
//import com.vici.loginprovide.util.logUtil.TimeUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.util.Date;
//import java.util.List;
//
///**
// * @ClassName TestUpdate
// * @Description
// * @Author vici_yyb
// * @Date 2019/3/23 16:40
// * @Version V2.0
// **/
//@Controller
//@Slf4j
//@EnableAutoConfiguration
//public class TestUpdate {
//
//    @Autowired
//    private ChildMapper childMapper;
//
//    @Autowired
//    private TrainStatusMapper trainStatusMapper;
//
//    @Autowired
//    private HospitalMapper hospitalMapper;
//
//    @GetMapping("/update")
//    public void testUpdate(){
//    }
//
//    @PostMapping("/update1")
//    public String testUpdate1(@RequestParam("trainNum") Integer number,
//                            @RequestParam("trainNowDay") Integer number2){
//        Child child = new Child();
//        child.setChildTrain(number);
//        child.setChildId(28);
//        int i = childMapper.updateByPrimaryKeySelective(child);
//        System.out.println(i);
//
//        TrainStatus trainStatus = new TrainStatus();
//        trainStatus.setTrainStatusNum(number2);
//        trainStatus.setTrainStatusChildId(28);
//        int i1 = trainStatusMapper.updateTrainStatus(trainStatus);
//        System.out.println(i1);
//
//        System.out.println(number);
//        return "update";
//
//    }
//
//    @ResponseBody
//    @PostMapping("/update2")
//    public int testOne(int num) throws ParseException {
//        Hospital hospital1 = hospitalMapper.selectByPrimaryKey(num);
//        String dateTime = DateUtils.formatDateTime(new Date());
//        String s = DateUtils.formatDate(hospital1.getHospitalCreateTime());
//        List<String> monthBetween = TimeUtils.getMonthBetween(s,dateTime);
//        System.out.println(monthBetween.size());
//        return monthBetween.size();
//    }
//
//    public static void main(String[] args) throws IOException {
////        String json = "{\"objects\" : [\"One\", \"Two\", \"Three\"]}";
////
////        JsonNode arrNode = new ObjectMapper().readTree(json).get("objects");
////        boolean array = arrNode.isArray();
////        System.out.println(array);
////        if (arrNode.isArray()) {
////            for (JsonNode objNode : arrNode) {
////                System.out.println(objNode);
////            }
////        }
//
//
//    }
//
//}
