package com.itheima.restkeeper.basic;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description：实体基础类
 */
@Data
@NoArgsConstructor
public class BasicPojo implements Serializable {

    //主键
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public Long id;

    //分片键
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public Long shardingId;

    //创建时间
    @TableField(fill = FieldFill.INSERT)//INSERT代表只在插入时填充
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//set
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")//get
    public Date createdTime;

    //修改时间
    @TableField(fill = FieldFill.INSERT_UPDATE)// INSERT_UPDATE 首次插入、其次更新时填充(或修改)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//set
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")//get
    public Date updatedTime;

    //是否有效
    @TableField(fill = FieldFill.INSERT)
    public String enableFlag;

    //构造函数
    public BasicPojo(Long id) {
        this.id = id;
    }
}
