package com.iniestar.mark1.utils;

import com.iniestar.mark1.db.entity.ApiInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Slf4j
public final class TokenUtils {

    public static String generateJwtToken(String uri, String ip, String secretRandomString) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(uri)
                .setHeader(createHeader())
                .setClaims(createClaims(uri, ip))
                .setExpiration(createExpireDateForOneYear())
                .signWith(createSigningKey(secretRandomString));
        return builder.compact();
    }

    public static boolean isValidToken(String authToken, String secretKey) {
        try {
            Claims claims = getClaimsFormToken(authToken, secretKey);
            log.info("expireTime :" + claims.getExpiration());
            log.info("uri :" + claims.get("uri"));
            log.info("ip :" + claims.get("clientIp"));
            return true;
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            return false;
        } catch (JwtException exception) {
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.error("Token is null");
            return false;
        }
    }

    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    private static Date createExpireDateForOneYear() { // 토큰 만료시간은 1일으로 설정
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    private static Map<String, Object> createClaims(String uri, String ip) { // 공개 클레임에 사용자의 이름과 이메일을 설정하여 정보를 조회할 수 있다.
        Map<String, Object> claims = new HashMap<>();
        claims.put("uri", uri);
        claims.put("clientIp", ip);
        return claims;
    }


    private static SecretKey createSigningKey(String secretRandomString) {
        /**
         * @Deprecated with signWith()
         * byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
         * return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
         */
        return Keys.hmacShaKeyFor(secretRandomString.getBytes(StandardCharsets.UTF_8));
    }

    private static Claims getClaimsFormToken(String authToken, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken).getBody();
    }

}

