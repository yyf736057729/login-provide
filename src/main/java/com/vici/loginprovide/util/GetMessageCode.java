package com.vici.loginprovide.util;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @ClassName GetMessageCode
 * @Description 发送短信验证码的类
 * @Author vici_yyb
 * @Date 2019/1/21 9:29
 * @Version V1.0
 **/
public class GetMessageCode {

    private static final String QUERY_PATH = "https://api.miaodiyun.com/20150822/industrySMS/sendSMS";

    //    凡聚科技公司ID
    private static final String ACCOUNT_SID = "3d01ecc9fdb44cd394e565753bd08656";
    private static final String AUTH_TOKEN = "3b44565b9ab340b5b02555fbbf436b28";


    /**
     * 返回手机号
     * @param phone
     * @return
     */
    public static String getCode(String phone) {
        String random = smsCode();
        String timestamp = getTimestamp();
        String sig = getMD5(ACCOUNT_SID, AUTH_TOKEN, timestamp);
        String tamp = "【凡聚科技】"+"您的验证码为：{"+random+"}，请于2分钟内正确输入，如非本人操作，请忽略此短信。";
//        String tamp = "【束手就擒】"+"验证码为：{"+random+"}，请于2分钟内正确输入，如非本人操作，请忽略此短信。";
        StringBuilder result = new StringBuilder();
        OutputStreamWriter out = null;
        BufferedReader br = null;
        try {
            URL url = new URL(QUERY_PATH);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);//设置参数
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");//:
            out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
            String args = getQueryArgs(ACCOUNT_SID, tamp, phone, timestamp, sig, "JSON");
            out.write(args);
            out.flush();

            //读取返回结果

            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String temp = "";
            while ((temp = br.readLine()) != null) {
                result.append(temp);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        org.json.JSONObject json = null;
        try {
            json = new org.json.JSONObject(result.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String respCode = null;
        try {
            respCode = json.getString("respCode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String defaultRespCode = "00000";
        if (defaultRespCode.equals(respCode)) {
            return random;
        } else {
            return respCode;
        }
    }

    /**
     *参数拼接
     *
     * @param accountSid
     * @param smsContent
     * @param to
     * @param timestamp
     * @param sig
     * @param respDataType
     * @return
     */
    public static String getQueryArgs(String accountSid, String smsContent, String to, String timestamp, String sig, String respDataType) {
        return "accountSid=" + accountSid + "&smsContent=" + smsContent
                + "&to=" + to + "&timestamp=" + timestamp + "&sig=" + sig
                + "&respDataType=" + respDataType;
    }

    public static String getTimestamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    /**
     * SIG签名
     *
     * @param sid
     * @param token
     * @param timestamp
     * @return
     * @throws
     */
    public static String getMD5(String sid, String token, String timestamp) {
        StringBuilder result = new StringBuilder();
        String source = sid + token + timestamp;
        try {

            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(source.getBytes());
            for (byte b : bytes) {
                String hex = Integer.toHexString(b & 0xff);
                if (hex.length() == 1) {
                    result.append("0" + hex);

                } else {
                    result.append(hex);
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
        return result.toString();
    }

    /**
     * (产生验证码)
     *
     * @return
     */
    public static String smsCode() {
        String ran = new Random().nextInt(1000000) + "";
        if (ran.length() != 6) {
            return smsCode();
        } else {
            return ran;
        }
    }

  /*  public static void main(String[] args) {
        String code = getCode("13819149764");
        System.out.println(code);
    }*/

}
