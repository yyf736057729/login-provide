package com.vici.loginprovide.service.impl;

import com.vici.loginprovide.dao.LogMapper;
import com.vici.loginprovide.model.Log;
import com.vici.loginprovide.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName LogServiceImpl
 * @Description
 * @Author vici_yyb
 * @Date 2019/1/21 13:14
 * @Version V1.0
 **/
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public void addLog(Log log) {

    }
}
