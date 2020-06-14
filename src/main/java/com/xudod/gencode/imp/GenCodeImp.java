package com.xudod.gencode.imp;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.IntrospectedTable.TargetRuntime;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.JavaTypeResolverConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.CaseFormat;
import com.xudod.gen_code.domain_info.domain.entity.po.DomainInfo;
import com.xudod.gen_code.domain_info.domain.service.DomainInfoService;
import com.xudod.gen_code.project_info.domain.entity.po.ProjectInfo;
import com.xudod.gen_code.project_info.domain.service.ProjectInfoService;
import com.xudod.gen_code.table_base_info.domain.entity.po.TableBaseInfo;
import com.xudod.gen_code.table_base_info.domain.service.TableBaseInfoService;
import com.xudod.gencode.data.GenCodeInfo;
import com.xudod.gencode.mybatisImp.MyMyBatisGenerator;
import com.xudod.gencode.service.GenCodeService;
import com.xudod.gencode.utils.FileUtils;
import com.xudod.gencode.utils.FreemarkerUtils;
import com.xudod.gencode.utils.StringUtils;

@Service
public class GenCodeImp implements GenCodeService {
	
	@Autowired
	private ProjectInfoService projectInfoService;
	
	@Autowired
	private DomainInfoService domainInfoService;
	
	@Autowired
	private TableBaseInfoService tableBaseInfoService;

	private Logger logger = LoggerFactory.getLogger(GenCodeImp.class);
	
	/**
	 * 考虑只生成其中一个领域的情况。
	 */
	@Override
	public boolean genCodeAllProject(String id) {
		GenCodeInfo genCodeInfo = new GenCodeInfo(id);
		//获取项目和微服务信息
		getProjectInfo(genCodeInfo);
		getDomainInfo(genCodeInfo);
		createEurekaIpInfo(genCodeInfo);
		createEurekaPortInfo(genCodeInfo);
		createTableInfo(genCodeInfo);
		// 模板文件文件夹位置
		createFtlFilePath(genCodeInfo);
		createDatabaseUrl(genCodeInfo);
		createFloderInfo(genCodeInfo);
		createTemplate(genCodeInfo);
		
		createBaseFile(genCodeInfo);
		
		createMapperAndBean(genCodeInfo);
		
		createMybatisPlusConfig(genCodeInfo);
		createMyMetaObjectHandler(genCodeInfo);
		return false;
	}
	
