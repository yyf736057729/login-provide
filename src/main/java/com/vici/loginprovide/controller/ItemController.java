package com.vici.loginprovide.controller;

import com.vici.loginprovide.entity.BaseEntity;
import com.vici.loginprovide.entity.ListEntity;
import com.vici.loginprovide.model.Item;
import com.vici.loginprovide.service.impl.ItemServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName ItemController
 * @Description
 * @Author vici_yyb
 * @Date 2019/3/15 18:24
 * @Version V2.0
 **/
@RequestMapping("/loginprovide")
@EnableAutoConfiguration
@RestController
@Slf4j
public class ItemController {

    @Autowired
    private ItemServiceImpl itemService;

    @RequestMapping(value = "/addItem",method = RequestMethod.POST)
    public void addItem(Item item){
        itemService.addItem(item);
    }

    /**
     * 查询项目版本信息
     * @return
     */
    @RequestMapping(value = "/selectAllItem",method = RequestMethod.GET)
    public ListEntity selectAllItem(){
        ListEntity<Item> itemListEntity = new ListEntity<>();
        List<Item> items = itemService.selectAllItem();

        itemListEntity.setReturnContent(items);
        itemListEntity.setRetLog("项目版本号查询完成");
        itemListEntity.setReturnCode("0");

        return itemListEntity;
    }

    /**
     * 修改项目版本信息
     * @param item
     * @return
     */
    @RequestMapping(value = "/updateItem",method = RequestMethod.POST)
    public BaseEntity updateItem(Item item){

        BaseEntity baseEntity = new BaseEntity();
        int i = itemService.updateItem(item);
        if (i==1){
            baseEntity.setReturnCode("0");
            baseEntity.setRetLog("修改成功");
        }else {
            baseEntity.setReturnCode("0");
            baseEntity.setRetLog("修改成功");
        }
        return baseEntity;
    }
}
