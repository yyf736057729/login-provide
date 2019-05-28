package com.vici.loginprovide.config.webconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName ViciConfig
 * @Description 读取yml中的文件上传路径配置
 * @Author vici_yyb
 * @Date 2019/2/21 16:00
 * @Version V2.0
 **/
@Component
@ConfigurationProperties(prefix = "vici")
@Data
public class ViciConfig {

    //二维码图片保存路径
    private String imagePath;

}
