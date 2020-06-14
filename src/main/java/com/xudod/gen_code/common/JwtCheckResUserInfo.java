package com.xudod.gen_code.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "token解析成功后，返回解析对象")
public class JwtCheckResUserInfo {
	
	
    public JwtCheckResUserInfo() {
		super();
	}

	public JwtCheckResUserInfo(String id, String mdmCode, String name) {
		super();
		this.id = id;
		this.mdmCode = mdmCode;
		this.name = name;
	}

	@ApiModelProperty(value = "主键",required = true)
    private String id;

    @ApiModelProperty(value = "主数据编码",required = true)
    private String mdmCode;

    @ApiModelProperty(value = "姓名",required = true)
    private String name;
    
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

    /**
     * 主数据编码
     * @return mdm_code 主数据编码
     */
    public String getMdmCode() {
        return mdmCode;
    }

    /**
     * 主数据编码
     * @param mdmCode 主数据编码
     */
    public void setMdmCode(String mdmCode) {
        this.mdmCode = mdmCode;
    }

    /**
     * 姓名
     * @return name 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 姓名
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }
    
}