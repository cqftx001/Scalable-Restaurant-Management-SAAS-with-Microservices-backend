package com.itheima.restkeeper.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.restkeeper.basic.BasicPojo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description：附件
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_affix")
@ApiModel(value="Affix对象", description="附件")
public class Affix extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public Affix(Long id,Long businessId,String businessType,String suffix,String fileName,String pathUrl){
        super(id);
        this.businessId=businessId;
        this.businessType=businessType;
        this.suffix=suffix;
        this.fileName=fileName;
        this.pathUrl=pathUrl;
    }

    @ApiModelProperty(value = "业务ID")
    private Long businessId;

    @ApiModelProperty(value = "业务类型")
    private String businessType;

    @ApiModelProperty(value = "后缀名")
    private String suffix;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "访问路径")
    private String pathUrl;


}
