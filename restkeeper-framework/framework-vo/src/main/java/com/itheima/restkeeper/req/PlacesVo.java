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

/**
 * @Description：地方表
 */
@Data
@NoArgsConstructor
@FieldNameConstants
public class PlacesVo extends BasicVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public PlacesVo(Long id, Long parentId, String cityName){
        super(id);
        this.parentId=parentId;
        this.cityName=cityName;
    }

    @ApiModelProperty(value = "父ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentId;

    @ApiModelProperty(value = "名称")
    private String cityName;


}
