package com.m.rabbit.utils;

/*
 * Created on 2005-6-11
 * Original Author: airland.congs
 * Campany: Disport Support Group (AT) JoyGogo
 * Copyright 2005, by JoyGogo Beijing Research Branch.
 * 
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * 
 * @author airland.congs
 * @version $Revision: 1.1 $
 * 
 */
public class MD5Utils {
    private static char[] DigitLower = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private static char[] DigitUpper = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    /**
     * 
     */
    public MD5Utils() {
    }

    /**
     * 
     * @param srcStr
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NullPointerException
     */
    public static String getMD5Lower(String srcStr) {
        String sign = "lower";
        try {
            return processStr(srcStr, sign);
        } catch (NoSuchAlgorithmException e) {
        } catch (NullPointerException e) {
        }
        return "";
    }

    /**
     * 
     * @param srcStr
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NullPointerException
     */
    public static String getMD5Upper(String srcStr) {
        String sign = "upper";
        try {
            return processStr(srcStr, sign);
        } catch (NoSuchAlgorithmException e) {
        } catch (NullPointerException e) {
        }
        return "";
    }

    private static String processStr(String srcStr, String sign) throws NoSuchAlgorithmException, NullPointerException {
        MessageDigest digest;
        String algorithm = "MD5";
        String result = "";
        digest = MessageDigest.getInstance(algorithm);
        digest.update(srcStr.getBytes());
        byte[] byteRes = digest.digest();

        int length = byteRes.length;

        for (int i = 0; i < length; i++) {
            result = result + byteHEX(byteRes[i], sign);
        }

        return result;
    }

    /**
     * 
     * @param bt
     * @return
     */
    private static String byteHEX(byte bt, String sign) {

        char[] temp = null;
        if (sign.equalsIgnoreCase("lower")) {
            temp = DigitLower;
        } else if (sign.equalsIgnoreCase("upper")) {
            temp = DigitUpper;
        } else {
            throw new java.lang.RuntimeException("");
        }
        char[] ob = new char[2];

        ob[0] = temp[(bt >>> 4) & 0X0F];

        ob[1] = temp[bt & 0X0F];

        return new String(ob);
    }
    public static String crypt(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes("GBK"));
            byte[] hash = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (Exception e) {
            return "";
        }
        return hexString.toString();
    }

}