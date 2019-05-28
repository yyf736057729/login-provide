package com.vici.loginprovide.service;


import com.vici.loginprovide.model.AreaInfo;

import java.util.List;

public interface AreaInfoService {

    List<AreaInfo> getProvince();

    List<AreaInfo> getCity(int id);

    List<AreaInfo> getArea(int id);
}
