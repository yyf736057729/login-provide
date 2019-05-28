package com.vici.loginprovide.model;

/**
 * 区县行政编码字典表
 */
public class AreaInfo {


    private int id;

    //名称
    private String name;
    //层级标识： 1  省份， 2  市， 3  区县
    private String areaLevel;
    //父节点
    private int parentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
