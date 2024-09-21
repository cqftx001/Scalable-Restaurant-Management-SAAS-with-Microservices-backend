package com.itheima.restkeeper.details;

import com.itheima.restkeeper.CustomerAdapterFace;
import com.itheima.restkeeper.UserAdapterFace;
import com.itheima.restkeeper.basic.UserAuth;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.req.UserVo;
import com.itheima.restkeeper.utils.EmptyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashSet;

/**
 * @ClassName ReactiveUserDetailsServiceImpl.java
 * @Description 支持flum的身份类实现ReactiveUserDetailsService接口
 */
@Component("reactiveUserDetailsService")
@Slf4j
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService{

    //调用RPC原创服务
    @DubboReference(version = "${dubbo.application.version}", check = false)
    UserAdapterFace userAdapterFace;

    //调用RPC原创服务
    @DubboReference(version = "${dubbo.application.version}", check = false)
    CustomerAdapterFace customerAdapterFace;

    //验证身份
    @Override
    public Mono<UserDetails> findByUsername(String principal) {
        String[] principals = principal.split(":");
        if (principals.length!=4){
            log.warn("用户:{}登录信息不完整",principal);
            return Mono.empty();
        }
        String mobile =principals[0];
        String username =principals[0];
        Long enterpriseId =Long.valueOf(principals[1]);
        String loginType =principals[2];
        String siteType =principals[3];
        UserVo userVo = null;
        //user账户登录
        if (loginType.equals(SuperConstant.USERNAME_LOGIN)
            &&siteType.equals(SuperConstant.WEBSITE)){
            userVo = userAdapterFace.findUserByUsernameAndEnterpriseId(username, enterpriseId);
        }
        //user手机登录
        if (loginType.equals(SuperConstant.MOBIL_LOGIN)
            &&siteType.equals(SuperConstant.WEBSITE)){
            userVo = userAdapterFace.findUserByMobilAndEnterpriseId(mobile, enterpriseId);
        }
        //customer账户登录
        if (loginType.equals(SuperConstant.USERNAME_LOGIN)
            &&siteType.equals(SuperConstant.APP_WEBSITE)){
            userVo = customerAdapterFace.findCustomerByUsernameAndEnterpriseId(username, enterpriseId);
        }
        //customer手机登录
        if (loginType.equals(SuperConstant.MOBIL_LOGIN)
            &&siteType.equals(SuperConstant.APP_WEBSITE)){
            userVo = customerAdapterFace.findCustomerByMobilAndEnterpriseId(mobile, enterpriseId);
        }
        if (EmptyUtil.isNullOrEmpty(userVo)){
            log.warn("用户:{}不存在",principal);
            return Mono.empty();
        }
        UserAuth userAuth = new UserAuth(
            userVo.getUsername(),
            userVo.getPassword(),
            new HashSet<>(),
            userVo.getId(),
            userVo.getShardingId(),
            userVo.getEnterpriseId(),
            userVo.getStoreId(),
            userVo.getJwtToken(),
            userVo.getRealName(),
            userVo.getSex(),
            userVo.getMobil(),
            userVo.getEmail(),
            userVo.getDiscountLimit(),
            userVo.getReduceLimit(),
            userVo.getDuties(),
            userVo.getCreatedTime(),
            userVo.getUpdatedTime()
        );
        return Mono.just(userAuth);
    }



}
