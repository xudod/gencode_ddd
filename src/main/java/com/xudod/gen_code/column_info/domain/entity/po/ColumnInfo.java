package com.xudod.gen_code.column_info.domain.entity.po;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;


@ApiModel(value = "领域内表对应的字段信息")
public class ColumnInfo {
    @ApiModelProperty(example = "id", position = 0)
    private String id;
    
    @ApiModelProperty(example = "tableId", position = 1)
    private String tableId;

	@ApiModelProperty(example = "字段名", position = 2)
    private String name;

    @ApiModelProperty(example = "字段中文名，用于属性注解和页面展示", position = 3)
    private String nameCn;

    @ApiModelProperty(example = "字段英文名，国际化时可用", position = 4)
    private String nameEn;

    @ApiModelProperty(example = "字段类型", position = 5)
    private String columnType;

    @ApiModelProperty(example = "字段长度", position = 6)
    private Integer columnLen;

    @ApiModelProperty(example = "小数点长度", position = 7)
    private Integer decimalPoint;

    @ApiModelProperty(example = "是否为null", position = 8)
    private String nullIs;

    @ApiModelProperty(example = "是否为主键", position = 9)
    private String keyIs;

    @ApiModelProperty(example = "字段校验类型", position = 10)
    private String checkType;

    @ApiModelProperty(example = "字段校验值，多个用逗号隔开", position = 11)
    private String checkValue;

    @ApiModelProperty(example = "提示，用于页面提示或其他需要的地方比如swagger", position = 12)
    private String pageTitle;

    @ApiModelProperty(example = "新建功能是否显示1显示，0不显示", position = 13)
    private String newShow;

    @ApiModelProperty(example = "编辑功能是否显示1显示，0不显示", position = 14)
    private String editUse;

    @ApiModelProperty(example = "表格展示数据时是否显示1显示，0不显示", position = 15)
    private String tableShow;

    @ApiModelProperty(example = "是否是公共字段1是，0不是，createby之类的", position = 17)
    private String publicIs;

    @ApiModelProperty(example = "排序号", position = 18)
    private Integer sequence;

    @ApiModelProperty(example = "分排序号", position = 18)
    private String partSequence;
    
    @ApiModelProperty(example = "备注", position = 19)
    private String remark;

    @ApiModelProperty(example = "接口数据状态", hidden = true, position = 20)
    private String infStatus;

    @ApiModelProperty(example = "是否可用", position = 21)
    private String ableStatus;

    @ApiModelProperty(example = "乐观锁", hidden = true, position = 22)
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    private Integer version;

    @ApiModelProperty(example = "删除状态", hidden = true, position = 23)
    @TableLogic
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    private String deleted;

    @ApiModelProperty(example = "数据来源", hidden = true, position = 24)
    private String clientType;

