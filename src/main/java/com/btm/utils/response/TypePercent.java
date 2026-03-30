package com.btm.utils.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 类型及百分比
 * @author bai
 * @since 2024/09/18 09:32
 */
@Data
@Accessors(chain = true)
@ApiModel(value="TypePercent对象", description="")
public class TypePercent {
    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "个数")
    private String count;

    @ApiModelProperty(value = "百分比")
    private String percent;

    @ApiModelProperty(value = "排序")
    private Integer soft;


}
