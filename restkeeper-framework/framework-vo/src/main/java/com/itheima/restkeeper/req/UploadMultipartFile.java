package com.itheima.restkeeper.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadMultipartFile implements Serializable {

    @ApiModelProperty(value = "文件名称")
    public String originalFilename;

    @ApiModelProperty(value = "文件数组")
    public byte[] fileByte;
}
