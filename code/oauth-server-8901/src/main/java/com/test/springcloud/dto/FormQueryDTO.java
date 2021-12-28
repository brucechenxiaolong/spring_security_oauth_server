package com.test.springcloud.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FormQueryDTO {

    @ApiModelProperty("单据状态：0=审核中，1=查档中，2=未归还，3=已办结，不传查全部")
    private Integer state;

    @ApiModelProperty("办理状态：1=办理中；2=已办理")
    private String use_state;

    @ApiModelProperty("查档人姓名")
    private String usePeopleName;

    @ApiModelProperty("查档人账号")
    private String loginName;

}
