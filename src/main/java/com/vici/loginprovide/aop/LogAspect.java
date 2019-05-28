package com.vici.loginprovide.aop;

import com.alibaba.fastjson.JSON;
import com.vici.loginprovide.dao.LogMapper;
import com.vici.loginprovide.dao.UserMapper;
import com.vici.loginprovide.model.Log;
import com.vici.loginprovide.model.User;
import com.vici.loginprovide.util.logUtil.AddressUtils;
import com.vici.loginprovide.util.logUtil.StringUtils;
import com.vici.loginprovide.util.logUtil.UserAgentUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.vici.loginprovide.util.logUtil.IpUtils.getRemoteAddr;
import static com.vici.loginprovide.util.logUtil.StringUtils.transStringToMap;

/**
 * @ClassName LogAspect
 * @Description
 * @Author vici_yyb
 * @Date 2019/1/21 9:37
 * @Version V1.0
 **/
@Aspect
@Component
public class LogAspect {

    @Autowired
    LogMapper logMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 本地异常日志记录
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * service层切点
     */
    @Pointcut("@annotation(com.vici.loginprovide.aop.ServiceLogs)")
    public void serviceAspect(){

    }

    /**
     * Controller层切点
     */
    @Pointcut("@annotation(com.vici.loginprovide.aop.ControllerLogs)")
    public void controllerAspect() {

    }

