package com.xudod.gen_code.project_info.domain.entity.po;

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


@ApiModel(value = "项目信息")
public class ProjectInfo {
    @ApiModelProperty(example = "id", position = 1)
    private String id;

    @ApiModelProperty(example = "项目中文名", position = 2)
    private String projectCnName;

    @ApiModelProperty(example = "项目名称", position = 3)
    private String projectName;

    @ApiModelProperty(example = "负责人", position = 5)
    private String charge;

    @ApiModelProperty(example = "开始日期", position = 6)
    private Date createDate;

    @ApiModelProperty(example = "公司", position = 7)
    private String company;

    @ApiModelProperty(example = "数据库ip", position = 8)
    private String dbIp;

    @ApiModelProperty(example = "数据库port", position = 9)
    private String dbPort;

    @ApiModelProperty(example = "db用户名", position = 10)
    private String dbUser;

    @ApiModelProperty(example = "db密码", position = 11)
    private String dbPassword;

    @ApiModelProperty(example = "代码作者", position = 12)
    private String author;

    @ApiModelProperty(example = "项目groupId", position = 13)
    private String groupId;

    @ApiModelProperty(example = "项目artifactId", position = 14)
    private String artifactId;

    @ApiModelProperty(example = "项目port", position = 15)
    private String projectPort;

    @ApiModelProperty(example = "雪花id序号", position = 16)
    private String snowIdSeq;

    @ApiModelProperty(example = "开发环境ip", position = 17)
    private String devIp;

    @ApiModelProperty(example = "测试环境ip", position = 19)
    private String testIp;

    @ApiModelProperty(example = "UAT环境ip", position = 21)
    private String uatIp;

    @ApiModelProperty(example = "生产环境ip", position = 23)
    private String prodIp;

    @ApiModelProperty(example = "项目描述", position = 25)
    private String description;

    @ApiModelProperty(example = "后端路径", position = 26)
    private String behindPath;

    @ApiModelProperty(example = "前端路径", position = 27)
    private String frontPath;

    @ApiModelProperty(example = "排序号", position = 28)
    private Integer sequence;

    @ApiModelProperty(example = "备注", position = 29)
    private String remark;

    @ApiModelProperty(example = "接口数据状态", hidden = true, position = 30)
    private String infStatus;

    @ApiModelProperty(example = "是否可用", position = 31)
    private String useStatus;

    @ApiModelProperty(example = "乐观锁", hidden = true, position = 32)
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    private Integer version;

    @ApiModelProperty(example = "删除状态", hidden = true, position = 33)
    @TableLogic
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    private String deleted;

    @ApiModelProperty(example = "所属应用id", hidden = true, position = 34)
    private String appId;

    @ApiModelProperty(example = "创建者", hidden = true, position = 35)
    private String createBy;

    @ApiModelProperty(example = "创建时间", hidden = true, position = 36)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(example = "修改者", hidden = true, position = 37)
    private String modifyBy;

    @ApiModelProperty(example = "修改时间", hidden = true, position = 38)
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
     * 项目中文名
     * @return project_cn_name 项目中文名
     */
    public String getProjectCnName() {
        return projectCnName;
    }

    /**
     * 项目中文名
     * @param projectCnName 项目中文名
     */
    public void setProjectCnName(String projectCnName) {
        this.projectCnName = projectCnName;
    }

    /**
     * 项目名称
     * @return project_name 项目名称
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * 项目名称
     * @param projectName 项目名称
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * 负责人
     * @return charge 负责人
     */
    public String getCharge() {
        return charge;
    }

    /**
     * 负责人
     * @param charge 负责人
     */
    public void setCharge(String charge) {
        this.charge = charge;
    }

    /**
     * 开始日期
     * @return create_date 开始日期
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 开始日期
     * @param createDate 开始日期
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 公司
     * @return company 公司
     */
    public String getCompany() {
        return company;
    }

    /**
     * 公司
     * @param company 公司
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * 数据库ip
     * @return db_ip 数据库ip
     */
    public String getDbIp() {
        return dbIp;
    }

