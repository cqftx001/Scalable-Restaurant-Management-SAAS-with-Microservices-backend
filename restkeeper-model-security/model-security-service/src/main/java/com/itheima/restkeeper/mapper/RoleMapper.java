package com.itheima.restkeeper.mapper;

import com.itheima.restkeeper.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

/**
 * @Description：用户角色表Mapper接口
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select({"SELECT r.id,",
            "r.role_name,",
            "r.label,",
            "r.description," ,
            "r.sort_no," ,
            "r.enable_flag," ,
            "r.created_time," ,
            "r.updated_time," ,
            "r.sharding_id " ,
            "FROM tab_user_role ur  " ,
            "LEFT JOIN tab_role r ON ur.role_id = r.id  " ,
            "WHERE ur.enable_flag =#{enableFlag}  " ,
            "AND r.enable_flag = #{enableFlag}  " ,
            "AND ur.user_id=#{userId}"
            })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
            @Result(column="label", property="label", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
            @Result(column="sort_no", property="sortNo", jdbcType=JdbcType.INTEGER),
            @Result(column="enable_flag", property="enableFlag", jdbcType=JdbcType.VARBINARY),
            @Result(column="created_time", property="createdTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="updated_time", property="updatedTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="sharding_id", property="shardingId", jdbcType=JdbcType.BIGINT)
    })
    List<Role> findRoleByUserId(@Param("userId") Long userId, @Param("enableFlag") String enableFlag);

    @Select({"SELECT r.id,",
            "r.role_name,",
            "r.label,",
            "r.description," ,
            "r.sort_no," ,
            "r.enable_flag," ,
            "r.created_time," ,
            "r.updated_time," ,
            "r.sharding_id " ,
            "FROM tab_user_role ur  " ,
            "LEFT JOIN tab_role r ON ur.role_id = r.id  " ,
            "WHERE ur.enable_flag =#{enableFlag}  " ,
            "AND r.enable_flag = #{enableFlag}  " ,
            "AND ur.user_id=#{customerId}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
            @Result(column="label", property="label", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
            @Result(column="sort_no", property="sortNo", jdbcType=JdbcType.INTEGER),
            @Result(column="enable_flag", property="enableFlag", jdbcType=JdbcType.VARBINARY),
            @Result(column="created_time", property="createdTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="updated_time", property="updatedTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="sharding_id", property="shardingId", jdbcType=JdbcType.BIGINT)
    })
    List<Role> findRoleByCustomerId(@Param("customerId")Long customerId, @Param("enableFlag")String enableFlag);
}
