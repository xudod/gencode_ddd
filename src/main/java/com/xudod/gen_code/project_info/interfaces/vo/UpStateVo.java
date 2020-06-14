package com.xudod.gen_code.project_info.interfaces.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "更新可用状态vo")
public class UpStateVo {

    @ApiModelProperty(example = "id11", position = 1)
    private String id;

    @ApiModelProperty(example = "本系统可用状态：1表示可用，0表示停用", position = 7)
    private String useStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}
    
}
