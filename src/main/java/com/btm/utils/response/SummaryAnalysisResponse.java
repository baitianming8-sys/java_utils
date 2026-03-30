package com.btm.utils.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 汇总分析通用 响应类
 * @author bai
 * @date 2024/9/18 9:30

 */

@Data
@ApiModel(value="SummaryAnalysisResponse对象", description="")
public class SummaryAnalysisResponse {
    @ApiModelProperty("总数")
    private Integer total;

    @ApiModelProperty("总数（可以加单位）")
    private String totalAndUnit;

    @ApiModelProperty("类型及百分比")
    private List<TypePercent> typePercentList;
}
