package com.vici.loginprovide.dao;


import com.vici.loginprovide.model.AreaInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AreaInfoMapper {

    List<AreaInfo> getAreaInfo(@Param("areaLevel") int areaLevel);

    List<AreaInfo> getCity(@Param("id") int id, @Param("areaLevel") int areaLevel);

    List<AreaInfo> getArea(@Param("id") int id, @Param("areaLevel") int areaLevel);
}
