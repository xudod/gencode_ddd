package com.xudod.gen_code.core_process.domain.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.CaseFormat;
import com.xudod.gen_code.column_info.domain.entity.po.ColumnInfo;
import com.xudod.gen_code.column_info.domain.service.ColumnInfoService;
import com.xudod.gen_code.common.FileUtils;
import com.xudod.gen_code.common.FreemarkerUtils;
import com.xudod.gen_code.common.StringUtils;
import com.xudod.gen_code.core_process.domain.entity.GenCodeInfo;
import com.xudod.gen_code.domain_info.domain.entity.po.DomainInfo;
import com.xudod.gen_code.domain_info.domain.service.DomainInfoService;
import com.xudod.gen_code.project_info.domain.entity.po.ProjectInfo;
import com.xudod.gen_code.project_info.domain.service.ProjectInfoService;
import com.xudod.gen_code.table_base_info.domain.entity.po.TableBaseInfo;
import com.xudod.gen_code.table_base_info.domain.service.TableBaseInfoService;


import freemarker.template.Configuration;

@Service("genJavaProJectService")
public class GenJavaProjectImp implements GenJavaProJectService {
	
	@Autowired
	private ProjectInfoService projectInfoService;
	
	@Autowired
	private DomainInfoService domainInfoService;
	
	@Autowired
	private TableBaseInfoService tableBaseInfoService;
	
	@Autowired
	private ColumnInfoService columnInfoService;

	private Logger logger = LoggerFactory.getLogger(GenJavaProjectImp.class);

	@Override
	public Boolean genCodeAllProject(String projectId) {
		GenCodeInfo genCodeInfo = new GenCodeInfo(projectId);
		getProjectInfo(genCodeInfo);
		createEurekaIpInfo(genCodeInfo);
		createEurekaPortInfo(genCodeInfo);
		createFtlFilePath(genCodeInfo);
		createDatabaseUrl(genCodeInfo);
		createFloderInfo(genCodeInfo);
		createTemplate(genCodeInfo);
		
		createBaseFile(genCodeInfo);
		createMapperXml(genCodeInfo);
		createBean(genCodeInfo);
		
		createMybatisPlusConfig(genCodeInfo);
		createMyMetaObjectHandler(genCodeInfo);
		return null;
	}
	
	private void createBean(GenCodeInfo genCodeInfo) {
		HashMap<String, String> floderPathData = genCodeInfo.getFloderPathData();
		List<DomainInfo> domainInfoList = genCodeInfo.getDomainInfoList();
		for (DomainInfo domainInfo : domainInfoList) {
			String resourcesPath = floderPathData.get("businessPath") + "\\" + domainInfo.getDbName() + "\\domain\\entity\\po\\";
			List<TableBaseInfo> tableBaseInfoList = genCodeInfo.getTableBaseInfoList();
			String id = domainInfo.getId();
			List<TableBaseInfo> tableList = tableBaseInfoList.stream().filter(x-> (id.equals(x.getDomainId()))).collect(Collectors.toList());
			for (TableBaseInfo table : tableList) {
				List<ColumnInfo> columnInfoList = genCodeInfo.getColumnInfoList();
				try {
					Configuration cfg = FreemarkerUtils.initFreemarkerConfiguration(genCodeInfo.getFtlFilePath());
					String tableId = table.getId();
					List<ColumnInfo> ColumnList = columnInfoList.stream().filter(x-> (tableId.equals(x.getTableId()))).collect(Collectors.toList());
					if(null == ColumnList || ColumnList.size() == 0) {
						continue;
					}

					String modelNameUpperCamel = StringUtils.tableNameConvertUpperCamel(table.getTableName());
					Map<String, Object> data = genCodeInfo.getTempData();
					data.put("ddd_name", domainInfo.getDbName());
					data.put("modelNameUpperCamel", modelNameUpperCamel);
					data.put("columnList", ColumnList);
					
					data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
					data.put("baseRequestMapping", StringUtils.toLowerCaseFirstOne(modelNameUpperCamel));
					data.put("tableComment", "表说明");
					data.put("tableName", table.getTableName());
					data.put("Vo", modelNameUpperCamel + "Vo");
					
					
//					//根据表名处理一些模板中要用的变量
//					genCodeInfo.setTableName(tableName);
//					String modelName = StringUtils.tableNameConvertUpperCamel(tableName);
//					genCodeInfo.setModelName(modelName);
//					tempData.put("tableName", genCodeInfo.getTableName());
//					tempData.put("baseRequestMapping", StringUtils.toLowerCaseFirstOne(modelName));
//					tempData.put("modelNameUpperCamel", modelName);
//					tempData.put("Vo", modelName + "Vo");
//					tempData.put("ddd_name", ddd_name);
//					tempData.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelName));
					File parntymlFile = new File(resourcesPath + "\\" + modelNameUpperCamel + ".java");
					if (!parntymlFile.getParentFile().exists()) {
						parntymlFile.getParentFile().mkdirs();
					}
					cfg.getTemplate("bean.ftl").process(data, new FileWriter(parntymlFile));
					logger.info("bean生成成功!");
					
					createImp(genCodeInfo);
					createController(genCodeInfo);
					createService(genCodeInfo);
					createMapperJava(genCodeInfo);
					createTest(genCodeInfo);
				} catch (Exception e) {
					throw new RuntimeException("bean生成异常!", e);
				}
			}
		}
	}

