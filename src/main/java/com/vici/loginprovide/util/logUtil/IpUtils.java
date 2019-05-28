package com.vici.loginprovide.util.logUtil;

import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName IpUtils
 * @Description
 * @Author vici_yyb
 * @Date 2019/1/21 10:00
 * @Version V2.0
 **/
public class IpUtils {
    /**
     * 获取客户端IP地址
     * @param request
     * @return
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return org.apache.commons.lang3.StringUtils.split(ObjectUtils.toString(ip), ",")[0];
    }
}