    /**
     * 数据库ip
     * @param dbIp 数据库ip
     */
    public void setDbIp(String dbIp) {
        this.dbIp = dbIp;
    }

    /**
     * 数据库port
     * @return db_port 数据库port
     */
    public String getDbPort() {
        return dbPort;
    }

    /**
     * 数据库port
     * @param dbPort 数据库port
     */
    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    /**
     * db用户名
     * @return db_user db用户名
     */
    public String getDbUser() {
        return dbUser;
    }

    /**
     * db用户名
     * @param dbUser db用户名
     */
    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    /**
     * db密码
     * @return db_password db密码
     */
    public String getDbPassword() {
        return dbPassword;
    }

    /**
     * db密码
     * @param dbPassword db密码
     */
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    /**
     * 代码作者
     * @return author 代码作者
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 代码作者
     * @param author 代码作者
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 项目groupId
     * @return group_id 项目groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * 项目groupId
     * @param groupId 项目groupId
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * 项目artifactId
     * @return artifact_id 项目artifactId
     */
    public String getArtifactId() {
        return artifactId;
    }

    /**
     * 项目artifactId
     * @param artifactId 项目artifactId
     */
    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    /**
     * 项目port
     * @return project_port 项目port
     */
    public String getProjectPort() {
        return projectPort;
    }

    /**
     * 项目port
     * @param projectPort 项目port
     */
    public void setProjectPort(String projectPort) {
        this.projectPort = projectPort;
    }

    /**
     * 雪花id序号
     * @return snow_id_seq 雪花id序号
     */
    public String getSnowIdSeq() {
        return snowIdSeq;
    }

    /**
     * 雪花id序号
     * @param snowIdSeq 雪花id序号
     */
    public void setSnowIdSeq(String snowIdSeq) {
        this.snowIdSeq = snowIdSeq;
    }

    /**
     * 开发环境ip
     * @return dev_ip 开发环境ip
     */
    public String getDevIp() {
        return devIp;
    }

    /**
     * 开发环境ip
     * @param devIp 开发环境ip
     */
    public void setDevIp(String devIp) {
        this.devIp = devIp;
    }

    /**
     * 测试环境ip
     * @return test_ip 测试环境ip
     */
    public String getTestIp() {
        return testIp;
    }

    /**
     * 测试环境ip
     * @param testIp 测试环境ip
     */
    public void setTestIp(String testIp) {
        this.testIp = testIp;
    }

    /**
     * UAT环境ip
     * @return uat_ip UAT环境ip
     */
    public String getUatIp() {
        return uatIp;
    }

    /**
     * UAT环境ip
     * @param uatIp UAT环境ip
     */
    public void setUatIp(String uatIp) {
        this.uatIp = uatIp;
    }

    /**
     * 生产环境ip
     * @return prod_ip 生产环境ip
     */
    public String getProdIp() {
        return prodIp;
    }

    /**
     * 生产环境ip
     * @param prodIp 生产环境ip
     */
    public void setProdIp(String prodIp) {
        this.prodIp = prodIp;
    }

    /**
     * 项目描述
     * @return description 项目描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 项目描述
     * @param description 项目描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 后端路径
     * @return behind_path 后端路径
     */
    public String getBehindPath() {
        return behindPath;
    }

    /**
     * 后端路径
     * @param behindPath 后端路径
     */
    public void setBehindPath(String behindPath) {
        this.behindPath = behindPath;
    }

    /**
     * 前端路径
     * @return front_path 前端路径
     */
    public String getFrontPath() {
        return frontPath;
    }

    /**
     * 前端路径
     * @param frontPath 前端路径
     */
    public void setFrontPath(String frontPath) {
        this.frontPath = frontPath;
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
     * @return use_status 是否可用
     */
    public String getUseStatus() {
        return useStatus;
    }

    /**
     * 是否可用
     * @param useStatus 是否可用
     */
    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
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