	private void createMapperXml(GenCodeInfo genCodeInfo) {
		HashMap<String, String> floderPathData = genCodeInfo.getFloderPathData();
		List<DomainInfo> domainInfoList = genCodeInfo.getDomainInfoList();
		for (DomainInfo domainInfo : domainInfoList) {
			String resourcesPath = floderPathData.get("rootPath") + floderPathData.get("resourcesPath") + "\\mapper\\";
			List<TableBaseInfo> tableBaseInfoList = genCodeInfo.getTableBaseInfoList();
			String id = domainInfo.getId();
			List<TableBaseInfo> tableList = tableBaseInfoList.stream().filter(x-> (id.equals(x.getDomainId()))).collect(Collectors.toList());
			for (TableBaseInfo table : tableList) {
				List<ColumnInfo> columnInfoList = genCodeInfo.getColumnInfoList();
				try {
					Configuration cfg = FreemarkerUtils.initFreemarkerConfiguration(genCodeInfo.getFtlFilePath());
					String tableId = table.getId();
					List<ColumnInfo> ColumnList = columnInfoList.stream().filter(x-> (tableId.equals(x.getTableId()))).collect(Collectors.toList());
					if(null == ColumnList || ColumnList.size() == 0) {
						continue;
					}
					String modelNameUpperCamel = StringUtils.tableNameConvertUpperCamel(table.getTableName());
					Map<String, Object> data = genCodeInfo.getTempData();
					data.put("ddd_name", domainInfo.getDbName());
					data.put("modelNameUpperCamel", modelNameUpperCamel);
					data.put("columnList", ColumnList);
					File parntymlFile = new File(resourcesPath + modelNameUpperCamel + "Mapper.xml\\");
					if (!parntymlFile.getParentFile().exists()) {
						parntymlFile.getParentFile().mkdirs();
					}
					cfg.getTemplate("mapper_xml.ftl").process(data, new FileWriter(parntymlFile));
					logger.info("mapper_xml生成成功!");
				} catch (Exception e) {
					throw new RuntimeException("mapper_xml生成异常!", e);
				}
			}
		}
	}

	// TODO 待改进，其中for循环能不能抽成一个方法
	// TODO 有很多个类继承了GenProjectInfo类，能不能将这个方法抽成公共方法。
	// 入参能不能使用泛型。
	private void getProjectInfo(GenCodeInfo genCodeInfo) {
		String projectId = genCodeInfo.getProjectId();
		ProjectInfo getbykey = projectInfoService.getbykey(projectId);
		List<DomainInfo> domainList = domainInfoService.getDomainListByProjectId(projectId);
		List<String> domainIds = new ArrayList<String>();
		for (DomainInfo domainInfo : domainList) {
			domainIds.add(domainInfo.getId());
		}
		List<TableBaseInfo> tableList = tableBaseInfoService.getTableByDomainIds(domainIds);
		List<ColumnInfo> columnList = columnInfoService.getTableColumnByTableIds(tableList);
		List<ColumnInfo> columnListNew = new ArrayList<ColumnInfo>();
		genCodeInfo.setProjectInfo(getbykey);
		genCodeInfo.setDomainInfoList(domainList);
		genCodeInfo.setTableBaseInfoList(tableList);
		for (ColumnInfo column : columnList) {
			column.setNameUpperCamel(StringUtils.tableNameConvertLowerCamel(column.getName()));
			column.setNameHeadUpperCamel(StringUtils.tableNameConvertUpperCamel(column.getName()));
			columnListNew.add(column);
		}
		genCodeInfo.setColumnInfoList(columnListNew);
	}
	
