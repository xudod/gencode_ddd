package com.xudod.gen_code.core_process.domain.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xudod.gen_code.column_info.domain.entity.po.ColumnInfo;
import com.xudod.gen_code.column_info.domain.service.ColumnInfoService;
import com.xudod.gen_code.core_process.domain.entity.FrontGenCodeInfo;
import com.xudod.gen_code.core_process.domain.entity.FrontInfo;
import com.xudod.gen_code.domain_info.domain.entity.po.DomainInfo;
import com.xudod.gen_code.domain_info.domain.service.DomainInfoService;
import com.xudod.gen_code.fun_resource.domain.entity.po.FunResource;
import com.xudod.gen_code.fun_resource.domain.service.FunResourceService;
import com.xudod.gen_code.project_info.domain.entity.po.ProjectInfo;
import com.xudod.gen_code.project_info.domain.service.ProjectInfoService;
import com.xudod.gen_code.table_base_info.domain.entity.po.TableBaseInfo;
import com.xudod.gen_code.table_base_info.domain.service.TableBaseInfoService;
import com.xudod.gencode.utils.FileUtils;
import com.xudod.gencode.utils.FreemarkerUtils;
import com.xudod.gencode.utils.StringUtils;

@Service("genVueProjectService")
public class GenVueProjectImp implements GenVueProjectService {
	
	@Autowired
	private ProjectInfoService projectInfoService;
	
	@Autowired
	private DomainInfoService domainInfoService;
	
	@Autowired
	private TableBaseInfoService tableBaseInfoService;
	
	@Autowired
	private ColumnInfoService columnInfoService;
	
	@Autowired
	private FunResourceService funResourceService;

	private Logger logger = LoggerFactory.getLogger(GenVueProjectImp.class);
	
	@Override
	public Boolean genFrontProject(String projectId) {
		FrontGenCodeInfo frontGenCodeInfo = new FrontGenCodeInfo(projectId);
		getProjectInfo(frontGenCodeInfo);
		createFunResource(frontGenCodeInfo);
		ProjectInfo p = frontGenCodeInfo.getProjectInfo();
		// TODO 要在项目数据库里增加初始化数据库，用于连接数据库。先写一个固定值
		//frontGenCodeInfo.setDatabaseUrl(DbUtil.createDatabaseUrl(p.getDbIp(), p.getDbPort(), "init_project"));
		createFtlFilePath(frontGenCodeInfo);
		createFolderAndFilePath(frontGenCodeInfo);
		copyFrontFolderAndFile(frontGenCodeInfo);
		
		createTemplate(frontGenCodeInfo);
		createBaseFile(frontGenCodeInfo);
		
		createVueFile(frontGenCodeInfo);
		
		
		return false;
	}
	
	// TODO 待改进，其中for循环能不能抽成一个方法
	// TODO 有很多个类继承了GenProjectInfo类，能不能将这个方法抽成公共方法。
	// 入参能不能使用泛型。
	private void getProjectInfo(FrontGenCodeInfo frontGenCodeInfo) {
		String projectId = frontGenCodeInfo.getProjectId();
		ProjectInfo getbykey = projectInfoService.getbykey(projectId);
		List<DomainInfo> domainList = domainInfoService.getDomainListByProjectId(projectId);
		List<String> domainIds = new ArrayList<String>();
		for (DomainInfo domainInfo : domainList) {
			domainIds.add(domainInfo.getId());
		}
		List<TableBaseInfo> tableList = tableBaseInfoService.getTableByDomainIds(domainIds);
		List<ColumnInfo> columnList = columnInfoService.getTableColumnByTableIds(tableList);
		List<ColumnInfo> columnListNew = new ArrayList<ColumnInfo>();
		frontGenCodeInfo.setProjectInfo(getbykey);
		frontGenCodeInfo.setDomainInfoList(domainList);
		frontGenCodeInfo.setTableBaseInfoList(tableList);
		for (ColumnInfo column : columnList) {
			column.setNameUpperCamel(StringUtils.tableNameConvertLowerCamel(column.getName()));
			column.setNameHeadUpperCamel(StringUtils.tableNameConvertUpperCamel(column.getName()));
			columnListNew.add(column);
		}
		frontGenCodeInfo.setColumnInfoList(columnListNew);
	}

