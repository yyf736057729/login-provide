package com.vici.loginprovide.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName MD5
 * @Description 密码加密类，使用MD5进行加密
 * @Author vici_yyb
 * @Date 2019/1/22 10:15
 * @Version V2.0
 **/
public class MD5 {

    /**
     * md5加密(实现方法)
     * @param value
     * @return
     */
    public static String md5Encrpt(String value){
        StringBuilder sd=new StringBuilder();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(value.getBytes());
//            加密SendSmsController
            byte[] target = md5.digest();
//            转化为string
            for (int i = 0; i <target.length ; i++) {
                int x=(int) target[i]&0xff;
                //“加盐”对加密之后的文件再加密
                x=x+1;
                if (x<16){
                    sd.append(0);
                }
                sd.append(Integer.toHexString(x));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sd.toString();
    }
    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr){

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }

    // 测试主函数
    public static void main(String args[]) {
        String s = new String("123");
//        System.out.println("原始：" + s);
//        System.out.println("MD5后：" + string2MD5(s));
//        System.out.println("加密的：" + convertMD5(s));
//        System.out.println("解密的：" + convertMD5(convertMD5(s)));
        System.out.println(md5Encrpt(s));
    }

}
