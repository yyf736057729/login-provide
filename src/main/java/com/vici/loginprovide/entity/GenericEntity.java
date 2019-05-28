package com.vici.loginprovide.entity;

/**
 * @ClassName GenericEntity
 * @Description
 * @Author vici_yyb
 * @Date 2019/1/21 11:41
 * @Version V1.0
 **/
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericEntity<T> extends BaseEntity {

    private T returnContent;

    public T getReturnContent() {
        return returnContent;
    }

    public void setReturnContent(T returnContent) {
        this.returnContent = returnContent;
    }
}
