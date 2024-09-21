package com.itheima.restkeeper.basic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itheima.restkeeper.validation.Create;
import com.itheima.restkeeper.validation.Delete;
import com.itheima.restkeeper.validation.Update;
import com.itheima.restkeeper.validation.UpdateEnableFlag;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName BasicVo.java
 * @Description 基础请求
 */
@Data
@NoArgsConstructor
public class BasicVo implements Serializable {

    @ApiModelProperty(value = "主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(value = "数据源分片Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long shardingId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")//get
    protected Date createdTime;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")//get
    protected Date updatedTime;

    @ApiModelProperty(value = "是否有效")
    @NotBlank(groups = {UpdateEnableFlag.class},message = "角色为空")
    protected String enableFlag;

    public BasicVo(Long id) {
        this.id = id;
    }
}
