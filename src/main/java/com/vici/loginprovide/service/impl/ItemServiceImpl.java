package com.vici.loginprovide.service.impl;

import com.vici.loginprovide.dao.ItemMapper;
import com.vici.loginprovide.model.Item;
import com.vici.loginprovide.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName ItemServiceImpl
 * @Description
 * @Author vici_yyb
 * @Date 2019/3/15 18:17
 * @Version V2.0
 **/
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public void addItem(Item item) {
        item.setItemCreateTime(new Date());
        item.setItemModifyTime(new Date());
        itemMapper.insert(item);
    }

    @Override
    public List<Item> selectAllItem() {
        List<Item> items = itemMapper.selectAllItem();
        return items;
    }

    @Override
    public int updateItem(Item item) {
        item.setItemModifyTime(new Date());
        int i = itemMapper.updateByPrimaryKeySelective(item);
        return i;
    }
}
