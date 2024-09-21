package com.itheima.restkeeper.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName Proof.java
 * @Description 证明文件对象
 */
@Data
@NoArgsConstructor
@ApiModel(value="ProofVo对象", description="证明文件对象")
public class ProofVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Builder
    public ProofVo(String proofImage, String proofType) {
        this.proofImage = proofImage;
        this.proofType = proofType;
    }

    @ApiModelProperty(value = "签名对应的资质证明图片需先进行 base64编码格式转换")
    private String proofImage;

    @ApiModelProperty(value = "签名证明文件类型")
    private String proofType;

}
