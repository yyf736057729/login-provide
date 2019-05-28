package com.vici.loginprovide.config.interceptor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by yyb on 2018/8/8.
 * bean拦截器
 */
@Component
public class BeanProvider implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanProvider.applicationContext=applicationContext;
    }


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    public static <T> T getBean(Class<T> clazz){
        if(applicationContext == null){
            return null;
        }
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String beanId){
        if(applicationContext == null){
            return null;
        }
        return (T)applicationContext.getBean(beanId);
    }
}
