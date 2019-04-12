package com.quxianggif.network.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/12 下午4:08
 * Describe:MD5加密辅助工具类
 */
public class MD5 {
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 对传入的字符串进行MD5加密
     * @param origin 原始字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String origin){
        try {
            MessageDigest digest=MessageDigest.getInstance("MD5");
            digest.update(origin.getBytes(Charset.defaultCharset()));
            return new String(toHex(digest.digest()));
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取文件的MD5值
     * @param path 文件的路径
     * @return 文件的MD5值
     */
    public static String getFileMD5(String path){
        try {
            FileInputStream fis=new FileInputStream(path);
            MessageDigest md=MessageDigest.getInstance("MD5");
            byte[] buffer=new byte[1024];
            int length;
            while ((length=fis.read(buffer,0,1024))!=-1){
                md.update(buffer,0,length);
            }
            BigInteger bigInteger=new BigInteger(1,md.digest());
            return bigInteger.toString(16).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static char[] toHex(byte[] data){
        char[] toDigits=DIGITS_UPPER;
        int l=data.length;
        char[] out=new char[l<<1];
        //two characters from the hex value
        for(int i=0,j=0;i<l;i++){
            out[j++]=toDigits[(0xF0&data[i])>>>4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }
}
