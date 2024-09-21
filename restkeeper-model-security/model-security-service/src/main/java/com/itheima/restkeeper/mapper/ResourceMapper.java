package com.itheima.restkeeper.mapper;

import com.itheima.restkeeper.pojo.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.restkeeper.pojo.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @Description：资源表Mapper接口
 */
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * @Description 查询资源对应的角色
     */
    @Select({"SELECT r.id,",
            "r.role_name,",
            "r.label,",
            "r.description," ,
            "r.sort_no," ,
            "r.enable_flag," ,
            "r.created_time," ,
            "r.updated_time," ,
            "r.sharding_id " ,
            "FROM tab_role_resource rr ",
            "LEFT JOIN tab_role r ON rr.role_id = r.id " ,
            "WHERE rr.resource_id = #{resourceId} " ,
            "AND rr.enable_flag = #{enableFlag} " ,
            "AND r.enable_flag = #{enableFlag}"})
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
    public List<Role> findRoleByResourceId(@Param("enableFlag") String enableFlag,
                                           @Param("resourceId") Long resourceId);

    /***
     * @description 用户对应资源
     * @param enableFlag 是否有效
     * @param userId 用户Id
     * @return: java.util.List<com.itheima.restkeeper.pojo.Resource>
     */
    @Select({"SELECT r.id," ,
            "r.parent_id," ,
            "r.resource_name," ,
            "r.request_path," ,
            "r.label," ,
            "r.icon," ,
            "r.is_leaf," ,
            "r.resource_type," ,
            "r.sort_no," ,
            "r.description," ,
            "r.system_code," ,
            "r.is_system_root," ,
            "r.enable_flag," ,
            "r.created_time," ,
            "r.updated_time," ,
            "r.sharding_id " ,
            "FROM tab_user_role ur " ,
            "LEFT JOIN tab_role_resource rr ON ur.role_id = rr.role_id " ,
            "LEFT JOIN tab_resource r ON r.id = rr.resource_id " ,
            "WHERE ur.enable_flag =#{enableFlag} " ,
            "AND rr.enable_flag =#{enableFlag} " ,
            "AND r.enable_flag =#{enableFlag} " ,
            "AND ur.user_id = #{userId}"})
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="parent_id", property="parentId", jdbcType=JdbcType.BIGINT),
            @Result(column="resource_name", property="resourceName", jdbcType=JdbcType.VARCHAR),
            @Result(column="request_path", property="requestPath", jdbcType=JdbcType.VARCHAR),
            @Result(column="label", property="label", jdbcType=JdbcType.VARCHAR),
            @Result(column="icon", property="icon", jdbcType=JdbcType.VARBINARY),
            @Result(column="is_leaf", property="isLeaf", jdbcType=JdbcType.VARBINARY),
            @Result(column="resource_type", property="resourceType", jdbcType=JdbcType.VARBINARY),
            @Result(column="sort_no", property="sortNo", jdbcType=JdbcType.INTEGER),
            @Result(column="description", property="description", jdbcType=JdbcType.VARBINARY),
            @Result(column="resource_type", property="resourceType", jdbcType=JdbcType.VARBINARY),
            @Result(column="is_system_root", property="isSystemRoot", jdbcType=JdbcType.VARBINARY),
            @Result(column="enable_flag", property="enableFlag", jdbcType=JdbcType.VARBINARY),
            @Result(column="created_time", property="createdTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="updated_time", property="updatedTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="sharding_id", property="shardingId", jdbcType=JdbcType.BIGINT)
    })
    List<Resource> findResourceByUserId(@Param("enableFlag") String enableFlag,
                                        @Param("userId") Long userId);

    @Select({"SELECT r.id," ,
            "r.parent_id," ,
            "r.resource_name," ,
            "r.request_path," ,
            "r.label," ,
            "r.icon," ,
            "r.is_leaf," ,
            "r.resource_type," ,
            "r.sort_no," ,
            "r.description," ,
            "r.system_code," ,
            "r.is_system_root," ,
            "r.enable_flag," ,
            "r.created_time," ,
            "r.updated_time," ,
            "r.sharding_id " ,
            "FROM tab_user_role ur " ,
            "LEFT JOIN tab_role_resource rr ON ur.role_id = rr.role_id " ,
            "LEFT JOIN tab_resource r ON r.id = rr.resource_id " ,
            "WHERE ur.enable_flag =#{enableFlag} " ,
            "AND rr.enable_flag =#{enableFlag} " ,
            "AND r.enable_flag =#{enableFlag} " ,
            "AND ur.user_id = #{customerId}"})
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="parent_id", property="parentId", jdbcType=JdbcType.BIGINT),
            @Result(column="resource_name", property="resourceName", jdbcType=JdbcType.VARCHAR),
            @Result(column="request_path", property="requestPath", jdbcType=JdbcType.VARCHAR),
            @Result(column="label", property="label", jdbcType=JdbcType.VARCHAR),
            @Result(column="icon", property="icon", jdbcType=JdbcType.VARBINARY),
            @Result(column="is_leaf", property="isLeaf", jdbcType=JdbcType.VARBINARY),
            @Result(column="resource_type", property="resourceType", jdbcType=JdbcType.VARBINARY),
            @Result(column="sort_no", property="sortNo", jdbcType=JdbcType.INTEGER),
            @Result(column="description", property="description", jdbcType=JdbcType.VARBINARY),
            @Result(column="resource_type", property="resourceType", jdbcType=JdbcType.VARBINARY),
            @Result(column="is_system_root", property="isSystemRoot", jdbcType=JdbcType.VARBINARY),
            @Result(column="enable_flag", property="enableFlag", jdbcType=JdbcType.VARBINARY),
            @Result(column="created_time", property="createdTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="updated_time", property="updatedTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="sharding_id", property="shardingId", jdbcType=JdbcType.BIGINT)
    })
    List<Resource> findResourceByCustomerId(@Param("enableFlag") String enableFlag,
                                            @Param("customerId") Long customerId);
}