	private void createFunResource(FrontGenCodeInfo frontGenCodeInfo) {
		List<DomainInfo> domainInfoList = frontGenCodeInfo.getDomainInfoList();
		FunResource funResource = null;
		for (DomainInfo domainInfo : domainInfoList) {
			funResource = new FunResource();
			funResource.setShowNameCn(domainInfo.getCnName());
			funResource.setShowNameEn(domainInfo.getDbTableList());
			funResource.setCode("aaa");
			funResource.setType("menu");
			funResource.setUrl("/" + StringUtils.tableNameConvertUpperCamel(domainInfo.getDbName()));
			funResourceService.add(funResource);
		}
		
	}

	private void createVueFile(FrontGenCodeInfo frontGenCodeInfo) {
		Map<String, Object> tempData = frontGenCodeInfo.getTempData();
		List<DomainInfo> domainInfoList = frontGenCodeInfo.getDomainInfoList();
		for (DomainInfo domainInfo : domainInfoList) {
			List<TableBaseInfo> tableBaseInfoList = frontGenCodeInfo.getTableBaseInfoList();
			String id = domainInfo.getId();
			List<TableBaseInfo> tableList = tableBaseInfoList.stream().filter(x-> (id.equals(x.getDomainId()))).collect(Collectors.toList());
			for (TableBaseInfo table : tableList) {
				
				createFrontInfo(frontGenCodeInfo, domainInfo.getDbName(), StringUtils.tableNameConvertUpperCamel(table.getTableName()), table.getId());
				tempData.put("domainName", StringUtils.tableNameConvertUpperCamel(domainInfo.getDbName()));
				tempData.put("domainUrl", StringUtils.toLowerCaseFirstOne(StringUtils.tableNameConvertUpperCamel(table.getTableName())));
				tempData.put("domainKey", domainInfo.getDbName());
				tempData.put("domainCnName",domainInfo.getCnName());
				tempData.put("tableName", StringUtils.tableNameConvertUpperCamel(table.getTableName()));
				if(table.getTableNameCn() != null && !"".equals(table.getTableNameCn())) {
					tempData.put("tableNameCn", StringUtils.tableNameConvertUpperCamel(table.getTableNameCn()));
				}else {
					tempData.put("tableNameCn", "");
				}
				tempData.put("frontTableInfoList", frontGenCodeInfo.getFrontTableInfoList());
				tempData.put("frontFormInfoList", frontGenCodeInfo.getFrontFormInfoList());
				frontGenCodeInfo.setTempData(tempData);
				String path = frontGenCodeInfo.getProjectInfo().getFrontPath() + "\\" + frontGenCodeInfo.getProjectInfo().getProjectName()
						+ "\\src\\views\\home\\" + domainInfo.getDbName();
				createAny(frontGenCodeInfo, path + "\\" + StringUtils.tableNameConvertUpperCamel(table.getTableName()) + ".vue", "domainVue.ftl");
			}
		}
	}

	private void createBaseFile(FrontGenCodeInfo frontGenCodeInfo) {
		String path = frontGenCodeInfo.getProjectInfo().getFrontPath() + "\\" + frontGenCodeInfo.getProjectInfo().getProjectName();
		createAny(frontGenCodeInfo, path + "\\src\\router\\index.js", "router.ftl");
		createAny(frontGenCodeInfo, path + "\\.env.development", "envDevelopment.ftl");
		createAny(frontGenCodeInfo, path + "\\.env.production", "envProduction.ftl");
	}
	
