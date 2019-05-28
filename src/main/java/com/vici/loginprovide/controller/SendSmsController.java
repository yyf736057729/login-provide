package com.vici.loginprovide.controller;

import com.vici.loginprovide.aop.ControllerLogs;
import com.vici.loginprovide.entity.BaseEntity;
import com.vici.loginprovide.util.GetMessageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @ClassName SendSmsController
 * @Description
 * @Author vici_yyb
 * @Date 2019/2/14 17:26
 * @Version V2.0
 **/
@RequestMapping("/loginprovide")
@EnableAutoConfiguration
@RestController
@Slf4j
public class SendSmsController extends HttpServlet {

    private static final long serialVersionUID = -8940196742313994740L;

    /**
     * 获取短信验证码
     * @param phone
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @ControllerLogs(description = "获取短信验证码！")
    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    protected BaseEntity doPost(String phone, HttpServletRequest request)
            throws ServletException, IOException {
        BaseEntity baseEntity = new BaseEntity();
        String code = GetMessageCode.getCode(phone);

        final HttpSession session = request.getSession();
        String id = request.getSession().getId();//打印本次回话的session id
        System.out.println("获取验证码请求的sessionID:" + id);

        session.setAttribute("checkCode",code);
        try {
            //TimerTask实现2分钟后从session中删除checkCode
//            java中自带的定时工具
            final Timer timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    session.removeAttribute("checkCode");
                    System.out.println("checkCode删除成功");
                    timer.cancel();
                }
                //验证码失效时间
            },2*60*1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(code);
        if (code.equals("00174")){
            baseEntity.setReturnCode("00174");
            baseEntity.setRetLog("一分钟内下发短信超过次数限制");
        }else if (code.equals("00141")){
            baseEntity.setReturnCode("00141");
            baseEntity.setRetLog("一小时内发送给单个手机次数超过限制");
        }else if (code.equals("00142")){
            baseEntity.setReturnCode("00142");
            baseEntity.setRetLog("一天内发送给单个手机次数超过限制");
        }else if (code.equals("00025")){
            baseEntity.setRetLog("手机号格式不对");
            baseEntity.setReturnCode("00025");
        } else {
            baseEntity.setReturnCode("0");
            baseEntity.setRetLog("短信已发送，请注意查收");
        }
        return baseEntity;
    }

    /**
     * 获取超级管理员管理手机号
     * @param phone
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public static String doPost1(String phone, HttpServletRequest request)
            throws ServletException, IOException {
        String code = GetMessageCode.getCode(phone);

        final HttpSession session = request.getSession();
        String id = request.getSession().getId();//打印本次回话的session id
        System.out.println("获取验证码请求的sessionID:" + id);

        session.setAttribute("checkCode",code);
        try {
            //TimerTask实现15分钟后从session中删除checkCode
//            java中自带的定时工具
            final Timer timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    session.removeAttribute("checkCode");
                    System.out.println("checkCode删除成功");
                    timer.cancel();
                }
                //验证码失效时间
            },15*60*1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(code);
        return code;
    }
    //获取浏览器页面传递过来的参数(手机号)
    /**
     * 1.继承HttpServlet类
     * 2.在Web.xml里面注册Servlet或者(web3.0新特性 注解的方法实现)
     * 3.实现对于方法
     */

}
