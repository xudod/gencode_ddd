package com.xudod.gencode.mybatisImp;

import static org.mybatis.generator.internal.util.ClassloaderUtility.getCustomClassloader;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.codegen.RootClassInfo;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.NullProgressCallback;
import org.mybatis.generator.internal.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xudod.gencode.data.GenCodeInfo;
import com.xudod.gencode.utils.FileUtils;


public class MyMyBatisGenerator {

	protected static final Logger logger = LoggerFactory.getLogger(MyMyBatisGenerator.class);
	
    /** The configuration. */
    private Configuration configuration;

    /** The shell callback. */
    private ShellCallback shellCallback;

    /** The generated java files. */
    private List<GeneratedJavaFile> generatedJavaFiles;

    /** The generated xml files. */
    private List<GeneratedXmlFile> generatedXmlFiles;

    /** The warnings. */
    private List<String> warnings;

    /** The projects. */
    private Set<String> projects;
    
    /**
     * 添加mapper类的注释
     */
    private String mapperComment;
    
    private GenCodeInfo genCodeInfo;

    public MyMyBatisGenerator(Configuration configuration, ShellCallback shellCallback,
            List<String> warnings, GenCodeInfo genCodeInfo) throws InvalidConfigurationException {
        super();
        this.genCodeInfo  = genCodeInfo;
        
        if (configuration == null) {
            throw new IllegalArgumentException(getString("RuntimeError.2")); //$NON-NLS-1$
        } else {
            this.configuration = configuration;
        }

        if (shellCallback == null) {
            this.shellCallback = new DefaultShellCallback(false);
        } else {
            this.shellCallback = shellCallback;
        }

        if (warnings == null) {
            this.warnings = new ArrayList<String>();
        } else {
            this.warnings = warnings;
        }
        generatedJavaFiles = new ArrayList<GeneratedJavaFile>();
        generatedXmlFiles = new ArrayList<GeneratedXmlFile>();
        projects = new HashSet<String>();

        this.configuration.validate();
    }

    public GenCodeInfo generate(ProgressCallback callback) throws SQLException, IOException, InterruptedException {
        generate(callback, null, null, true);
        return this.genCodeInfo;
    }

    public void generate(ProgressCallback callback, Set<String> contextIds, Set<String> fullyQualifiedTableNames, boolean writeFiles) 
    		throws SQLException, IOException, InterruptedException {

        if (callback == null) {
            callback = new NullProgressCallback();
        }

        generatedJavaFiles.clear();
        generatedXmlFiles.clear();
        ObjectFactory.reset();
        RootClassInfo.reset();

        // calculate the contexts to run
        List<Context> contextsToRun;
        if (contextIds == null || contextIds.size() == 0) {
            contextsToRun = configuration.getContexts();
        } else {
            contextsToRun = new ArrayList<Context>();
            for (Context context : configuration.getContexts()) {
                if (contextIds.contains(context.getId())) {
                    contextsToRun.add(context);
                }
            }
        }

        // setup custom classloader if required
        if (configuration.getClassPathEntries().size() > 0) {
            ClassLoader classLoader = getCustomClassloader(configuration.getClassPathEntries());
            ObjectFactory.addExternalClassLoader(classLoader);
        }

        // now run the introspections...
        int totalSteps = 0;
        for (Context context : contextsToRun) {
            totalSteps += context.getIntrospectionSteps();
        }
        callback.introspectionStarted(totalSteps);

        //这一步将连接数据库，并将数据库表的信息写入context的
        //private List<IntrospectedTable> introspectedTables;这个类的私有变量中
        //从外部可以通过其他方法拿到，初步估计，应该是generateFiles方法
        //但是这个方法并没有返回结果，是将introspectedTables处理后，在生成其他变量
        for (Context context : contextsToRun) {
            context.introspectTables(callback, warnings, fullyQualifiedTableNames);
        }
        // now run the generates
        totalSteps = 0;
        for (Context context : contextsToRun) {
            totalSteps += context.getGenerationSteps();
        }
        callback.generationStarted(totalSteps);

        for (Context context : contextsToRun) {
            context.generateFiles(callback, generatedJavaFiles, generatedXmlFiles, warnings);
        }

        // now save the files
        if (writeFiles) {
            callback.saveStarted(generatedXmlFiles.size()
                + generatedJavaFiles.size());

            for (GeneratedXmlFile gxf : generatedXmlFiles) {
                projects.add(gxf.getTargetProject());
                writeGeneratedXmlFile(gxf, callback);
            }

            //generatedXmlFiles.clear();
            for (GeneratedJavaFile gjf : generatedJavaFiles) {
                projects.add(gjf.getTargetProject());
                writeGeneratedJavaFile(gjf, callback);
                createFrontInfo(gjf, callback);
            }
            //generatedJavaFiles.clear();

            for (String project : projects) {
                shellCallback.refreshProject(project);
            }
        }

        callback.done();
    }

