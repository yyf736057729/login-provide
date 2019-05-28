package com.vici.loginprovide.aop;

import java.lang.annotation.*;

/**
 * @ClassName ControllerLogs
 * @Description Controller日志记录
 * @Author vici_yyb
 * @Date 2019/1/21 9:57
 * @Version V2.0
 **/
@Target({ElementType.METHOD})//注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME)//注解在哪个阶段执行
@Documented //生成文档
public @interface ControllerLogs {

    /**
     * 描述
     */
    String description() default "";

}
