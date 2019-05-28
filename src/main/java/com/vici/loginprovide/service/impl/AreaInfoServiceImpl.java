package com.vici.loginprovide.service.impl;

import com.vici.loginprovide.dao.AreaInfoMapper;
import com.vici.loginprovide.model.AreaInfo;
import com.vici.loginprovide.service.AreaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaInfoServiceImpl implements AreaInfoService {

    @Autowired
    private AreaInfoMapper areaInfoMapper;

    @Override
    public List<AreaInfo> getProvince() {
        return areaInfoMapper.getAreaInfo(1);
    }


    @Override
    public List<AreaInfo> getCity(int id) {
        return areaInfoMapper.getCity(id,2);
    }

    @Override
    public List<AreaInfo> getArea(int id) {
        return areaInfoMapper.getArea(id,3);
    }
}
