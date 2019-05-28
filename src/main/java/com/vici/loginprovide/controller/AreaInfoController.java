package com.vici.loginprovide.controller;


import com.vici.loginprovide.entity.ListEntity;
import com.vici.loginprovide.model.AreaInfo;
import com.vici.loginprovide.service.AreaInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 省市区
 */
@RequestMapping("/loginprovide")
@EnableAutoConfiguration
@RestController
@Slf4j
public class AreaInfoController {


    @Autowired
    private AreaInfoService areaInfoService;


    /**
     * 获取全部省
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getProvince",method = RequestMethod.POST)
    public ListEntity<AreaInfo> getProvince(){
        List<AreaInfo> province = areaInfoService.getProvince();
        ListEntity<AreaInfo> AreaInfoListEntity = new ListEntity<>();
        AreaInfoListEntity.setReturnContent(province);
        AreaInfoListEntity.setReturnCode("0");
        AreaInfoListEntity.setRetLog("省份信息查询完成！");
        return AreaInfoListEntity;
    }



    /**
     * 获取全部市
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getCity",method = RequestMethod.POST)
    public ListEntity<AreaInfo> getCity(int id){
        List<AreaInfo> province = areaInfoService.getCity(id);
        ListEntity<AreaInfo> AreaInfoListEntity = new ListEntity<>();
        AreaInfoListEntity.setReturnContent(province);
        AreaInfoListEntity.setReturnCode("0");
        AreaInfoListEntity.setRetLog("省份信息查询完成！");
        return AreaInfoListEntity;
    }



    /**
     * 获取全部区
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getArea",method = RequestMethod.POST)
    public ListEntity<AreaInfo> getArea(int id){
        List<AreaInfo> province = areaInfoService.getArea(id);
        ListEntity<AreaInfo> AreaInfoListEntity = new ListEntity<>();
        AreaInfoListEntity.setReturnContent(province);
        AreaInfoListEntity.setReturnCode("0");
        AreaInfoListEntity.setRetLog("区域信息查询完成！");
        return AreaInfoListEntity;
    }

}
