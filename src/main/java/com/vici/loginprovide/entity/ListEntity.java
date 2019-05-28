package com.vici.loginprovide.entity;

import java.util.List;

/**
 * @ClassName ListEntity
 * @Description
 * @Author vici_yyb
 * @Date 2019/1/21 9:31
 * @Version V1.0
 **/
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListEntity<T> extends BaseEntity {

    private List<T> returnContent;

    public List<T> getReturnContent() {
        return returnContent;
    }

    public void setReturnContent(List<T> returnContent) {
        this.returnContent = returnContent;
    }

    @Override
    public String toString() {
        return "ListEntity{" +
                "returnContent=" + returnContent +
                '}';
    }
}
