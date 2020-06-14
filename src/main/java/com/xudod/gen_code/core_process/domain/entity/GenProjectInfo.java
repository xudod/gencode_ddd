package com.xudod.gen_code.core_process.domain.entity;

import java.util.List;

import com.xudod.gen_code.column_info.domain.entity.po.ColumnInfo;
import com.xudod.gen_code.domain_info.domain.entity.po.DomainInfo;
import com.xudod.gen_code.project_info.domain.entity.po.ProjectInfo;
import com.xudod.gen_code.table_base_info.domain.entity.po.TableBaseInfo;

public class GenProjectInfo {
	
	public GenProjectInfo(){}
	
	public GenProjectInfo(String id){
		this.setProjectId(id);
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

	public ProjectInfo getProjectInfo() {
		return projectInfo;
	}

	public void setProjectInfo(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
	}
	
}
