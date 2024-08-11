package com.shiliuzi.personnel_management.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

/**
 * @description:
 * @author: chun
 **/
public class JwtUtil {
    private static final String SIGN_KEY = "chun";
    //一小时
    private static final Long EXPIRE = 1000 * 60 * 60 * 60L;

    //生成JWT令牌
    public static String genToken(Map<String, Object> claims)
    {
     /*   return Jwts.builder()
                //用户与用户名的声明
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SIGN_KEY)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .compact();*/


        return JWT.create()
                .withClaim("claims",claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE ))
                .sign(Algorithm.HMAC256(SIGN_KEY));
    }

    //解析JWT令牌
    public static Map<String, Object> parseToken(String token)
    {
       /* return Jwts.parser()
                .setSigningKey(SIGN_KEY)
                .parseClaimsJws(jwt)
                .getBody();*/

        return JWT.require(Algorithm.HMAC256(SIGN_KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }

    // 判断JWT是否过期
    /*public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
*/
}