	private void createAny(FrontGenCodeInfo frontGenCodeInfo, String fileInfo, String tempName) {
		try {
			freemarker.template.Configuration cfg = FreemarkerUtils.initFreemarkerConfiguration(frontGenCodeInfo.getFtlFilePath());
			Map<String, Object> data = frontGenCodeInfo.getTempData();
			File file = new File(fileInfo);
		
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
	        }
			FileWriter fileWriter = new FileWriter(file);
	        cfg.getTemplate(tempName).process(data, fileWriter);
	        fileWriter.close();
	        logger.info(fileInfo + "生成成功!");
	    } catch (Exception e) {
	        throw new RuntimeException(fileInfo + "生成异常!", e);
	    }
	}
	
	private void createTemplate(FrontGenCodeInfo frontGenCodeInfo) {
		ProjectInfo projectInfo = frontGenCodeInfo.getProjectInfo();
		Map<String, Object> tempData = new HashMap<String, Object>();
		List<DomainInfo> domainInfoList = frontGenCodeInfo.getDomainInfoList();
		HashMap<String, String> hashMap = new HashMap<String, String>();
		for (DomainInfo domainInfo : domainInfoList) {
			hashMap.put(domainInfo.getDbName(), StringUtils.tableNameConvertUpperCamel(domainInfo.getDbName()));
		}
		tempData.put("domainInfoList", hashMap);
		tempData.put("devIp", projectInfo.getDevIp());
		tempData.put("port", projectInfo.getProjectPort());
		tempData.put("testIp", projectInfo.getTestIp());
		tempData.put("uatIp", projectInfo.getUatIp());
		tempData.put("prodIp", projectInfo.getProdIp());
		
		frontGenCodeInfo.setTempData(tempData);
	}

	private void copyFrontFolderAndFile(FrontGenCodeInfo frontGenCodeInfo) {
		//项目运行在win下没有问题，如果是在linux下，要转换路径格式。
		try {
			String path = frontGenCodeInfo.getProjectInfo().getFrontPath() + "\\" + frontGenCodeInfo.getProjectInfo().getProjectName();
			FileUtils.copyDir(frontGenCodeInfo.getFrontFolderAndFilePath(), path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
    /**
     * 创建表中所有字段数据为前端使用的信息
     */
    public void createFrontInfo(FrontGenCodeInfo frontGenCodeInfo, String ddd_name, String tableName, String tableId) {
    	List<FrontInfo> frontTableInfoList = new ArrayList<FrontInfo>();
    	List<FrontInfo> frontFormInfoList = new ArrayList<FrontInfo>();
    	List<ColumnInfo> columnInfoList = frontGenCodeInfo.getColumnInfoList();
    	List<ColumnInfo> ColumnList = columnInfoList.stream().filter(x-> (tableId.equals(x.getTableId()))).collect(Collectors.toList());
        FrontInfo frontInfo = null;
        try {
        	for (ColumnInfo columnInfo : ColumnList) {
            	frontInfo = new FrontInfo();
            	frontInfo.setProjectId(frontGenCodeInfo.getProjectId());
            	frontInfo.setDomainName(ddd_name);
            	frontInfo.setTableName(tableName);
            	frontInfo.setFieldName(StringUtils.tableNameConvertLowerCamel(columnInfo.getName()));
            	frontInfo.setFieldType(columnInfo.getColumnType());
				frontInfo.setFieldLabel(columnInfo.getNameCn());
				//frontInfo.setFieldRequired(columnInfo.getCheckType());
				frontInfo.setFieldRequired("1");
				String checkValue = columnInfo.getCheckValue();
				//暂时还没有做校验，后边再说。
//				String[] split2 = split[j].split(",");
//				frontInfo.setFieldMin(split2[1]);
//				frontInfo.setFieldMax(split2[2]);
				frontInfo.setFieldMin("3");
				frontInfo.setFieldMax("30");
				frontInfo.setFieldTitle(columnInfo.getPageTitle());
				frontInfo.setEditAble(columnInfo.getEditUse());
				frontInfo.setFromShow(columnInfo.getNewShow());
				frontInfo.setTableShow(columnInfo.getTableShow());
            	if("1".equals(frontInfo.getFromShow())) {
            		frontFormInfoList.add(frontInfo);
            	}
            	if("1".equals(frontInfo.getTableShow())) {
            		frontTableInfoList.add(frontInfo);
            	}
            }
            frontGenCodeInfo.setFrontTableInfoList(frontTableInfoList);
            frontGenCodeInfo.setFrontFormInfoList(frontFormInfoList);
        } catch (Exception e) {
        	System.out.println("getColumnNames failure" + e);
        }
    }

	/***********************生成基础文件********************************/

	/**
	 * 生成初始化项目目录位置
	 * @param genCodeInfo
	 */
	private void createFolderAndFilePath(FrontGenCodeInfo frontGenCodeInfo) {
		String frontFolderAndFile = System.getProperty("user.dir");
		frontFolderAndFile = frontFolderAndFile + "\\src\\main\\resources\\frontFolderAndFile";
		frontGenCodeInfo.setFrontFolderAndFilePath(frontFolderAndFile);
	}

	/**
	 * 生成模板文件文件夹位置
	 * @param genCodeInfo
	 */
	private void createFtlFilePath(FrontGenCodeInfo frontGenCodeInfo) {
		String frontFtlTemplate = System.getProperty("user.dir");
		frontFtlTemplate = frontFtlTemplate + "\\src\\main\\resources\\frontFtlTemplate";
		frontGenCodeInfo.setFtlFilePath(frontFtlTemplate);
	}
	
}
