package com.xudod.gen_code.core_process.domain.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrontGenCodeInfo extends GenBaseInfo {

	public FrontGenCodeInfo(){}
	
	public FrontGenCodeInfo(String id){
		this.setProjectId(id);
	}
	
	/**
	 * 前端表格字段信息map
	 */
	private List<FrontInfo> frontTableInfoList;
	
	/**
	 * 前端表单字段信息map
	 */
	private List<FrontInfo> frontFormInfoList;

	/**
	 * 项目生成文件夹路径数据
	 */
	private HashMap<String, String> floderPathData;
	
	/**
	 * 初始化项目文件夹路径
	 */
	private String frontFolderAndFilePath;
	
	/**
	 *  模板文件文件夹位置
	 */
	private String ftlFilePath;
	
	/**
	 * java使用数据库连接地址
	 */
	//private String databaseUrl;
	
	/**
	 * 模板使用数据
	 */
	private Map<String, Object> TempData;
	
	/**
	 * 表名列表
	 */
	private HashMap<String, ArrayList<String>> tableList;

	public HashMap<String, ArrayList<String>> getTableList() {
		return tableList;
	}

	public void setTableList(HashMap<String, ArrayList<String>> hashMap) {
		this.tableList = hashMap;
	}
	
	public Map<String, Object> getTempData() {
		return TempData;
	}

	public void setTempData(Map<String, Object> tempData) {
		TempData = tempData;
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

//	public String getDatabaseUrl() {
//		return databaseUrl;
//	}
//
//	public void setDatabaseUrl(String databaseUrl) {
//		this.databaseUrl = databaseUrl;
//	}

	public String getFrontFolderAndFilePath() {
		return frontFolderAndFilePath;
	}

	public void setFrontFolderAndFilePath(String frontFolderAndFilePath) {
		this.frontFolderAndFilePath = frontFolderAndFilePath;
	}

	public List<FrontInfo> getFrontTableInfoList() {
		return frontTableInfoList;
	}

	public void setFrontTableInfoList(List<FrontInfo> frontTableInfoList) {
		this.frontTableInfoList = frontTableInfoList;
	}

	public List<FrontInfo> getFrontFormInfoList() {
		return frontFormInfoList;
	}

	public void setFrontFormInfoList(List<FrontInfo> frontFormInfoList) {
		this.frontFormInfoList = frontFormInfoList;
	}
}