	private void createEurekaIpInfo(GenCodeInfo genCodeInfo) {
		HashMap<String , String> eurekaIp = new HashMap<String, String>();
    	eurekaIp.put("dev", genCodeInfo.getProjectInfo().getDevIp());
    	eurekaIp.put("test", genCodeInfo.getProjectInfo().getTestIp());
    	eurekaIp.put("prod", genCodeInfo.getProjectInfo().getProdIp());
    	genCodeInfo.setEurekaIp(eurekaIp);
	}
	
	private void createEurekaPortInfo(GenCodeInfo genCodeInfo) {
    	HashMap<String , String> eurekaPort = new HashMap<String, String>();
    	eurekaPort.put("dev", genCodeInfo.getProjectInfo().getProjectPort());
    	eurekaPort.put("test", genCodeInfo.getProjectInfo().getProjectPort());
    	eurekaPort.put("prod", genCodeInfo.getProjectInfo().getProjectPort());
    	genCodeInfo.setEurekaPort(eurekaPort);
	}
	
	/**
	 * 生成模板文件文件夹位置
	 * @param genCodeInfo
	 */
	private void createFtlFilePath(GenCodeInfo genCodeInfo) {
		genCodeInfo.setFtlFilePath(System.getProperty("user.dir") + "\\src\\main\\resources\\generator\\template");
	}
	/**
	 * 生成数据库连接用databaseUrl
	 * @param genCodeInfo
	 */
	private void createDatabaseUrl(GenCodeInfo genCodeInfo) {
		ProjectInfo projectInfo = genCodeInfo.getProjectInfo();
		String databaseUrl = "jdbc:mysql://" + projectInfo.getDbIp() + ":" + projectInfo.getDbPort() + "/"
			+ genCodeInfo.getProjectInfo().getProjectName() + "?serverTimezone=GMT%2B8&amp&useSSL=false&amp&nullCatalogMeansCurrent=true&amp&allowPublicKeyRetrieval=true";
		genCodeInfo.setDatabaseUrl(databaseUrl);
	}

