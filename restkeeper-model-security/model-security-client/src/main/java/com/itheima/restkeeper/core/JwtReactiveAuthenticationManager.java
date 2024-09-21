package com.itheima.restkeeper.core;

import com.itheima.restkeeper.constant.SmsCacheConstant;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.enums.AuthEnum;
import com.itheima.restkeeper.exception.AuthCodeException;
import com.itheima.restkeeper.exception.UsernamePasswordException;
import com.itheima.restkeeper.utils.EmptyUtil;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * @ClassName JwtUserDetailsRepositoryReactiveAuthenticationManager.java
 * @Description 认证管理器
 */
@Component
public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    //密码编辑者
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories
            .createDelegatingPasswordEncoder();

    //调度程序
    private Scheduler scheduler = Schedulers.parallel();

    //用户明细信息服务
    @Autowired
    private ReactiveUserDetailsService reactiveUserDetailsService;

    @Autowired
    RedissonClient redissonClient;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        final String principal = authentication.getName();
        final String password = (String) authentication.getCredentials();
        String[] principals = principal.split(":");
        //mobile+":"+enterpriseId+":"+loginType+":"+siteType
        String mobile =principals[0];
        String enterpriseId =principals[1];
        String loginType =principals[2];
        String siteType =principals[3];
        Mono<UserDetails> userDetailsMono = this.reactiveUserDetailsService.findByUsername(principal);
        //密码校验
        if (loginType.equals(SuperConstant.USERNAME_LOGIN)){
            return userDetailsMono.publishOn(this.scheduler)
                //密码比较
                .filter(u -> this.passwordEncoder.matches(password, u.getPassword()))
                //失败处理
                .switchIfEmpty(Mono.defer(()->
                    Mono.error(new UsernamePasswordException("用户名或密码错误"))))
                //成功处理
                .map(u ->
                    new UsernamePasswordAuthenticationToken(u, u.getPassword(), u.getAuthorities()));
        }
        //短信校验
        if (loginType.equals(SuperConstant.MOBIL_LOGIN)){
            //redis中获得验证码
            String key = SmsCacheConstant.LOGIN_CODE+principals[0];
            RBucket<String> bucket = redissonClient.getBucket(key);
            String authCode = bucket.get();
            if (EmptyUtil.isNullOrEmpty(authCode)){
                Mono.error(new AuthCodeException(AuthEnum.AUTH_CODE_ISNULL));
            }
            return userDetailsMono.publishOn(this.scheduler)
                //密码比较
                .filter(u -> authCode.equals(password))
                //失败处理
                .switchIfEmpty(Mono.defer(()->
                    Mono.error(new AuthCodeException(AuthEnum.AUTH_CODE_ERROR))))
                //成功处理
                .map(u ->
                    new UsernamePasswordAuthenticationToken(u, u.getPassword(), u.getAuthorities()));
        }
        throw new BadCredentialsException("Invalid Credentials");
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
        this.passwordEncoder = passwordEncoder;
    }

}