	private void createTableInfo(GenCodeInfo genCodeInfo) {
		//用key作为领域的包名，其中列表包含所有该领域的表
		HashMap<String, ArrayList<String>> hashMap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> tableName = new ArrayList<String>();
		List<DomainInfo> domainInfoList = genCodeInfo.getDomainInfoList();
		for (DomainInfo domainInfo : domainInfoList) {
			tableName = new ArrayList<String>();
			String domainId = domainInfo.getId();
			List<TableBaseInfo> tableByDomainId = tableBaseInfoService.getTableByDomainId(domainId);
//			String[] split = dbTableList.split(",");
			for (int i = 0; i < tableByDomainId.size(); i++) {
				tableName.add(tableByDomainId.get(i).getTableName());
			}
			hashMap.put(domainInfo.getDbName(), tableName);
		}
		// TODO 这里要继续改造
//		tableName = new ArrayList<String>();
//		tableName.add("table_base_info");
//		hashMap.put("table_base_info", tableName);
    	genCodeInfo.setTableList(hashMap);
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
	
	
	/**
	 * 生成test文件
	 * @param genCodeInfo
	 */
	private void createTest(GenCodeInfo genCodeInfo) {
		String templatePath = genCodeInfo.getFtlFilePath();
		String filePathAndName = genCodeInfo.getFloderPathData().get("testTotlePath") + "\\" + genCodeInfo.getFloderPathData().get("ddd_name")
				+ "\\" + genCodeInfo.getModelName() + "Test.java";
		String templateName = "test";
		FileUtils.createFun(genCodeInfo, templatePath, filePathAndName, templateName);
	}
	
	/**
	 * 生成Service文件
	 * @param genCodeInfo
	 */
	private void createService(GenCodeInfo genCodeInfo) {
		String templatePath = genCodeInfo.getFtlFilePath();
		String filePathAndName = genCodeInfo.getFloderPathData().get("domain") + "\\service\\" + genCodeInfo.getModelName() + "Service.java";
		String templateName = "service";
		FileUtils.createFun(genCodeInfo, templatePath, filePathAndName, templateName);
	}
	
	
	/**
	 * 生成Controller文件
	 * @param genCodeInfo
	 */
	private void createController(GenCodeInfo genCodeInfo) {
		String templatePath = genCodeInfo.getFtlFilePath();
		String filePathAndName = genCodeInfo.getFloderPathData().get("interfacesPath") + "\\" + genCodeInfo.getModelName() + "Controller.java";
		String templateName = "controller";
		FileUtils.createFun(genCodeInfo, templatePath, filePathAndName, templateName);
	}
	
	/**
	 * 生成imp文件
	 * @param genCodeInfo
	 */
	private void createImp(GenCodeInfo genCodeInfo) {
		String templatePath = genCodeInfo.getFtlFilePath();
		String filePathAndName = genCodeInfo.getFloderPathData().get("domain") + "\\service\\" + genCodeInfo.getModelName() + "Imp.java";
		String templateName = "imp";
		FileUtils.createFun(genCodeInfo, templatePath, filePathAndName, templateName);
	}

	private void createMapperAndBean(GenCodeInfo genCodeInfo) {
		HashMap<String, String> floderPathData = genCodeInfo.getFloderPathData();
		String domainPath = "";
		String interfacesPath = "";
		HashMap<String, ArrayList<String>> tablelMap = genCodeInfo.getTableList();
		for (Entry<String, ArrayList<String>> entry : tablelMap.entrySet()) {
			String businessPath = floderPathData.get("businessPath");
			domainPath = businessPath + "\\" + entry.getKey() + "\\domain";
			interfacesPath = businessPath + "\\" + entry.getKey() + "\\interfaces";
			creatBusinessDir(domainPath, interfacesPath);
			
			initContext(genCodeInfo);
			String ddd_name = entry.getKey();
			String filePath = floderPathData.get("rootPath") + floderPathData.get("javaPath")
					+ floderPathData.get("basePackagePath") + "\\" + ddd_name;
			String testTotlePath = floderPathData.get("testTotlePath");
			floderPathData.put("testTotlePath", testTotlePath);
			
			floderPathData.put("ddd_name", ddd_name);
			floderPathData.put("filePath", filePath);
			floderPathData.put("interfacesPath", interfacesPath);
			floderPathData.put("domain", domainPath);
			genCodeInfo.setFloderPathData(floderPathData);
			
			ArrayList<String> tablelList = entry.getValue();
			for (String tableName : tablelList) {
				Map<String, Object> tempData = genCodeInfo.getTempData();
				//根据表名处理一些模板中要用的变量
				genCodeInfo.setTableName(tableName);
				String modelName = StringUtils.tableNameConvertUpperCamel(tableName);
				genCodeInfo.setModelName(modelName);
				tempData.put("tableName", genCodeInfo.getTableName());
				tempData.put("baseRequestMapping", StringUtils.toLowerCaseFirstOne(modelName));
				tempData.put("modelNameUpperCamel", modelName);
				tempData.put("Vo", modelName + "Vo");
				tempData.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelName));
				tempData.put("ddd_name", ddd_name);
				genCodeInfo.setTempData(tempData);
				genCodeInfo = generateFromJavaConfig(genCodeInfo);
				
				createImp(genCodeInfo);
				createController(genCodeInfo);
				createService(genCodeInfo);
				createTest(genCodeInfo);
			}
		}
	}

	private void creatBusinessDir(String domainPath, String interfacesPath) {
		FileUtils.createDir(domainPath + "\\entity");
		FileUtils.createDir(domainPath + "\\event");
		FileUtils.createDir(domainPath + "\\mapper");
		FileUtils.createDir(domainPath + "\\entity\\po");
		FileUtils.createDir(domainPath + "\\entity\\bo");
		FileUtils.createDir(domainPath + "\\service");
		FileUtils.createDir(interfacesPath + "\\manager");
		FileUtils.createDir(interfacesPath + "\\vo");
	}
	
	private void initContext(GenCodeInfo genCodeInfo) {
		ProjectInfo projectInfo = genCodeInfo.getProjectInfo();
		Context context = new Context(ModelType.FLAT);

    	//这里的设置用来调用自己的方法，生成实体类的注释
        CommentGeneratorConfiguration comm = new CommentGeneratorConfiguration();
        comm.setConfigurationType("com.xudod.gencode.mybatisImp.MyCommentGenerator");
        context.setCommentGeneratorConfiguration(comm);
    	
        context.setId("Potato");
        context.setTargetRuntime(TargetRuntime.MYBATIS3.name());
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

        Properties p = new Properties();
        // 生成时是否需要注释
        p.setProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS, "false");
        p.setProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE, "true");
        p.setProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS, "true");
        context.getCommentGenerator().addConfigurationProperties(p);
        JavaTypeResolverConfiguration javaTypeResolverConfiguration = new JavaTypeResolverConfiguration();
        javaTypeResolverConfiguration.addProperty(PropertyRegistry.TYPE_RESOLVER_FORCE_BIG_DECIMALS,"false");
        context.setJavaTypeResolverConfiguration(javaTypeResolverConfiguration);

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(genCodeInfo.getDatabaseUrl());
        jdbcConnectionConfiguration.setUserId(projectInfo.getDbUser());
        jdbcConnectionConfiguration.setPassword(projectInfo.getDbPassword());
        jdbcConnectionConfiguration.setDriverClass(genCodeInfo.getTempData().get("databaseDriver") + "");
        jdbcConnectionConfiguration.addProperty("remarksReporting", "true");
