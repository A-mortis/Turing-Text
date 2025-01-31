package com.itheima.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    private static final String SECRET_KEY = "aXRoZWltYQ=="; // 使用与测试类中一致的密钥
    private static final long EXPIRATION_TIME = 12 * 60 * 60 * 1000; // 过期时间设置为12小时

    /**
     * 根据提供的载荷生成一个带有过期时间的JWT令牌。
     *
     * @param payload 包含要编码到令牌中的数据的Map
     * @return 生成的JWT字符串
     */
    public static String generateToken(Map<String, Object> payload) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .addClaims(payload)
                .setExpiration(expiryDate)
                .compact();
    }

    /**
     * 解析给定的JWT令牌并返回其载荷。
     *
     * @param token 要解析的JWT字符串
     * @return 解析后的载荷（Claims对象）
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // 指定密钥
                .parseClaimsJws(token) // 解析令牌
                .getBody();
    }
}