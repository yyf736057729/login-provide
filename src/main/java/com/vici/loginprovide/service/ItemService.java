package com.vici.loginprovide.service;

import com.vici.loginprovide.model.Item;

import java.util.List;

/**
 * @ClassName ItemService
 * @Description
 * @Author vici_yyb
 * @Date 2019/3/15 18:15
 * @Version V2.0
 **/
public interface ItemService {

    void addItem(Item item);

    List<Item> selectAllItem();

    int updateItem(Item item);

}