    private void createFrontInfo(GeneratedJavaFile gjf, ProgressCallback callback) throws InterruptedException, IOException {
    	File targetFile;
        String source;
        try {
            File directory = shellCallback.getDirectory(gjf.getTargetProject(), gjf.getTargetPackage());
            targetFile = new File(directory, gjf.getFileName());
            if (targetFile.exists()) {
                if (shellCallback.isMergeSupported()) {
                    source = shellCallback.mergeJavaFile(gjf.getFormattedContent(), targetFile.getAbsolutePath(),
                            MergeConstants.OLD_ELEMENT_TAGS, gjf.getFileEncoding());
                } else if (shellCallback.isOverwriteEnabled()) {
                    source = gjf.getFormattedContent();
                    
                    warnings.add(getString("Warning.11", targetFile.getAbsolutePath()));
                } else {
                    source = gjf.getFormattedContent();
                    targetFile = getUniqueFileName(directory, gjf.getFileName());
                    warnings.add(getString("Warning.2", targetFile.getAbsolutePath())); //$NON-NLS-1$
                }
            } else {
            	source = gjf.getFormattedContent();
            }
        	if((genCodeInfo.getFloderPathData().get("basePackageValue") + "." + genCodeInfo.getFloderPathData().get("ddd_name")
        			+ ".domain.entity.po").equals(gjf.getTargetPackage())) {
                addBeanClassRemark(gjf, source);
        		int i = 1;
        		while (source.indexOf(", position =  )") > 0) {
        			source = source.replaceFirst(", position =  [)]", ", position = " + i + ")");
        			i++;
				}
        		callback.checkCancel();
        		callback.startTask(getString("Progress.15", targetFile.getName())); //$NON-NLS-1$
        		//FileUtils.writeFile(targetFile, source, gjf.getFileEncoding());
        	}
        	
        } catch (ShellException e) {
            warnings.add(e.getMessage());
        }
	}

