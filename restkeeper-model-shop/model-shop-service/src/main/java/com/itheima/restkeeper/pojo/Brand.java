package com.itheima.restkeeper.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.restkeeper.basic.BasicPojo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description：品牌管理
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_brand")
@ApiModel(value="Brand对象", description="品牌管理")
public class Brand extends BasicPojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public Brand(Long id,String brandName,String category,Long enterpriseId){
        super(id);
        this.brandName=brandName;
        this.category=category;
        this.enterpriseId=enterpriseId;
    }

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "品牌分类")
    private String category;

    @ApiModelProperty(value = "商户号")
    private Long enterpriseId;


}
