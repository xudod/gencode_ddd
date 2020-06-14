package com.xudod.gencode.data;

//原来是数据表存这些信息，后来觉得表没用就只留下了这个实体")
public class FrontInfo {

    //项目id
    private String projectId;

    //领域名称
    private String domainName;

    //"表名称
    private String tableName;

    //字段label
    private String fieldLabel;

    //字段名称
    private String fieldName;

    //字段类型
    private String fieldType;

    //是否必填
    private String fieldRequired;

    //最小长度
    private String fieldMin;

    //最大长度
    private String fieldMax;

    //提示信息
    private String fieldTitle;

    //表单中是否显示
    private String fromShow;

    //表格中是否显示
    private String tableShow;

    //可否编辑
    private String editAble;


    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldRequired() {
        return fieldRequired;
    }

    public void setFieldRequired(String fieldRequired) {
        this.fieldRequired = fieldRequired;
    }

    public String getFieldMin() {
        return fieldMin;
    }

    public void setFieldMin(String fieldMin) {
        this.fieldMin = fieldMin;
    }

    public String getFieldMax() {
        return fieldMax;
    }

    public void setFieldMax(String fieldMax) {
        this.fieldMax = fieldMax;
    }

    public String getFieldTitle() {
        return fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public String getFromShow() {
        return fromShow;
    }

    public void setFromShow(String fromShow) {
        this.fromShow = fromShow;
    }

    public String getEditAble() {
        return editAble;
    }

    public void setEditAble(String editAble) {
        this.editAble = editAble;
    }

	public String getTableShow() {
		return tableShow;
	}

	public void setTableShow(String tableShow) {
		this.tableShow = tableShow;
	}
}