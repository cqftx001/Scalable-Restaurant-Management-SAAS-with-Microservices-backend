package com.itheima.restkeeper.config;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.itheima.restkeeper.constant.SuperConstant;
import com.itheima.restkeeper.handler.MyBatisMetaObjectHandler;
import com.itheima.restkeeper.properties.TenantProperties;
import com.itheima.restkeeper.req.UserVo;
import com.itheima.restkeeper.utils.EmptyUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.apache.dubbo.rpc.RpcContext;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Description：配置文件
 */
@Slf4j
//申明此类为配置类
@Configuration
//扫描mapper接口类
@MapperScan("com.itheima.restkeeper.mapper")
//读取配置
@EnableConfigurationProperties({MybatisPlusProperties.class, TenantProperties.class})
public class MyBatisPlusConfig {

    @Autowired
    MybatisPlusProperties mybatisPlusProperties;

    @Autowired
    TenantProperties tenantProperties;

    /**
     * @Description mybatis提供的主键生成策略【制定雪花】
     */
    @Bean
    public IdentifierGenerator identifierGenerator() {
        return new DefaultIdentifierGenerator();
    }

    /**
     * 自动填充
     */
    @Bean
    public MyBatisMetaObjectHandler myMetaObjectHandler() {
        return new MyBatisMetaObjectHandler();
    }

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,
     * 需要设置 MybatisConfiguration#useDeprecatedExecutor = false
     * 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 如果用了分页插件注意
        // 先 add TenantLineInnerInterceptor
        // 再 add PaginationInnerInterceptor
        // 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
        //企业号租户字段插件
        interceptor.addInnerInterceptor(tenantEnterpriseInterceptor());
        //门店租户字段插件
        interceptor.addInnerInterceptor(tenantStoreInterceptor());
        //分页的插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        return interceptor;
    }

    @Bean
    public TenantLineInnerInterceptor tenantEnterpriseInterceptor(){
        return new TenantLineInnerInterceptor(new TenantLineHandler() {

            @Override
            public Expression getTenantId() {
                //从当前的RPC上下文中获取用户信息
                String currentUser = RpcContext.getContext().getAttachment(SuperConstant.CURRENT_USER);
                if (EmptyUtil.isNullOrEmpty(currentUser)){
                    return null;
                }
                UserVo userVo = JSON.parseObject(currentUser, UserVo.class);
                return new StringValue(String.valueOf(userVo.getEnterpriseId()));
            }

            @Override
            public String getTenantIdColumn() {
                return SuperConstant.ENTERPRISE_ID;
            }

            @Override
            public boolean ignoreTable(String tableName) {
                // 是否需要需要过滤某一张表
                List<String> tableNameList = tenantProperties.getIgnoreEnterpriseTables();
                if (!EmptyUtil.isNullOrEmpty(tableNameList)){
                    if (tableNameList.contains(tableName)){
                        return true;
                    }
                }
                //拼接的sql多租户字段标示对应的值不能为空
                Expression tenantId = this.getTenantId();
                if (EmptyUtil.isNullOrEmpty(tenantId)){
                    log.info("企业隐式传参为空，忽略表：{}",tableName);
                    return true;
                }
                return false;
            }
        });
    }

    @Bean
    public TenantLineInnerInterceptor tenantStoreInterceptor(){
        return new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                String currentStore = RpcContext.getContext().getAttachment(SuperConstant.CURRENT_STORE);
                if (EmptyUtil.isNullOrEmpty(currentStore)){
                   return null;
                }
                return new StringValue(String.valueOf(currentStore));
            }

            @Override
            public String getTenantIdColumn() {
                return SuperConstant.STORE_ID;
            }

            /**
             * 是否忽略表拼装SQL
             * @param tableName 当前正在执行的SQL 表名
             * @return
             * true：忽略getTenantIdColumn对应的字段，不拼装SQL
             * false：不忽略getTenantIdColumn对应的字段，拼装SQL条件
             */
            @Override
            public boolean ignoreTable(String tableName) {
                // 是否需要需要过滤某一张表
                List<String> tableNameList = tenantProperties.getIgnoreStoreTables();
                if (!EmptyUtil.isNullOrEmpty(tableNameList)
                        &&tableNameList.contains(tableName)){
                    return true;
                }
                //拼接的sql多租户字段标示对应的值不能为空
                Expression tenantId = this.getTenantId();
                if (EmptyUtil.isNullOrEmpty(tenantId)){
                    log.info("门店隐式传参为空，忽略表：{}",tableName);
                    return true;
                }
                return false;
            }
        });
    }

    //分页的插件配置
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        return new PaginationInnerInterceptor(DbType.MYSQL);
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }

}