    /**
     * 前置通知，用于拦截controller层记录用户操作
     *
     * @param joinPoint 切点
     */
    @Before("controllerAspect()")
    public void addBefore(JoinPoint joinPoint){

        try {
            /**
             * 通过RequestContextHolder来获取HttpRequest和HttpResponse
             * spring boot提供用于获取函数参数中没有给出request和response或者session时获取http request和http response
             */
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Log log = new Log();
            //类名
            String className = joinPoint.getTarget().getClass().getName();
            //请求方法
            String method =  joinPoint.getSignature().getName() + "()";
            //方法参数
//            String methodParam = JSON.toJSONString(joinPoint.getArgs());

            /**
             * getParameterMap的返回值为Map<String, String[]>从中取得请求参数转换为Map<String,String>的方法
             */
            Map<String, String[]> params = request.getParameterMap();
            Map<String, String> tempMap = new HashMap<String, String>();
            Set<Map.Entry<String, String[]>> set = params.entrySet();
//            Iterator<Map.Entry<String, String[]>> it = set.iterator();
//            while (it.hasNext()) {
//                Map.Entry<String, String[]> entry = it.next();
//                System.out.println("KEY:"+entry.getKey());
//                for (String str : entry.getValue()) {
//                    System.out.println(str);
//                    tempMap.put(entry.getKey(), str);
//                }
//            }
            /**
             * 遍历map集合
             */
            for (Map.Entry<String, String[]> entry : set) {
                String key = entry.getKey();
                if (entry.getKey().equals("userPhone")){
                    String[] value = entry.getValue();
                    User user = userMapper.selectUserByPhone(value[0]);
                    log.setLogUser(user.getUserName());
                    log.setLogUserId(user.getUserId());
                }
                String[] value = entry.getValue();
                System.out.println(key + ": " + value);
            }

            String decode = "";
            //针对get请求
            if(request.getQueryString()!=null){
                try {
                    decode = URLDecoder.decode(request.getQueryString(),"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else{
                //针对post请求
                for (String key : params.keySet()) {
                    String[] values = params.get(key);
                    for (int i = 0; i < values.length; i++) {
                        String value = values[i];
                        decode += key + "=" + value + "&";
                    }
                }
            }

            //将String根据&转成Map
            Map<String, Object> methodParamMap = transStringToMap(decode, "&", "=");
            //设置日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //方法描述
            String methodDescription = getControllerMethodDescription(joinPoint);
            StringBuilder sb = new StringBuilder(1000);
            sb.append("\n");
            sb.append("*********************************Request请求***************************************");
            sb.append("\n");
            sb.append("ClassName     :  ").append(className).append("\n");
            sb.append("RequestMethod :  ").append(method).append("\n");
            sb.append("ContentType   :  ").append(("".equals(request.getContentType()) || request.getContentType() == null)?"FROM":request.getContentType()).append("\n");
//            sb.append("RequestParams :  ").append(("".equals(decode) || decode == null)?methodParam:methodParamMap).append("\n");
            sb.append("RequestType   :  ").append(request.getMethod()).append("\n");
            sb.append("Description   :  ").append(methodDescription).append("\n");
            sb.append("ServerAddr    :  ").append(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()).append("\n");
            sb.append("RemoteAddr    :  ").append(getRemoteAddr(request)).append("\n");
            UserAgent userAgent = UserAgentUtils.getUserAgent(request);
            sb.append("DeviceName    :  ").append(userAgent.getOperatingSystem().getName()).append("\n");
            sb.append("BrowserName   :  ").append(userAgent.getBrowser().getName()).append("\n");
            sb.append("UserAgent     :  ").append(request.getHeader("User-Agent")).append("\n");
            sb.append("RequestUri    :  ").append(StringUtils.abbr(request.getRequestURI(), 255)).append("\n");
            sb.append("**************************");
            sb.append(df.format(new Date()));
            sb.append("***********************************");
            sb.append("\n");
            logger.info(sb.toString());

            log.setLogClassName(className);
            log.setLogRequestMethod(method);
            log.setLogContentType(("".equals(request.getContentType()) || request.getContentType() == null)?"FROM":request.getContentType());
            log.setLogRequestType(request.getMethod());
            log.setLogDescription(methodDescription);
            log.setLogServerAddr(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
            log.setLogRemoteAddr(getRemoteAddr(request));
            log.setLogDeviceName(userAgent.getOperatingSystem().getName());
            log.setLogBrowserName(userAgent.getBrowser().getName());
            log.setLogUserAgent(request.getHeader("User-Agent"));
            log.setLogRequestUri(StringUtils.abbr(request.getRequestURI(),255));
            log.setLogTime(new Date());
            AddressUtils addressUtils = new AddressUtils();
            try {
                String address = addressUtils.getAddresses("ip="+getRemoteAddr(request), "utf-8");
//                String address = addressUtils.getAddresses("ip="+"183.156.48.157", "utf-8");
//                System.out.println(address);
                log.setLogAddress(address);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            logMapper.addLog(log);
//            System.out.println(sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterReturning(returning = "ret", pointcut = "controllerAspect()")
    public void doAfterReturning(Object ret) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //请求方法
        String method = StringUtils.abbr(request.getRequestURI(), 255);
        StringBuilder sb = new StringBuilder(1000);
        // 处理完请求，返回内容
        sb.append("\n");
        sb.append("Result        :  ").append(ret);
        logger.info(sb.toString());
    }


    /**
     * 异常通知 用于拦截service层记录异常日志
     *
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            //类名
            String className = joinPoint.getTarget().getClass().getName();
            //请求方法
            String method =  (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()");
            //方法参数
            String methodParam = Arrays.toString(joinPoint.getArgs());
            //方法描述
            String methodDescription = getServiceMthodDescription(joinPoint);
            //获取用户请求方法的参数并序列化为JSON格式字符串
            String params = "";
            if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                for (int i = 0; i < joinPoint.getArgs().length; i++) {
                    params += JSON.toJSONString(joinPoint.getArgs()[i]) + ";";
                }
            }
            StringBuilder sb = new StringBuilder(1000);
            sb.append("\n");
            sb.append("*********************************Service异常***************************************");
            sb.append("\n");
            sb.append("ClassName        :  ").append(className).append("\n");
            sb.append("Method           :  ").append(method).append("\n");
            sb.append("Params           :  ").append("[" + params + "]").append("\n");
            sb.append("Description      :  ").append(methodDescription).append("\n");
            sb.append("ExceptionName    :  ").append(ex.getClass().getName()).append("\n");
            sb.append("ExceptionMessage :  ").append(ex.getMessage()).append("\n");
            logger.info(sb.toString());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    /**
     * 获取注解中对方法的描述信息 用于service层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getServiceMthodDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        for (int i = 0; i <arguments.length ; i++) {
            Object argument = arguments[i];
        }
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(ServiceLogs.class).description();
                    break;
                }
            }
        }
        return description;
    }
    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(ControllerLogs.class).description();
                    break;
                }
            }
        }
        return description;
    }

}
