package com.itheima.restkeeper.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 资源树显示类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeVo implements Serializable {

	@ApiModelProperty(value = "tree数据")
	private List<TreeItem> items;

	@ApiModelProperty(value = "选择节点")
	private List<String> checkedIds;

	@ApiModelProperty(value = "展开项")
	private List<String> expandedIds;

}