	/**
	 * 生成模板需要的参数
	 * @param genCodeInfo
	 */
	private void createTemplate(GenCodeInfo genCodeInfo) {
		ProjectInfo projectInfo = genCodeInfo.getProjectInfo();
		String basePackageValue = projectInfo.getGroupId() + "." + projectInfo.getArtifactId();
		HashMap<String, Object> tempData = new HashMap<String, Object>();
		tempData.put("groupId", projectInfo.getGroupId());
		tempData.put("artifactId", projectInfo.getArtifactId());
		tempData.put("description", projectInfo.getDescription());
		//tempData.put("schame", genCodeInfo.getInitParamData().get("schame"));
		tempData.put("projectPort", projectInfo.getProjectPort());
		tempData.put("eurekaName", projectInfo.getProjectName().replace("_", "-"));
		tempData.put("databaseDriver", "com.mysql.cj.jdbc.Driver");
		tempData.put("databaseUrl", genCodeInfo.getDatabaseUrl());
		tempData.put("databaseUserName", projectInfo.getDbUser());
		tempData.put("databasePassword", projectInfo.getDbPassword());
		tempData.put("basePackageValue", basePackageValue);
		tempData.put("entityPackageName", "domain.entity");
		tempData.put("mapperPackageName", "domain.mapper");
		tempData.put("projectDatacenterId", projectInfo.getSnowIdSeq());
		tempData.put("date", new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
		tempData.put("author", projectInfo.getAuthor());
		tempData.put("workerId", "@Value(\"${snowid.workerId}\")");
		tempData.put("datacenterId", "@Value(\"${snowid.datacenterId}\")");
		genCodeInfo.setTempData(tempData);
	}

	/**
	 * 根据项目信息生成项目文件夹目录信息
	 * @param genCodeInfo
	 */
	private void createFloderInfo(GenCodeInfo genCodeInfo) {
		ProjectInfo projectInfo = genCodeInfo.getProjectInfo();
		String rootPath = projectInfo.getBehindPath() + "\\" + genCodeInfo.getProjectInfo().getProjectName();
		String javaPath = "\\src\\main\\java\\";
		String testPath = "\\src\\test\\java\\";
		String basePackagePath = projectInfo.getGroupId().replace(".", "\\") + "\\" + projectInfo.getArtifactId();
		String basePackageValue = projectInfo.getGroupId() + "." + projectInfo.getArtifactId();
		String resourcesPath = "\\src\\main\\resources";
		String businessPath =  rootPath + javaPath + basePackagePath;
		String testTotlePath =  rootPath + testPath + basePackagePath;
		
		FileUtils.createDir(rootPath + resourcesPath);
		FileUtils.createDir(rootPath);
		FileUtils.createDir(rootPath + javaPath);
		FileUtils.createDir(rootPath + javaPath + basePackagePath + "\\config");
		FileUtils.createDir(rootPath + javaPath + basePackagePath + "\\common");
		FileUtils.createDir(rootPath + testPath);
		FileUtils.createDir(rootPath + javaPath + "\\" + basePackagePath);
		
		HashMap<String, String> pathData = new HashMap<String, String>();
		pathData.put("businessPath", businessPath);
		pathData.put("testTotlePath", testTotlePath);
		pathData.put("rootPath", rootPath);
		pathData.put("javaPath", javaPath);
		pathData.put("testPath", testPath);
		pathData.put("basePackagePath", basePackagePath);
		pathData.put("basePackageValue", basePackageValue);
		pathData.put("resourcesPath", resourcesPath);
		genCodeInfo.setFloderPathData(pathData);
	}

	private void createBaseFile(GenCodeInfo genCodeInfo) {
		HashMap<String, String> floderPathData = genCodeInfo.getFloderPathData();
		String path = floderPathData.get("rootPath") + floderPathData.get("javaPath") + floderPathData.get("basePackagePath");
		createYmlFile(genCodeInfo);
		createSwaggerFile(genCodeInfo);
		createAny(genCodeInfo, floderPathData.get("rootPath") + "\\pom.xml", "pom.ftl");
		createAny(genCodeInfo, path + "\\Application.java", "application.ftl");
		createAny(genCodeInfo, path + "\\config\\RedisConfig.java", "redisconfig.ftl");
		createAny(genCodeInfo, path + "\\common\\RedisUtils.java", "redisutils.ftl");
		createAny(genCodeInfo, path + "\\common\\BaseResp.java", "baseresp.ftl");
		createAny(genCodeInfo, path + "\\common\\BusinessException.java", "businessexception.ftl");
		createAny(genCodeInfo, path + "\\common\\BusinessUtils.java", "businessutils.ftl");
		createAny(genCodeInfo, path + "\\common\\ByteUtil.java", "byteutil.ftl");
		createAny(genCodeInfo, path + "\\common\\CommonUtil.java", "commonutil.ftl");
		createAny(genCodeInfo, path + "\\common\\CreatePassWord.java", "createpassword.ftl");
		createAny(genCodeInfo, path + "\\common\\DataCorrectCheck.java", "datacorrectcheck.ftl");
		createAny(genCodeInfo, path + "\\common\\DateUtil.java", "dateutil.ftl");
		createAny(genCodeInfo, path + "\\common\\GenId.java", "genid.ftl");
		createAny(genCodeInfo, path + "\\common\\GlobalExceptionHandler.java", "globalexceptionhandler.ftl");
		createAny(genCodeInfo, path + "\\common\\HttpSend.java", "httpsend.ftl");
		createAny(genCodeInfo, path + "\\common\\JsonProcess.java", "jsonprocess.ftl");
		createAny(genCodeInfo, path + "\\common\\JwtCheckResUserInfo.java", "jwtcheckresuserinfo.ftl");
		createAny(genCodeInfo, path + "\\common\\JwtProcess.java", "jwtprocess.ftl");
		createAny(genCodeInfo, path + "\\common\\OnlyId.java", "onlyid.ftl");
		createAny(genCodeInfo, path + "\\common\\OrderItem.java", "orderitem.ftl");
		createAny(genCodeInfo, path + "\\common\\PageParameter.java", "pageparameter.ftl");
		createAny(genCodeInfo, path + "\\common\\ResultCodeEnum.java", "resultcodeenum.ftl");
		createAny(genCodeInfo, path + "\\common\\SnowId.java", "snowid.ftl");
		createAny(genCodeInfo, path + "\\common\\StringUtil.java", "stringutil.ftl");
		createAny(genCodeInfo, path + "\\common\\UpStateVo.java", "upstatevo.ftl");
	}
	
	private void createAny(GenCodeInfo genCodeInfo, String fileInfo, String tempName) {
		try {
			Configuration cfg = FreemarkerUtils.initFreemarkerConfiguration(genCodeInfo.getFtlFilePath());
			Map<String, Object> data = genCodeInfo.getTempData();
			File file = new File(fileInfo);
		
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
	        }
	        cfg.getTemplate(tempName).process(data, new FileWriter(file));
	        logger.info(fileInfo + "生成成功!");
	    } catch (Exception e) {
	        throw new RuntimeException(fileInfo + "生成异常!", e);
	    }
	}
	