    @ApiModelProperty(example = "创建者", hidden = true, position = 25)
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(example = "创建时间", hidden = true, position = 26)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(example = "修改者", hidden = true, position = 27)
    private String modifyBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(example = "修改时间", hidden = true, position = 28)
    @TableField(value = "modify_time", fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;

    /**
     * id
     * @return id id
     */
    public String getId() {
        return id;
    }

    /**
     * id
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

	public String getPartSequence() {
		return partSequence;
	}

	public void setPartSequence(String partSequence) {
		this.partSequence = partSequence;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	/**
     * id
     * @return name id
     */
    public String getName() {
        return name;
    }

    /**
     * id
     * @param name id
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * id
     * @return name_cn id
     */
    public String getNameCn() {
        return nameCn;
    }

    /**
     * id
     * @param nameCn id
     */
    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    /**
     * id
     * @return name_en id
     */
    public String getNameEn() {
        return nameEn;
    }

    /**
     * id
     * @param nameEn id
     */
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    /**
     * id
     * @return column_type id
     */
    public String getColumnType() {
        return columnType;
    }

    /**
     * id
     * @param columnType id
     */
    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    /**
     * id
     * @return column_len id
     */
    public Integer getColumnLen() {
        return columnLen;
    }

    /**
     * id
     * @param columnLen id
     */
    public void setColumnLen(Integer columnLen) {
        this.columnLen = columnLen;
    }

    /**
     * id
     * @return decimal_point id
     */
    public Integer getDecimalPoint() {
        return decimalPoint;
    }

    /**
     * id
     * @param decimalPoint id
     */
    public void setDecimalPoint(Integer decimalPoint) {
        this.decimalPoint = decimalPoint;
    }

    /**
     * id
     * @return null_is id
     */
    public String getNullIs() {
        return nullIs;
    }

    /**
     * id
     * @param nullIs id
     */
    public void setNullIs(String nullIs) {
        this.nullIs = nullIs;
    }

    /**
     * id
     * @return key_is id
     */
    public String getKeyIs() {
        return keyIs;
    }

    /**
     * id
     * @param keyIs id
     */
    public void setKeyIs(String keyIs) {
        this.keyIs = keyIs;
    }

    /**
     * id
     * @return check_type id
     */
    public String getCheckType() {
        return checkType;
    }

    /**
     * id
     * @param checkType id
     */
    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    /**
     * id
     * @return check_value id
     */
    public String getCheckValue() {
        return checkValue;
    }

    /**
     * id
     * @param checkValue id
     */
    public void setCheckValue(String checkValue) {
        this.checkValue = checkValue;
    }

    /**
     * id
     * @return page_title id
     */
    public String getPageTitle() {
        return pageTitle;
    }

    /**
     * id
     * @param pageTitle id
     */
    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    /**
     * id
     * @return new_show id
     */
    public String getNewShow() {
        return newShow;
    }

    /**
     * id
     * @param newShow id
     */
    public void setNewShow(String newShow) {
        this.newShow = newShow;
    }

    /**
     * id
     * @return edit_use id
     */
    public String getEditUse() {
        return editUse;
    }

    /**
     * id
     * @param editUse id
     */
    public void setEditUse(String editUse) {
        this.editUse = editUse;
    }

    /**
     * id
     * @return table_use id
     */
    public String getTableShow() {
        return tableShow;
    }

    /**
     * id
     * @param tableShow id
     */
    public void setTableShow(String tableShow) {
        this.tableShow = tableShow;
    }

    /**
     * id
     * @return public_is id
     */
    public String getPublicIs() {
        return publicIs;
    }

    /**
     * id
     * @param publicIs id
     */
    public void setPublicIs(String publicIs) {
        this.publicIs = publicIs;
    }

    /**
     * 排序号
     * @return sequence 排序号
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * 排序号
     * @param sequence 排序号
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * 备注
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 接口数据状态
     * @return inf_status 接口数据状态
     */
    public String getInfStatus() {
        return infStatus;
    }

    /**
     * 接口数据状态
     * @param infStatus 接口数据状态
     */
    public void setInfStatus(String infStatus) {
        this.infStatus = infStatus;
    }

    /**
     * 是否可用
     * @return able_status 是否可用
     */
    public String getAbleStatus() {
        return ableStatus;
    }

    /**
     * 是否可用
     * @param ableStatus 是否可用
     */
    public void setAbleStatus(String ableStatus) {
        this.ableStatus = ableStatus;
    }

    /**
     * 乐观锁
     * @return version 乐观锁
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 乐观锁
     * @param version 乐观锁
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 删除状态
     * @return deleted 删除状态
     */
    public String getDeleted() {
        return deleted;
    }

    /**
     * 删除状态
     * @param deleted 删除状态
     */
    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    /**
     * 数据来源
     * @return client_type 数据来源
     */
    public String getClientType() {
        return clientType;
    }

    /**
     * 数据来源
     * @param clientType 数据来源
     */
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    /**
     * 创建者
     * @return create_by 创建者
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 创建者
     * @param createBy 创建者
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 修改者
     * @return modify_by 修改者
     */
    public String getModifyBy() {
        return modifyBy;
    }

    /**
     * 修改者
     * @param modifyBy 修改者
     */
    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    /**
     * 修改时间
     * @return modify_time 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 修改时间
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}