//        <jdbcConnection driverClass="oracle.jdbc.driver.OracleDriver"
//        connectionURL="jdbc:oracle:thin:@192.168.56.7:1521/kqpdb1"
//        userId="kqadmin" password="123245964">
//        <!-- 针对oracle数据库 -->
//        <property name="remarksReporting" value="true"></property>
//    </jdbcConnection>
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(genCodeInfo.getFloderPathData().get("rootPath") + genCodeInfo.getFloderPathData().get("resourcesPath"));
        sqlMapGeneratorConfiguration.setTargetPackage("mapper");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        genCodeInfo.setContext(context);
	}

    private GenCodeInfo generateFromJavaConfig(GenCodeInfo genCodeInfo) {
    	List<String> warnings = new ArrayList<String>();
		try {
			Context initConfig = initConfig(genCodeInfo);
			Configuration cfg = new Configuration();
			cfg.addContext(initConfig);
			cfg.validate();
			DefaultShellCallback callback = new DefaultShellCallback(true);
			MyMyBatisGenerator generator = new MyMyBatisGenerator(cfg, callback, warnings, genCodeInfo);
			genCodeInfo = generator.generate(null);
			getPrimaryKeys(cfg);
			if (generator == null || generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
				throw new RuntimeException("Model 和  Mapper 生成失败, warnings: " + warnings);
			}else {
				logger.info(genCodeInfo.getModelName() + "Model 和  Mapper 生成成功!");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			throw new RuntimeException("Model 和  Mapper 生成失败, warnings: " + warnings);
		}
    	return genCodeInfo;
    }
    
    /**
     * Get the primary keys of current table, which will be used for cache
     * @param cfg
     */
    private void getPrimaryKeys(Configuration cfg) {
        try {
             //当前生成的表的主列
            List<String> primaryColumnsList = new ArrayList<String>();
            //当前生成的表的所有列。
            List<String> allColumnsList = new ArrayList<String>();
            Context context = cfg.getContexts().get(0);
            Field field = context.getClass().getDeclaredField("introspectedTables");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            List<IntrospectedTable> list = (List<IntrospectedTable>)field.get(context);
            IntrospectedTable introspectedTable = list.get(0);
            List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
            for(IntrospectedColumn column :allColumns) {
                allColumnsList.add(column.getActualColumnName());
            }
            List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
            if(primaryKeyColumns!=null && primaryKeyColumns.size()>0) {
                for(IntrospectedColumn column :primaryKeyColumns) {
                    primaryColumnsList.add(column.getActualColumnName());
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * 完善初始化环境
     * 
     * @param tableName 表名
     * @param modelName 自定义实体类名, 为null则默认将表名下划线转成大驼峰形式
     */
    private Context initConfig(GenCodeInfo genCodeInfo) {
    	HashMap<String, String> floderPathData = genCodeInfo.getFloderPathData();
    	String javaPath = floderPathData.get("rootPath") + floderPathData.get("javaPath");
    	String basePackageValue = floderPathData.get("basePackageValue");
    	String ddd_name = floderPathData.get("ddd_name");
        Context context = genCodeInfo.getContext();
        try {
            JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
            javaModelGeneratorConfiguration.setTargetProject(javaPath);
            javaModelGeneratorConfiguration.setTargetPackage(basePackageValue + "." + ddd_name + ".domain.entity.po");
            context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
            
            JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
            javaClientGeneratorConfiguration.setTargetProject(javaPath);
            javaClientGeneratorConfiguration.setTargetPackage(basePackageValue + "." + ddd_name + ".domain.mapper");
            // javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
            javaClientGeneratorConfiguration.setConfigurationType("org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator");
            context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

            TableConfiguration tableConfiguration = new TableConfiguration(context);
            tableConfiguration.setTableName(genCodeInfo.getTableName());
            tableConfiguration.setSchema("");
            tableConfiguration.setDomainObjectName(genCodeInfo.getModelName());
            tableConfiguration.setCountByExampleStatementEnabled(false);
            tableConfiguration.setDeleteByExampleStatementEnabled(false);
            tableConfiguration.setSelectByExampleStatementEnabled(false);
            tableConfiguration.setUpdateByExampleStatementEnabled(false);
            context.addTableConfiguration(tableConfiguration);
        } catch (Exception e) {
            throw new RuntimeException("ModelAndMapperGenerator 初始化环境异常!", e);
        }
        return context;
    }
    
	/***********************生成基础文件********************************/
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
			freemarker.template.Configuration cfg = FreemarkerUtils.initFreemarkerConfiguration(genCodeInfo.getFtlFilePath());
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
			freemarker.template.Configuration cfg = FreemarkerUtils.initFreemarkerConfiguration(genCodeInfo.getFtlFilePath());
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
			freemarker.template.Configuration cfg = FreemarkerUtils.initFreemarkerConfiguration(genCodeInfo.getFtlFilePath());
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
	
	/***********************生成基础文件********************************/

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
	 * 生成模板文件文件夹位置
	 * @param genCodeInfo
	 */
	private void createFtlFilePath(GenCodeInfo genCodeInfo) {
		genCodeInfo.setFtlFilePath(System.getProperty("user.dir") + "\\src\\main\\resources\\generator\\template");
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

	/**
	 * 根据项目id，获取所有领域/微服务信息列表
	 * @param genCodeInfo
	 */
	private void getDomainInfo(GenCodeInfo genCodeInfo) {
		List<DomainInfo> domainInfoList = domainInfoService.getDomainListByProjectId(genCodeInfo.getProjectId());
		genCodeInfo.setDomainInfoList(domainInfoList);
	}

	/**
	 * 根据项目id获取项目信息
	 * @param genCodeInfo
	 */
	private void getProjectInfo(GenCodeInfo genCodeInfo) {
		ProjectInfo projectInfo = projectInfoService.getbykey(genCodeInfo.getProjectId());
		genCodeInfo.setProjectInfo(projectInfo);
	}

}