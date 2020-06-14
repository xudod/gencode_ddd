package com.xudod.gencode.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.generator.config.Context;

import com.xudod.gen_code.column_info.domain.entity.po.ColumnInfo;
import com.xudod.gen_code.domain_info.domain.entity.po.DomainInfo;
import com.xudod.gen_code.project_info.domain.entity.po.ProjectInfo;
import com.xudod.gen_code.table_base_info.domain.entity.po.TableBaseInfo;

public class GenCodeInfo {
	
	public GenCodeInfo(){}
	
	public GenCodeInfo(String id){
		this.projectId = id;
	}

	private String projectId;
	
	/**
	 * 项目基本信息
	 */
	private ProjectInfo projectInfo;
	
	/**
	 * 某项目下，领域/微服务信息列表
	 */
	private List<DomainInfo> domainInfoList;
	
	/**
	 * 某个项目下，所有表信息
	 */
	private List<TableBaseInfo> tableBaseInfoList;
	
	/**
	 * 某个项目下，表内字段的信息
	 */
	private List<ColumnInfo> columnInfoList;
	
	/**
	 * 项目生成文件夹路径数据
	 */
	private HashMap<String, String> floderPathData;
	
	/**
	 *  模板文件文件夹位置
	 */
	private String ftlFilePath;
	
	/**
	 * java使用数据库连接地址
	 */
	private String databaseUrl;
	
	/**
	 * 模板使用数据
	 */
	private Map<String, Object> TempData;
	
	/**
	 * 表名列表
	 */
	private HashMap<String, ArrayList<String>> tableList;
	
	/**
	 * eurekaIp
	 */
	private HashMap<String, String> eurekaIp;
	
	/**
	 * eurekaPort
	 */
	private HashMap<String, String> eurekaPort;
	
	/**
	 * 中间过程数据，表名称
	 */
	private String tableName;
	
	
	/**
	 * 中间过程数据，当前实体类名称
	 */
	private String modelName;
	
	/**
	 * 中间过程数据，xml文件文件夹名称
	 */
	private String xmlFileDirName;
	
	/**
	 * 中间过程数据，数据库表的注释
	 */
	private String tableComment;
	
	/**
	 * 中间过程数据，Context对象
	 */
	private Context context;

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public HashMap<String, ArrayList<String>> getTableList() {
		return tableList;
	}

	public void setTableList(HashMap<String, ArrayList<String>> hashMap) {
		this.tableList = hashMap;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getXmlFileDirName() {
		return xmlFileDirName;
	}

	public void setXmlFileDirName(String xmlFileDirName) {
		this.xmlFileDirName = xmlFileDirName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public Map<String, Object> getTempData() {
		return TempData;
	}

	public void setTempData(Map<String, Object> tempData) {
		TempData = tempData;
	}

	public HashMap<String, String> getEurekaIp() {
		return eurekaIp;
	}

	public void setEurekaIp(HashMap<String, String> eurekaIp) {
		this.eurekaIp = eurekaIp;
	}

	public HashMap<String, String> getEurekaPort() {
		return eurekaPort;
	}

	public void setEurekaPort(HashMap<String, String> eurekaPort) {
		this.eurekaPort = eurekaPort;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public ProjectInfo getProjectInfo() {
		return projectInfo;
	}

	public void setProjectInfo(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public List<DomainInfo> getDomainInfoList() {
		return domainInfoList;
	}

	public void setDomainInfoList(List<DomainInfo> domainInfoList) {
		this.domainInfoList = domainInfoList;
	}

	public HashMap<String, String> getFloderPathData() {
		return floderPathData;
	}

	public void setFloderPathData(HashMap<String, String> floderPathData) {
		this.floderPathData = floderPathData;
	}

	public String getFtlFilePath() {
		return ftlFilePath;
	}

	public void setFtlFilePath(String ftlFilePath) {
		this.ftlFilePath = ftlFilePath;
	}

	public String getDatabaseUrl() {
		return databaseUrl;
	}

	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	public List<TableBaseInfo> getTableBaseInfoList() {
		return tableBaseInfoList;
	}

	public void setTableBaseInfoList(List<TableBaseInfo> tableBaseInfoList) {
		this.tableBaseInfoList = tableBaseInfoList;
	}

	public List<ColumnInfo> getColumnInfoList() {
		return columnInfoList;
	}

	public void setColumnInfoList(List<ColumnInfo> columnInfoList) {
		this.columnInfoList = columnInfoList;
	}

}