	private void createYmlFile(GenCodeInfo genCodeInfo) {
		HashMap<String, String> floderPathData = genCodeInfo.getFloderPathData();
		String resourcesPath = floderPathData.get("rootPath") + floderPathData.get("resourcesPath");
		try {
			Configuration cfg = FreemarkerUtils.initFreemarkerConfiguration(genCodeInfo.getFtlFilePath());
			Map<String, Object> data = genCodeInfo.getTempData();
			File parntymlFile = new File(resourcesPath + "\\application.yml");
			if (!parntymlFile.getParentFile().exists()) {
				parntymlFile.getParentFile().mkdirs();
	        }
	        cfg.getTemplate("parntyml.ftl").process(data, new FileWriter(parntymlFile));
	        logger.info("总yml 生成成功!");

			File devYmlFile = new File(resourcesPath + "\\application-dev.yml");
			if (!devYmlFile.getParentFile().exists()) {
				devYmlFile.getParentFile().mkdirs();
	        }
			data.put("runType", "dev");
			data.put("eurekaIp", genCodeInfo.getEurekaIp().get("dev"));
			data.put("eurekaPort", genCodeInfo.getEurekaPort().get("dev"));
	        cfg.getTemplate("yml.ftl").process(data, new FileWriter(devYmlFile));
	        logger.info("开发环境yml生成成功!");
	        File testYmlFile = new File(resourcesPath + "\\application-test.yml");
			if (!testYmlFile.getParentFile().exists()) {
				testYmlFile.getParentFile().mkdirs();
	        }
			data.put("runType", "test");
			data.put("eurekaIp", genCodeInfo.getEurekaIp().get("test"));
			data.put("eurekaPort", genCodeInfo.getEurekaPort().get("test"));
	        cfg.getTemplate("yml.ftl").process(data, new FileWriter(testYmlFile));
	        logger.info("测试环境yml生成成功!");
	        File prodYmlFile = new File(resourcesPath + "\\application-prod.yml");
			if (!prodYmlFile.getParentFile().exists()) {
				prodYmlFile.getParentFile().mkdirs();
	        }
			data.put("runType", "prod");
			data.put("eurekaIp", genCodeInfo.getEurekaIp().get("prod"));
			data.put("eurekaPort", genCodeInfo.getEurekaPort().get("prod"));
	        cfg.getTemplate("yml.ftl").process(data, new FileWriter(prodYmlFile));
	        logger.info("生产环境yml生成成功!");
	    } catch (Exception e) {
	        throw new RuntimeException("yml生成异常!", e);
	    }
	}
	
