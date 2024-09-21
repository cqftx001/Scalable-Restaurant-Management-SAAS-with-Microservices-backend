package com.itheima.restkeeper.basic;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * @ClassName AuthUser.java
 * @Description 自定认证用户
 */
public class UserAuth extends User {

    //主键
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    //数据源分片Id
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long shardingId;

    //商户号
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long enterpriseId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long storeId;

    //jwt加密字段
    private String jwtToken;

    //真实姓名
    private String realName;

    //性别
    private String sex;

    //电话
    private String mobil;

    //邮箱
    private String email;

    //折扣上线
    private BigDecimal discountLimit;

    //减免金额上线
    private BigDecimal reduceLimit;

    //职务
    private String duties;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")//get
    private Date createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")//get
    private Date updatedTime;

    public UserAuth(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id,
                    Long shardingId, Long enterpriseId, Long storeId, String jwtToken, String realName, String sex, String mobil,
                    String email, BigDecimal discountLimit, BigDecimal reduceLimit, String duties, Date createdTime,
                    Date updatedTime) {
        super(username, password, authorities);
        this.id = id;
        this.shardingId = shardingId;
        this.enterpriseId = enterpriseId;
        this.storeId = storeId;
        this.jwtToken = jwtToken;
        this.realName = realName;
        this.sex = sex;
        this.mobil = mobil;
        this.email = email;
        this.discountLimit = discountLimit;
        this.reduceLimit = reduceLimit;
        this.duties = duties;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShardingId() {
        return shardingId;
    }

    public void setShardingId(Long shardingId) {
        this.shardingId = shardingId;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobil() {
        return mobil;
    }

    public void setMobil(String mobil) {
        this.mobil = mobil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getDiscountLimit() {
        return discountLimit;
    }

    public void setDiscountLimit(BigDecimal discountLimit) {
        this.discountLimit = discountLimit;
    }

    public BigDecimal getReduceLimit() {
        return reduceLimit;
    }

    public void setReduceLimit(BigDecimal reduceLimit) {
        this.reduceLimit = reduceLimit;
    }

    public String getDuties() {
        return duties;
    }

    public void setDuties(String duties) {
        this.duties = duties;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

}
