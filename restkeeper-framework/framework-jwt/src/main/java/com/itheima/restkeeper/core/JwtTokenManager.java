package com.itheima.restkeeper.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.itheima.restkeeper.properties.JwtProperties;
import com.itheima.restkeeper.utils.EmptyUtil;
import com.itheima.restkeeper.utils.EncodesUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description：自定义jwtkoken管理者
 */
@Configuration
@EnableConfigurationProperties({JwtProperties.class})
@Slf4j
public class JwtTokenManager {

    @Autowired
    JwtProperties jwtProperties;

    /**
     * @Description 签发令牌
     * @param iss 签发者
     * @param ttlMillis 过期时间
     * @param claims jwt存储的一些非隐私信息
     * @return
     */
    public String issuedToken(String iss, long ttlMillis, String jti, Map<String,Object> claims){
        if (EmptyUtil.isNullOrEmpty(claims)){
            claims = new HashMap<>();
        }
        //获取当前时间
        long nowMillis = System.currentTimeMillis();
        //获取加密签名
        String base64EncodedSecretKey = EncodesUtil.encodeBase64(jwtProperties.getBase64EncodedSecretKey().getBytes());
        //构建令牌
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)//构建非隐私信息
                .setId(jti)//构建唯一标识
                .setIssuedAt(new Date(nowMillis))//构建签发时间
                .setSubject(iss)//签发者
                .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey);//指定算法和秘钥
        if (ttlMillis>0){
            long expMillis = nowMillis+ttlMillis*1000;
            Date expData = new Date(expMillis);
            builder.setExpiration(expData);//指定过期时间
        }
        return builder.compact();
    }

    /**
     * @Description 解析令牌
     * @param jwtToken 令牌字符串
     * @return
     */
    public Claims decodeToken(String jwtToken){
        //获取加密签名
        String base64EncodedSecretKey = EncodesUtil.encodeBase64(jwtProperties.getBase64EncodedSecretKey().getBytes());
        //带着密码去解析字符串
        return Jwts.parser()
                .setSigningKey(base64EncodedSecretKey)
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    /**
     * @description 从Token中获取CurrentUser
     * @param jwtToken
     * @return: java.lang.String
     */
    public Object getCurrentUser(String jwtToken){
        //获取加密签名
        String base64EncodedSecretKey = EncodesUtil.encodeBase64(jwtProperties.getBase64EncodedSecretKey().getBytes());
        Claims claims = this.decodeToken(jwtToken);
        return claims.get("currentUser");
    }

    /**
     * @Description 校验令牌:1、头部信息和荷载信息是否被篡改 2、校验令牌是否过期
     * @param  jwtToken 令牌字符串
     * @return
     */
    public boolean isVerifyToken(String jwtToken){
        //带着签名构建校验对象
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getBase64EncodedSecretKey().getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        //校验:如果校验1、头部信息和荷载信息是否被篡改 2、校验令牌是否过期 不通过则会抛出异常
        try {
            jwtVerifier.verify(jwtToken);
        }catch (Exception e){
            log.warn("令牌校验失败");
            return false;
        }

        return true;
    }

    public JwtProperties getJwtProperties() {
        return jwtProperties;
    }
}