	private void createSwaggerFile(GenCodeInfo genCodeInfo) {
		HashMap<String, String> floderPathData = genCodeInfo.getFloderPathData();
		String path = floderPathData.get("rootPath") + floderPathData.get("javaPath") + floderPathData.get("basePackagePath");
		try {
			Configuration cfg = FreemarkerUtils.initFreemarkerConfiguration(genCodeInfo.getFtlFilePath());
			Map<String, Object> data = genCodeInfo.getTempData();
			data.put("businessComment", genCodeInfo.getProjectInfo().getDescription());
			File file = new File(path + "\\Swagger.java");
		
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
	        }
	        cfg.getTemplate("swagger.ftl").process(data, new FileWriter(file));
	        logger.info("swagger生成成功!");
	    } catch (Exception e) {
	        throw new RuntimeException("swagger生成异常!", e);
	    }
	}
	
	public void createFun(GenCodeInfo genCodeInfo, String templatePath, String filePathAndName, String templateName) {
		try {
			Configuration cfg = FreemarkerUtils.initFreemarkerConfiguration(templatePath);
			Map<String, Object> data = genCodeInfo.getTempData();
			data.put("tableComment", genCodeInfo.getTableComment());
			File file = new File(filePathAndName);
			if(!file.exists()) {
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				cfg.getTemplate(templateName + ".ftl").process(data, new FileWriter(file));
				logger.info(genCodeInfo.getModelName() + templateName + ".java 生成成功!");
			}else {
            	String needKeepStrH = getCreateBeforHead(filePathAndName);
            	String needKeepStrE = getCreateBeforEnd(filePathAndName);
            	cfg.getTemplate(templateName + ".ftl").process(data, new FileWriter(file));
            	String needKeepStringNew = getCreateAfter(filePathAndName);
            	File targetFile = new File(filePathAndName);
            	String fileContent = needKeepStrH + needKeepStringNew + needKeepStrE;
            	writeFile(targetFile, fileContent, "UTF-8");
				logger.info(genCodeInfo.getModelName() + templateName + ".java 存在，进行新老代码段拼接重新生成!");
			}
	    } catch (Exception e) {
	        throw new RuntimeException(templateName + ".java 生成失败!", e);
	    }
	}
	
	/**
	 * 获取代码生成前头的代码
	 * <p>Title: getCreateBefor</p>  
	 * <p>Description: </p>  
	 * @param filePathAndName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String getCreateBeforHead(String filePathAndName) throws FileNotFoundException, IOException {
		FileReader fr = new FileReader(filePathAndName);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		String needKeepString = "";
		boolean needKeepStrMark = true;
		while ((line = br.readLine()) != null) {
		    if(needKeepStrMark) {
		    	needKeepString = needKeepString + line + "\n";
		    }
		    if ("/*代码分界head TODO*/".equals(line.trim())) {
		    	needKeepStrMark = false;
		    }
		}
		return needKeepString;
	}
	
	/**
	 * 获取代码生成前尾的代码
	 * <p>Title: getCreateBefor</p>  
	 * <p>Description: </p>  
	 * @param filePathAndName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String getCreateBeforEnd(String filePathAndName) throws FileNotFoundException, IOException {
		FileReader fr = new FileReader(filePathAndName);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		String needKeepString = "\n";
		boolean needKeepStrMark = false;
		while ((line = br.readLine()) != null) {
		    if(needKeepStrMark) {
		    	needKeepString = needKeepString + line + "\n";
		    }
		    if ("/*代码分界end TODO*/".equals(line.trim())) {
		    	needKeepStrMark = true;
		    }
		}
		return needKeepString;
	}
	
	/**
	 * 获取代码生成后的代码
	 * <p>Title: getCreateAfter</p>  
	 * <p>Description: </p>  
	 * @param filePathAndName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String getCreateAfter(String filePathAndName) throws FileNotFoundException, IOException {
		FileReader frNew = new FileReader(filePathAndName);
		BufferedReader brNew = new BufferedReader(frNew);
		String lineNew = null;
		String needKeepStringNew = "\n";
		boolean needKeepStrMarkNew = false;
		while ((lineNew = brNew.readLine()) != null) {
		    if(needKeepStrMarkNew) {
		    	needKeepStringNew = needKeepStringNew + lineNew + "\n";
		    }
		    if ("/*代码分界head TODO*/".equals(lineNew.trim())) {
		    	needKeepStrMarkNew = true;
		    }
		    if ("/*代码分界end TODO*/".equals(lineNew.trim())) {
		    	needKeepStrMarkNew = false;
		    }
		}
		return needKeepStringNew;
	}
	
	/**
	 * 根据文件路径、内容、字符集生成文件
	 * <p>Title: writeFile</p>  
	 * <p>Description: </p>  
	 * @param filePath 
	 * @param content
	 * @param fileEncoding
	 * @throws IOException
	 */
	public static void writeFile(File filePath, String content, String fileEncoding) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath, false);
        OutputStreamWriter osw;
        if (fileEncoding == null) {
            osw = new OutputStreamWriter(fos);
        } else {
            osw = new OutputStreamWriter(fos, fileEncoding);
        }
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(content);
        bw.close();
    }
	
	/**
	 * 生成test文件
	 * @param genCodeInfo
	 */
	private void createTest(GenCodeInfo genCodeInfo) {
		Map<String, Object> tempData = genCodeInfo.getTempData();
		String templatePath = genCodeInfo.getFtlFilePath();
		String filePathAndName = genCodeInfo.getFloderPathData().get("testTotlePath") + "\\" + tempData.get("ddd_name")
				+ "\\" + tempData.get("modelNameUpperCamel") + "Test.java";
		String templateName = "test";
		FileUtils.createFun(genCodeInfo, templatePath, filePathAndName, templateName);
	}
	
	/**
	 * 生成Service文件
	 * @param genCodeInfo
	 */
	private void createService(GenCodeInfo genCodeInfo) {
		Map<String, Object> tempData = genCodeInfo.getTempData();
		HashMap<String, String> floderPathData = genCodeInfo.getFloderPathData();
		String templatePath = genCodeInfo.getFtlFilePath();
		String filePathAndName = floderPathData.get("businessPath") + "\\" + tempData.get("ddd_name") + "\\domain\\service\\" + tempData.get("modelNameUpperCamel") + "Service.java";
		String templateName = "service";
		FileUtils.createFun(genCodeInfo, templatePath, filePathAndName, templateName);
	}
	
	/**
	 * 生成MapperJava文件
	 * @param genCodeInfo
	 */
	private void createMapperJava(GenCodeInfo genCodeInfo) {
		Map<String, Object> tempData = genCodeInfo.getTempData();
		HashMap<String, String> floderPathData = genCodeInfo.getFloderPathData();
		String templatePath = genCodeInfo.getFtlFilePath();
		String filePathAndName = floderPathData.get("businessPath") + "\\" + tempData.get("ddd_name") + "\\domain\\mapper\\" + tempData.get("modelNameUpperCamel") + "Mapper.java";
		String templateName = "mapper";
		FileUtils.createFun(genCodeInfo, templatePath, filePathAndName, templateName);
	}
	
	
	/**
	 * 生成Controller文件
	 * @param genCodeInfo
	 */
	private void createController(GenCodeInfo genCodeInfo) {
		Map<String, Object> tempData = genCodeInfo.getTempData();
		HashMap<String, String> floderPathData = genCodeInfo.getFloderPathData();
		String templatePath = genCodeInfo.getFtlFilePath();
		String filePathAndName = floderPathData.get("businessPath") + "\\" + tempData.get("ddd_name") + "\\interfaces\\" + tempData.get("modelNameUpperCamel") + "Controller.java";
		String templateName = "controller";
		FileUtils.createFun(genCodeInfo, templatePath, filePathAndName, templateName);
	}
	
	/**
	 * 生成imp文件
	 * @param genCodeInfo
	 */
	private void createImp(GenCodeInfo genCodeInfo) {
		Map<String, Object> tempData = genCodeInfo.getTempData();
		HashMap<String, String> floderPathData = genCodeInfo.getFloderPathData();
		String templatePath = genCodeInfo.getFtlFilePath();
		String filePathAndName = floderPathData.get("businessPath") + "\\" + tempData.get("ddd_name") + "\\domain\\service\\" + tempData.get("modelNameUpperCamel") + "Imp.java";
		String templateName = "imp";
		FileUtils.createFun(genCodeInfo, templatePath, filePathAndName, templateName);
	}
	
	/**
	 * 生成MybatisPlusConfig文件
	 * @param genCodeInfo
	 */
	private void createMybatisPlusConfig(GenCodeInfo genCodeInfo) {
		String templatePath = genCodeInfo.getFtlFilePath();
		String filePathAndName = genCodeInfo.getFloderPathData().get("businessPath") + "\\config\\" + "MybatisPlusConfig.java";
		String templateName = "mybatisplusconfig";
		FileUtils.createFun(genCodeInfo, templatePath, filePathAndName, templateName);
	}
	
	/**
	 * 生成MyMetaObjectHandler文件
	 * @param genCodeInfo
	 */
	private void createMyMetaObjectHandler(GenCodeInfo genCodeInfo) {
		String templatePath = genCodeInfo.getFtlFilePath();
		String filePathAndName = genCodeInfo.getFloderPathData().get("businessPath") + "\\config\\" + "MyMetaObjectHandler.java";
		String templateName = "metaobjecthandler";
		FileUtils.createFun(genCodeInfo, templatePath, filePathAndName, templateName);
	}
}
