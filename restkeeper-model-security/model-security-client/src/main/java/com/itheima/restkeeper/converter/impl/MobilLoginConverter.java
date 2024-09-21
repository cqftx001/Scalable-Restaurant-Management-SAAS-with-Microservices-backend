package com.itheima.restkeeper.converter.impl;

import com.itheima.restkeeper.constant.SecurityCacheConstant;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.converter.LoginConverter;
import com.itheima.restkeeper.req.EnterpriseVo;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName SystemMobilLoginConverter.java
 * @Description 后台手机登录
 */
@Component("mobilLogin")
public class MobilLoginConverter implements LoginConverter {

    //手机
    private String mobileParameter = "mobile";

    //验证码
    private String authCodeParameter = "authCode";

    @Autowired
    RedissonClient redissonClient;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange,
                                        String loginType,
                                        String siteType) {
        String hostName = exchange.getRequest().getURI().getHost();
        String key = null;
        if (siteType.equals(SuperConstant.WEBSITE)){
            key = SecurityCacheConstant.WEBSITE+hostName;
        }else if (siteType.equals(SuperConstant.APP_WEBSITE)){
            key = SecurityCacheConstant.APP_WEBSITE+hostName;
        }else {
            return  Mono.error(new BadCredentialsException("站点类型未定义"));
        }
        //域名校验
        RBucket<EnterpriseVo> bucket = redissonClient.getBucket(key);
        EnterpriseVo enterpriseVo = bucket.get();
        if (EmptyUtil.isNullOrEmpty(enterpriseVo)){
            return  Mono.error(new BadCredentialsException("Invalid hostName"));
        }
        //获得enterpriseId
        String enterpriseId = String.valueOf(enterpriseVo.getEnterpriseId());
        return exchange.getFormData().map( data -> {
            String mobile = data.getFirst(this.mobileParameter);
            String authCode = data.getFirst(this.authCodeParameter);
            if (EmptyUtil.isNullOrEmpty(mobile)||
                    EmptyUtil.isNullOrEmpty(authCode)){
                throw  new BadCredentialsException("客户登陆异常");
            }
            String principal = mobile+":"+enterpriseId+":"+loginType+":"+siteType;
            return new UsernamePasswordAuthenticationToken(principal, authCode);
        });
    }
}
