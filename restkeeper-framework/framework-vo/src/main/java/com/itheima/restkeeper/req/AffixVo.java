package com.itheima.restkeeper.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itheima.restkeeper.basic.BasicVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * @Description：附件表
 */
@Data
@NoArgsConstructor
@FieldNameConstants
public class AffixVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    public AffixVo(Long id, Long businessId) {
        super(id);
        this.businessId = businessId;
    }

    @Builder
    public AffixVo(Long id,Long businessId,String businessType,
                   String suffix,String fileName,String pathUrl){
        super(id);
        this.businessId=businessId;
        this.businessType=businessType;
        this.suffix=suffix;
        this.fileName=fileName;
        this.pathUrl=pathUrl;
        this.url=pathUrl;
    }

    @ApiModelProperty(value = "业务ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long businessId;

    @ApiModelProperty(value = "业务类型")
    private String businessType;

    @ApiModelProperty(value = "后缀名")
    private String suffix;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "访问路径")
    private String pathUrl;

    @ApiModelProperty(value = "访问路径")
    private String url;

    @ApiModelProperty(value = "选中节点")
    private String[] checkedIds;

    @ApiModelProperty(value = "base64图片")
    private String base64Image;

}
