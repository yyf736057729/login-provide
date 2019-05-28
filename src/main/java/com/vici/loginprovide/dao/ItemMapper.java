package com.vici.loginprovide.dao;

import com.vici.loginprovide.model.Item;

import java.util.List;

public interface ItemMapper {

    int deleteByPrimaryKey(Integer itemId);

    int insert(Item record);

    int insertSelective(Item record);

    Item selectByPrimaryKey(Integer itemId);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);

    List<Item> selectAllItem();
}