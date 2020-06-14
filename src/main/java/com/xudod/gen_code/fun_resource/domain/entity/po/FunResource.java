package com.xudod.gen_code.fun_resource.domain.entity.po;

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


@ApiModel(value = "权限功能资源")
public class FunResource {
    @ApiModelProperty(example = "id", position = 1)
    private String id;

    @ApiModelProperty(example = "名称", position = 2)
    private String name;

    @ApiModelProperty(example = "中文名称", position = 3)
    private String showNameCn;

    @ApiModelProperty(example = "英文名称", position = 4)
    private String showNameEn;

    @ApiModelProperty(example = "功能编码", position = 5)
    private String code;

    @ApiModelProperty(example = "功能类型", position = 6)
    private String type;

    @ApiModelProperty(example = "路由地址", position = 7)
    private String url;

    @ApiModelProperty(example = "所属系统", position = 8)
    private String belongSys;

    @ApiModelProperty(example = "父级", position = 9)
    private String pid;

    @ApiModelProperty(example = "图标编号", position = 10)
    private String imgNum;

    @ApiModelProperty(example = "系统id", position = 11)
    private String pertainSys;

    @ApiModelProperty(example = "功能级别", position = 12)
    private String level;

    @ApiModelProperty(example = "排序号", position = 13)
    private Integer sequence;

    @ApiModelProperty(example = "备注", position = 14)
    private String remark;

    @ApiModelProperty(example = "分享模式一", hidden = true, position = 15)
    @TableField(value = "public_able", fill = FieldFill.INSERT)
    private String publicAble;

    @ApiModelProperty(example = "分享模式二", hidden = true, position = 16)
    @TableField(value = "shared_able", fill = FieldFill.INSERT)
    private String sharedAble;

    @ApiModelProperty(example = "接口数据状态", hidden = true, position = 17)
    private String infStatus;

    @ApiModelProperty(example = "是否可用", hidden = true, position = 18)
    @TableField(value = "status", fill = FieldFill.INSERT)
    private String status;

    @ApiModelProperty(example = "乐观锁", hidden = true, position = 19)
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    private Integer version;

    @ApiModelProperty(example = "删除状态", hidden = true, position = 20)
    @TableLogic
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    private String deleted;

    @ApiModelProperty(example = "用户id", hidden = true, position = 21)
    private String userId;

    @ApiModelProperty(example = "所属组织id", hidden = true, position = 22)
    private String orgId;

    @ApiModelProperty(example = "所属应用id", hidden = true, position = 23)
    private String appId;

    @ApiModelProperty(example = "数据来源", hidden = true, position = 24)
    private String clientType;

    @ApiModelProperty(example = "创建者", hidden = true, position = 25)
    private String createBy;

    @ApiModelProperty(example = "创建时间", hidden = true, position = 26)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(example = "修改者", hidden = true, position = 27)
    private String modifyBy;

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

    /**
     * 名称
     * @return name 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 中文名称
     * @return show_name_cn 中文名称
     */
    public String getShowNameCn() {
        return showNameCn;
    }

    /**
     * 中文名称
     * @param showNameCn 中文名称
     */
    public void setShowNameCn(String showNameCn) {
        this.showNameCn = showNameCn;
    }

    /**
     * 英文名称
     * @return show_name_en 英文名称
     */
    public String getShowNameEn() {
        return showNameEn;
    }

    /**
     * 英文名称
     * @param showNameEn 英文名称
     */
    public void setShowNameEn(String showNameEn) {
        this.showNameEn = showNameEn;
    }

    /**
     * 功能编码
     * @return code 功能编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 功能编码
     * @param code 功能编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 功能类型
     * @return type 功能类型
     */
    public String getType() {
        return type;
    }

    /**
     * 功能类型
     * @param type 功能类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 路由地址
     * @return url 路由地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 路由地址
     * @param url 路由地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 所属系统
     * @return belong_sys 所属系统
     */
    public String getBelongSys() {
        return belongSys;
    }

    /**
     * 所属系统
     * @param belongSys 所属系统
     */
    public void setBelongSys(String belongSys) {
        this.belongSys = belongSys;
    }

    /**
     * 父级
     * @return pid 父级
     */
    public String getPid() {
        return pid;
    }

    /**
     * 父级
     * @param pid 父级
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * 图标编号
     * @return img_num 图标编号
     */
    public String getImgNum() {
        return imgNum;
    }

    /**
     * 图标编号
     * @param imgNum 图标编号
     */
    public void setImgNum(String imgNum) {
        this.imgNum = imgNum;
    }

    /**
     * 系统id
     * @return pertain_sys 系统id
     */
    public String getPertainSys() {
        return pertainSys;
    }

    /**
     * 系统id
     * @param pertainSys 系统id
     */
    public void setPertainSys(String pertainSys) {
        this.pertainSys = pertainSys;
    }

    /**
     * 功能级别
     * @return level 功能级别
     */
    public String getLevel() {
        return level;
    }

    /**
     * 功能级别
     * @param level 功能级别
     */
    public void setLevel(String level) {
        this.level = level;
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
     * 分享模式一
     * @return public_able 分享模式一
     */
    public String getPublicAble() {
        return publicAble;
    }

    /**
     * 分享模式一
     * @param publicAble 分享模式一
     */
    public void setPublicAble(String publicAble) {
        this.publicAble = publicAble;
    }

    /**
     * 分享模式二
     * @return shared_able 分享模式二
     */
    public String getSharedAble() {
        return sharedAble;
    }

    /**
     * 分享模式二
     * @param sharedAble 分享模式二
     */
    public void setSharedAble(String sharedAble) {
        this.sharedAble = sharedAble;
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
     * @return status 是否可用
     */
    public String getStatus() {
        return status;
    }

    /**
     * 是否可用
     * @param status 是否可用
     */
    public void setStatus(String status) {
        this.status = status;
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
     * 用户id
     * @return user_id 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户id
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 所属组织id
     * @return org_id 所属组织id
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 所属组织id
     * @param orgId 所属组织id
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 所属应用id
     * @return app_id 所属应用id
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 所属应用id
     * @param appId 所属应用id
     */
    public void setAppId(String appId) {
        this.appId = appId;
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