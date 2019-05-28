package com.vici.loginprovide.config.webconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName WebConfigurer
 * @Description
 * @Author vici_yyb
 * @Date 2019/2/21 16:02
 * @Version V2.0
 **/
@Component
public class WebConfigurer extends WebMvcConfigurerAdapter {

    @Autowired
    ViciConfig viciConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/QRcode/**").addResourceLocations("file:///"+viciConfig.getImagePath());
    }
}