	private void writeGeneratedJavaFile(GeneratedJavaFile gjf, ProgressCallback callback)
            throws InterruptedException, IOException {
        File targetFile;
        String source;
        try {
            File directory = shellCallback.getDirectory(gjf.getTargetProject(), gjf.getTargetPackage());
            targetFile = new File(directory, gjf.getFileName());
            if (targetFile.exists()) {
                if (shellCallback.isMergeSupported()) {
                    source = shellCallback.mergeJavaFile(gjf.getFormattedContent(), targetFile.getAbsolutePath(),
                            MergeConstants.OLD_ELEMENT_TAGS, gjf.getFileEncoding());
                } else if (shellCallback.isOverwriteEnabled()) {
                    source = gjf.getFormattedContent();
                    
                    warnings.add(getString("Warning.11", targetFile.getAbsolutePath()));
                } else {
                    source = gjf.getFormattedContent();
                    targetFile = getUniqueFileName(directory, gjf.getFileName());
                    warnings.add(getString("Warning.2", targetFile.getAbsolutePath())); //$NON-NLS-1$
                }
            } else {
            	source = gjf.getFormattedContent();
            }
            //生成mapper
        	if(gjf.getTargetPackage().equals(genCodeInfo.getFloderPathData().get("basePackageValue") + "."
        			+ genCodeInfo.getFloderPathData().get("ddd_name") + ".domain.mapper")
        			&& gjf.getFileName().equals(genCodeInfo.getModelName() + "Mapper.java")) {
        		try {
        			String templatePath = System.getProperty("user.dir") + "\\src\\main\\resources\\generator\\template";
        			String filePathAndName = genCodeInfo.getFloderPathData().get("filePath") + "\\domain\\mapper\\" + genCodeInfo.getModelName() + "Mapper.java";
        			String templateName = "mapper";
        			FileUtils.createFun(genCodeInfo, templatePath, filePathAndName, templateName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	        return;
        	}
        	//当处理data时将实体类的类注释保存到genCodeInfo中
        	
        	//为实体的属性进行排序，生成数据实体对象
        	//2020-04-13准备从这里截获前端需要的数据。并保存到数据库中。
        	if((genCodeInfo.getFloderPathData().get("basePackageValue") + "." + genCodeInfo.getFloderPathData().get("ddd_name")
        			+ ".domain.entity.po").equals(gjf.getTargetPackage())) {
                addBeanClassRemark(gjf, source);
        		int i = 1;
        		while (source.indexOf(", position =  )") > 0) {
        			source = source.replaceFirst(", position =  [)]", ", position = " + i + ")");
        			i++;
				}
        		callback.checkCancel();
        		callback.startTask(getString("Progress.15", targetFile.getName())); //$NON-NLS-1$
        		FileUtils.writeFile(targetFile, source, gjf.getFileEncoding());
        		
        		//生成vo实体类，等后边需要了再加
//        		directory = shellCallback.getDirectory(gjf.getTargetProject(), gjf.getTargetPackage().replace(".domain.entity.po", ".interfaces.vo"));
//        		targetFile = new File(directory, gjf.getFileName().replace(".java", "Vo.java"));
//        		source = source.replace(".domain.entity.po", ".interfaces.vo");
//        		source = source.replace(gjf.getFileName().replace(".java", ""), gjf.getFileName().replace(".java", "Vo"));
//        		if(source.indexOf("hidden") > 0) {
//        			source = source.substring(0, source.indexOf("hidden"));
//				}
//        		FileUtils.writeFile(targetFile, source, gjf.getFileEncoding());
        	}
        	
        } catch (ShellException e) {
            warnings.add(e.getMessage());
        }
    }

	private void addBeanClassRemark(GeneratedJavaFile gjf, String source) {
		if((genCodeInfo.getFloderPathData().get("basePackageValue") + "." + genCodeInfo.getFloderPathData().get("ddd_name")
				+ ".domain.entity.po").equals(gjf.getTargetPackage())) {
			mapperComment = source.substring(
					source.indexOf("@ApiModel(value = \"") + "@ApiModel(value = \"".length(), source.indexOf("\")"));
			genCodeInfo.setTableComment(null != mapperComment && !"".equals(mapperComment) ? mapperComment : "");
			//在create time和modify time上加标注自动填充标注
//			source.replaceAll("private\tDate\tcreateTime", "@TableField(value = \"create_time\", fill = FieldFill.INSERT)\n    private Date createTime;");
//			source.replace("private\tDate\tmodifyTime;", "@TableField(value = \"modify_time\", fill = FieldFill.INSERT_UPDATE)\n" 
//					+ StringUtils.SPACES_4 + "private Date modifyTime;");
//			
//			if(source.indexOf("/**") >= 0) {
//				
//			}
		}
	}

    private void writeGeneratedXmlFile(GeneratedXmlFile gxf, ProgressCallback callback)
            throws InterruptedException, IOException {
        File targetFile;
        String source;
        try {
            File directory = shellCallback.getDirectory(gxf.getTargetProject(), gxf.getTargetPackage());
            targetFile = new File(directory, gxf.getFileName());
            if (targetFile.exists()) {
            	source = gxf.getFormattedContent();
            	int indexOf = source.indexOf("</sql>");
            	source = source.substring(0, indexOf + "</sql>".length());
            	FileReader fr = new FileReader(directory + "\\" + gxf.getFileName());
            	@SuppressWarnings("resource")
				BufferedReader br = new BufferedReader(fr);
            	String line = null;
            	String needKeepString = "\n";
            	boolean needKeepStrMark = false;
            	while ((line = br.readLine()) != null) {
                    if(needKeepStrMark) {
                    	needKeepString = needKeepString + line + "\n";
                    }
                    if ("</sql>".equals(line.trim())) {
                    	needKeepStrMark = true;
                    }
                } 
            	source = source + needKeepString;
            } else {
                source = gxf.getFormattedContent();
                int indexOf = source.indexOf("</sql>");
            	source = source.substring(0, indexOf + "</sql>".length());
            	source = source + "\n</mapper>";
            }

            callback.checkCancel();
            callback.startTask(getString("Progress.15", targetFile.getName()));
            FileUtils.writeFile(targetFile, source, "UTF-8");
        } catch (ShellException e) {
            warnings.add(e.getMessage());
        }
    }

    private File getUniqueFileName(File directory, String fileName) {
        File answer = null;

        // try up to 1000 times to generate a unique file name
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 1000; i++) {
            sb.setLength(0);
            sb.append(fileName);
            sb.append('.');
            sb.append(i);

            File testFile = new File(directory, sb.toString());
            if (!testFile.exists()) {
                answer = testFile;
                break;
            }
        }
        if (answer == null) {
            throw new RuntimeException(getString("RuntimeError.3", directory.getAbsolutePath()));
        }
        return answer;
    }

    public List<GeneratedJavaFile> getGeneratedJavaFiles() {
        return generatedJavaFiles;
    }

    public List<GeneratedXmlFile> getGeneratedXmlFiles() {
        return generatedXmlFiles;
    }
}

