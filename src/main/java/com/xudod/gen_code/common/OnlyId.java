package com.xudod.gen_code.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "只有id属性的实体，用于根据id删除或查询等操作")
public class OnlyId {
	
    @ApiModelProperty(value = "主键",required = true)
    private String id;
    
    public OnlyId() {
	}

	public OnlyId(String id) {
		this.id = id;
	}

	/**
     * 主键
     * @return id 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }
    
}