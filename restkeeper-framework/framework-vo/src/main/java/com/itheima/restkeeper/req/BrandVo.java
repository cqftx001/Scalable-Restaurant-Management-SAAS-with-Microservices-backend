package com.itheima.restkeeper.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itheima.restkeeper.basic.BasicVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * @ClassName BrandVo.java
 * @Description 品牌
 */
@Data
@NoArgsConstructor
public class BrandVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public BrandVo(Long id,String brandName,String category,Long enterpriseId){
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long enterpriseId;

    @ApiModelProperty(value = "选中节点")
    private String[] checkedIds;

    @ApiModelProperty(value = "附件信息",dataType = "AffixVo")
    private AffixVo affixVo;

}
