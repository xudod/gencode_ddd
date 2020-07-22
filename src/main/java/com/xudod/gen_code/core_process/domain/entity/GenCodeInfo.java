package com.xudod.gen_code.core_process.domain.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.generator.config.Context;

public class GenCodeInfo extends GenBaseInfo {
	
	public GenCodeInfo(){}
	
	public GenCodeInfo(String id){
		this.setProjectId(id);
	}
	
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

}
