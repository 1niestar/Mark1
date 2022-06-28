package com.iniestar.mark1.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.sql.SQLOutput;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ApiToken {

    final String secretKey = "secretKey-test-authorization-jwt-manage-token";

    public String createToken() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("data", "test");

        Long expiredTime = 1000 * 60L * 60L * 2L; // 2 Hours

        Date ext = new Date();
        String jwt = null;
        try {
            ext.setTime(ext.getTime() + expiredTime);
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes("UTF-8"));
            jwt = Jwts.builder()
                    .setHeader(headers)
                    .setClaims(payloads)
                    .setSubject("user")
                    .setExpiration(ext)
                    .signWith(key)
                    .compact();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jwt;
    }

    public boolean verifyJWT(String jwt) {

        Map<String, Object> claimMap = null;

        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes("UTF-8"));
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt)
                    .getBody();

            claimMap = claims;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public static void main(String[] args) {
        ApiToken apiToken = new ApiToken();
        String jwt = apiToken.createToken();
        System.out.println("jwt value : " + jwt);

        boolean bRet = apiToken.verifyJWT(jwt);
        System.out.println("verify : " + bRet);
    }
}
