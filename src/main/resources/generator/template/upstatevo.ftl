package ${basePackageValue}.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "更新可用状态vo")
public class UpStateVo {

    @ApiModelProperty(example = "id11", position = 1)
    private String id;

    @ApiModelProperty(example = "本系统可用状态：1表示可用，0表示停用", position = 7)
    private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}