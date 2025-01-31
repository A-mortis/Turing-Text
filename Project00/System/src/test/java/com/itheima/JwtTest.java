package com.itheima;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGenerateJwt(){
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id",1);
        dataMap.put("username","admin");

        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256,"aXRoZWltYQ==")
                .addClaims(dataMap)
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .compact();
        System.out.println(jwt);
    }

    @Test
    //解析
    public void testParseJwt(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTczNzQzODQzMH0.gMJ7DkW4KphrM4VyorKRNPm0hH9hcnivhD7f68_6lYg";
        Claims claims =Jwts.parser().setSigningKey( "aXRoZWltYQ==")//指定密钥
                .parseClaimsJws(token)//解析令牌
                .getBody();
        System.out.println(claims);
    }

}
