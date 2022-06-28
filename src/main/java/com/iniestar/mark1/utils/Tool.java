package com.iniestar.mark1.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class Tool {
    private static SecureRandom secureRandom;
    static{
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static int randomInt(int n){
        if(secureRandom == null){
            throw new RuntimeException("SecureRandom not instantiated!");
        }
        return secureRandom.nextInt(n)+1;
    }
    public static byte[] randomBytes(int length){
        if (length<0){
            throw new IllegalArgumentException("len must > 0");
        }
        byte[] buffer = new byte[length];

        if(secureRandom == null){
            throw new RuntimeException("SecureRandom not instantiated!");
        }
        secureRandom.nextBytes(buffer);
        return buffer;
    }

    public static String randomByteString(int byteLength){
        if(byteLength < 0) throw new IllegalArgumentException("len must > 0");

        return toHex(randomBytes(byteLength/2)) ;
    }

    public static long  genRandomNum(){
        long rc = 0l;
        int i = randomInt(1000000-1);  //
        String tmp = String.format("%d%06d", System.currentTimeMillis(), i);
        rc = Long.parseLong(tmp);
        return rc;
    }

    public static String toHex(byte[] data) {
        if (data==null) return "";
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; ++i){
            int halfbyte = data[i] >>> 4 & 0xF;
            int two_halfs = 0;
            do {
                if ((halfbyte >= 0) && (halfbyte <= 9))
                    buf.append((char)(48 + halfbyte));
                else
                    buf.append((char)(97 + halfbyte - 10));
                halfbyte = data[i] & 0xF;
            }while (two_halfs++ < 1);
        }
        return buf.toString().toUpperCase();
    }

    public static String toHex(byte[] data, int offset, int length) {
        StringBuffer buf = new StringBuffer();
        for (int i = offset; i < offset+length; ++i){
            int halfbyte = data[i] >>> 4 & 0xF;
            int two_halfs = 0;
            do {
                if ((halfbyte >= 0) && (halfbyte <= 9))
                    buf.append((char)(48 + halfbyte));
                else
                    buf.append((char)(97 + halfbyte - 10));
                halfbyte = data[i] & 0xF;
            }while (two_halfs++ < 1);
        }
        return buf.toString().toUpperCase();
    }

    public static Timestamp getTimestamp(){
        return new Timestamp(new Date().getTime());
    }
    public static Timestamp getTimestampForDays(long days){
        return new Timestamp(new Date(days).getTime());
    }


    /**
     * null 이거나, 공백여부 확인
     * @param a
     * @return true : null 이거나, 공백인 경우
     */
    public static boolean isNullOrEmpty(String a){
        return (a==null || a.trim().length()==0) ? true : false;
    }

    public static Date getDateForDays(int days) { // 토큰 만료시간은 1일으로 설정
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static String toJson(Object o){
        if(o == null) return null;

        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
