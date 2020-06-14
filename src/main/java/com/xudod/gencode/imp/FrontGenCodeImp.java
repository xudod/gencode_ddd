package com.xudod.gencode.imp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xudod.gen_code.common.DbUtil;
import com.xudod.gen_code.domain_info.domain.entity.po.DomainInfo;
import com.xudod.gen_code.domain_info.domain.service.DomainInfoService;
import com.xudod.gen_code.fun_resource.domain.entity.po.FunResource;
import com.xudod.gen_code.fun_resource.domain.service.FunResourceService;
import com.xudod.gen_code.project_info.domain.entity.po.ProjectInfo;
import com.xudod.gen_code.project_info.domain.service.ProjectInfoService;
import com.xudod.gencode.data.FrontGenCodeInfo;
import com.xudod.gencode.data.FrontInfo;
import com.xudod.gencode.service.FrontGenCodeService;
import com.xudod.gencode.utils.FileUtils;
import com.xudod.gencode.utils.FreemarkerUtils;
import com.xudod.gencode.utils.StringUtils;

@Service
public class FrontGenCodeImp implements FrontGenCodeService {

	@Autowired
	private ProjectInfoService projectInfoService;
	
	@Autowired
	private DomainInfoService domainInfoService;
	
	@Autowired
	private FunResourceService funResourceService;

	private Logger logger = LoggerFactory.getLogger(FrontGenCodeImp.class);
	
	@Override
	public boolean genFrontProject(String id) {
		FrontGenCodeInfo frontGenCodeInfo = new FrontGenCodeInfo(id);
		//获取项目和微服务信息
		getProjectInfo(frontGenCodeInfo);
		getDomainInfo(frontGenCodeInfo);
		createFunResource(frontGenCodeInfo);
		ProjectInfo p = frontGenCodeInfo.getProjectInfo();
		// TODO 要在项目数据库里增加初始化数据库，用于连接数据库。先写一个固定值
		frontGenCodeInfo.setDatabaseUrl(DbUtil.createDatabaseUrl(p.getDbIp(), p.getDbPort(), "init_project"));
		createFtlFilePath(frontGenCodeInfo);
		createFolderAndFilePath(frontGenCodeInfo);
		copyFrontFolderAndFile(frontGenCodeInfo);
		
		createTemplate(frontGenCodeInfo);
		createBaseFile(frontGenCodeInfo);
		
		createVueFile(frontGenCodeInfo);
		
		
		return false;
	}
	
	/**
	 * 根据项目id获取项目信息
	 * @param genCodeInfo
	 */
	private void getProjectInfo(FrontGenCodeInfo frontGenCodeInfo) {
		ProjectInfo projectInfo = projectInfoService.getbykey(frontGenCodeInfo.getProjectId());
		frontGenCodeInfo.setProjectInfo(projectInfo);
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
			createFrontInfo(frontGenCodeInfo, domainInfo.getDbName(), StringUtils.tableNameConvertUpperCamel(domainInfo.getDbName()));
			tempData.put("domainName", StringUtils.tableNameConvertUpperCamel(domainInfo.getDbName()));
			tempData.put("domainUrl", StringUtils.toLowerCaseFirstOne(StringUtils.tableNameConvertUpperCamel(domainInfo.getDbName())));
			tempData.put("domainKey", domainInfo.getDbName());
			tempData.put("domainCnName",domainInfo.getCnName());
			tempData.put("frontTableInfoList", frontGenCodeInfo.getFrontTableInfoList());
			tempData.put("frontFormInfoList", frontGenCodeInfo.getFrontFormInfoList());
			frontGenCodeInfo.setTempData(tempData);
			String path = frontGenCodeInfo.getProjectInfo().getFrontPath() + "\\" + frontGenCodeInfo.getProjectInfo().getProjectName()
					+ "\\src\\views\\home\\" + domainInfo.getDbName();
			createAny(frontGenCodeInfo, path + "\\" + StringUtils.tableNameConvertUpperCamel(domainInfo.getDbName()) + ".vue", "domainVue.ftl");
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
    public void createFrontInfo(FrontGenCodeInfo frontGenCodeInfo, String ddd_name, String tableName) {
    	String tableSql = "select * from " + ddd_name;
    	List<FrontInfo> frontTableInfoList = new ArrayList<FrontInfo>();
    	List<FrontInfo> frontFormInfoList = new ArrayList<FrontInfo>();
        FrontInfo frontInfo = null;
        //与数据库的连接
        Connection conn = DbUtil.getConnection(frontGenCodeInfo.getDatabaseUrl(), 
        		frontGenCodeInfo.getProjectInfo().getDbUser(), frontGenCodeInfo.getProjectInfo().getDbPassword());
        PreparedStatement pStemt = null;
        try {
            pStemt = conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            ResultSet rs = pStemt.executeQuery("show full columns from " + ddd_name);
            //表列数
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
            	frontInfo = new FrontInfo();
            	rs.next();
            	frontInfo.setProjectId(frontGenCodeInfo.getProjectId());
            	frontInfo.setDomainName(ddd_name);
            	frontInfo.setTableName(tableName);
            	frontInfo.setFieldName(StringUtils.tableNameConvertLowerCamel(rsmd.getColumnName(i + 1)));
            	frontInfo.setFieldType(rsmd.getColumnTypeName(i + 1));
            	String string = rs.getString("Comment");
            	String[] split = string.split("@");
            	for (int j = 0; j < split.length; j++) {
            		switch (split[j].substring(0,1)) {
					case "%":
						frontInfo.setFieldLabel(split[j].substring(1,split[j].length()));
						break;
					case "^":
						String[] split2 = split[j].split(",");
						frontInfo.setFieldRequired(split2[0].substring(1,split2[0].length()));
						frontInfo.setFieldMin(split2[1]);
						frontInfo.setFieldMax(split2[2]);
						break;
					case "*":
						frontInfo.setFieldTitle(split[j].substring(1,split[j].length()));
						break;
					case "&":
						frontInfo.setEditAble(split[j].substring(1,split[j].length()));
						break;
					case "#":
						frontInfo.setFromShow(split[j].substring(1,split[j].length()));
						break;
					case "T":
						frontInfo.setTableShow(split[j].substring(1,split[j].length()));
						break;
					default:
						break;
					}
				}
            	if("true".equals(frontInfo.getFromShow())) {
            		frontFormInfoList.add(frontInfo);
            	}
            	if("true".equals(frontInfo.getTableShow())) {
            		frontTableInfoList.add(frontInfo);
            	}
            }
            frontGenCodeInfo.setFrontTableInfoList(frontTableInfoList);
            frontGenCodeInfo.setFrontFormInfoList(frontFormInfoList);
        } catch (SQLException e) {
        	System.out.println("getColumnNames failure" + e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    DbUtil.closeConnection(conn);
                } catch (SQLException e) {
                	System.out.println("getColumnNames close pstem and connection failure" + e);
                }
            }
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

	/**
	 * 根据项目id，获取所有领域/微服务信息列表
	 * @param genCodeInfo
	 */
	private void getDomainInfo(FrontGenCodeInfo frontGenCodeInfo) {
		List<DomainInfo> domainInfoList = domainInfoService.getDomainListByProjectId(frontGenCodeInfo.getProjectId());
		frontGenCodeInfo.setDomainInfoList(domainInfoList);
	}

	
}
