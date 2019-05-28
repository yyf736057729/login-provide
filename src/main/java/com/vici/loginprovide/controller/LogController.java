package com.vici.loginprovide.controller;

import com.vici.loginprovide.model.Log;
import com.vici.loginprovide.service.impl.LogServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LogController
 * @Description
 * @Author vici_yyb
 * @Date 2019/1/21 13:15
 * @Version V1.0
 **/
@RequestMapping("/loginprovide")
@EnableAutoConfiguration
@RestController
@Slf4j
public class LogController {

    @Autowired
    private LogServiceImpl logService;

    /**
     * 日志记录
     * @param log
     */
    @ResponseBody
    @RequestMapping(value = "/addLog", method = RequestMethod.POST)
    public void addLog(Log log){
        logService.addLog(log);
    }



}
