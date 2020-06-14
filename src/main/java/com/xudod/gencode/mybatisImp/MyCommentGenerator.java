package com.xudod.gencode.mybatisImp;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
* <p>Title: MyCommentGenerator</p>  
* <p>Description: </p>  
* @author 许宇召
* @date 2019年9月20日
 */
public class MyCommentGenerator implements CommentGenerator{
	
    private Properties properties;
    
    private Properties systemPro;
    
    private boolean suppressDate;
    
    private boolean suppressAllComments;
    
    private String currentDateStr;
    
    private boolean addRemarkComments;

    public MyCommentGenerator() {
        super();
        properties = new Properties();
        systemPro = System.getProperties();
        suppressDate = false;
        suppressAllComments = false;
        addRemarkComments = false;
        currentDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    }

    public void addConfigurationProperties(Properties properties) {
    	this.properties.putAll(properties);
    	
    	suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
    	
    	suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
    	
    	addRemarkComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));
    }
    
	/**
	 * 生成model实体类的注释
	 */
	@Override
	public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		if (suppressAllComments  || !addRemarkComments) {
            return;
        }
        String remarks = introspectedTable.getRemarks();
        topLevelClass.addJavaDocLine("import javax.validation.constraints.NotEmpty;");
        topLevelClass.addJavaDocLine("import javax.validation.constraints.NotNull;");
        topLevelClass.addJavaDocLine("import com.fasterxml.jackson.annotation.JsonFormat;");
        topLevelClass.addJavaDocLine("import io.swagger.annotations.ApiModel;");
        topLevelClass.addJavaDocLine("import io.swagger.annotations.ApiModelProperty;");
        topLevelClass.addJavaDocLine("import com.baomidou.mybatisplus.annotation.FieldFill;");
        topLevelClass.addJavaDocLine("import com.baomidou.mybatisplus.annotation.TableField;");
        topLevelClass.addJavaDocLine("import com.baomidou.mybatisplus.annotation.TableLogic;");
        		topLevelClass.addJavaDocLine("import com.baomidou.mybatisplus.annotation.Version;");
        topLevelClass.addJavaDocLine("\n");
        if(null != remarks && !"".equals(remarks)) {
        	topLevelClass.addJavaDocLine("@ApiModel(value = \"" + remarks + "\")");
        }else {
        	topLevelClass.addJavaDocLine("@ApiModel(value = \"数据库中没有表的备注，请添加\")");
        }
	}
	
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        String remarks = introspectedColumn.getRemarks();
        if(null != remarks && !"".equals(remarks)) {
        	remarks = remarks.substring(1,remarks.indexOf("@"));
        }
        method.addJavaDocLine("/**");
        method.addJavaDocLine((" * " + remarks).replace("\n", " "));
        method.addJavaDocLine((" * @return " + introspectedColumn.getActualColumnName() + " " + remarks).replace("\n", " "));
        method.addJavaDocLine(" */");
    }

    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        String remarks = introspectedColumn.getRemarks();
        if(null != remarks && !"".equals(remarks)) {
        	remarks = remarks.substring(1,remarks.indexOf("@"));
        }
        method.addJavaDocLine("/**");
        method.addJavaDocLine((" * " + remarks).replace("\n", " "));
        Parameter parm = method.getParameters().get(0);
        method.addJavaDocLine((" * @param " + parm.getName() + " " + remarks).replace("\n", " "));
        method.addJavaDocLine(" */");
    }
    
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        // add no file level comments by default
    	//compilationUnit.addFileCommentLine("xuyuzhaobigman");
    	//compilationUnit.addStaticImport("xuyuzhaobigman");
    	//这里的配置可以同时加到mapper和modle，是整个文档的注释，不是类的注释	
        return;
    }

    /**
     * Adds a suitable comment to warn users that the element was generated, and
     * when it was generated.
     */
    public void addComment(XmlElement xmlElement) {
        return;
    }

    public void addRootComment(XmlElement rootElement) {
        // add no document level comments by default
        return;
    }

    /**
     * mapper.java文件中方法上的注释
     */
    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine(" *");
        StringBuilder sb = new StringBuilder();
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge");
        }
        String s = getDateString();
        if (s != null) {
            sb.append(' ');
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    /**
     * This method returns a formated date string to include in the Javadoc tag
     * and XML comments. You may return null if you do not want the date in
     * these documentation elements.
     * 
     * @return a string representing the current timestamp, or null
     */
    protected String getDateString() {
        String result = null;
        if (!suppressDate) {
            result = currentDateStr;
        }
        return result;
    }

    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerClass.addJavaDocLine("/**xuyuzhaoisbigman");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append(" ");
        sb.append(getDateString());
        innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
        innerClass.addJavaDocLine(" */");
    }

    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerEnum.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString().replace("\n", " "));
        innerEnum.addJavaDocLine(" */");
    }

    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        String type = field.getType().toString();
        if("java.util.Date".equals(type)) {
        	field.addJavaDocLine("@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\",timezone=\"GMT+8\")");
        }
        String a = introspectedColumn.getActualColumnName();
        String remarks = introspectedColumn.getRemarks();
        remarks = remarks.substring(1,remarks.indexOf("@"));
        boolean normalClumonJudge = true;
        if("user_id".equals(a)) {
        	field.addJavaDocLine("@ApiModelProperty(example = \"" + remarks + "\", hidden = true, position =  )");
        	normalClumonJudge = false;
        }if("org_id".equals(a)) {
        	field.addJavaDocLine("@ApiModelProperty(example = \"" + remarks + "\", hidden = true, position =  )");
        	normalClumonJudge = false;
        }if("app_id".equals(a)) {
        	field.addJavaDocLine("@ApiModelProperty(example = \"" + remarks + "\", hidden = true, position =  )");
        	normalClumonJudge = false;
        }if("client_type".equals(a)) {
        	field.addJavaDocLine("@ApiModelProperty(example = \"" + remarks + "\", hidden = true, position =  )");
        	normalClumonJudge = false;
        }if("inf_status".equals(a)) {
        	field.addJavaDocLine("@ApiModelProperty(example = \"" + remarks + "\", hidden = true, position =  )");
        	normalClumonJudge = false;
        }
        if("create_time".equals(a)) {
        	field.addJavaDocLine("@ApiModelProperty(example = \"" + remarks + "\", hidden = true, position =  )");
        	field.addJavaDocLine("@TableField(value = \"create_time\", fill = FieldFill.INSERT)");
        	normalClumonJudge = false;
        }
        if("modify_time".equals(a)) {
        	field.addJavaDocLine("@ApiModelProperty(example = \"" + remarks + "\", hidden = true, position =  )");
        	field.addJavaDocLine("@TableField(value = \"modify_time\", fill = FieldFill.INSERT_UPDATE)");
        	normalClumonJudge = false;
        }
        if("modify_by".equals(a) || "create_by".equals(a) || "version".equals(a) || "deleted".equals(a) || "public_able".equals(a)
        		  || "status".equals(a) || "shared_able".equals(a)) {
        	field.addJavaDocLine("@ApiModelProperty(example = \"" + remarks + "\", hidden = true, position =  )");
        	if("version".equals(a)) {
        		field.addJavaDocLine("@Version");
        		field.addJavaDocLine("@TableField(value = \"version\", fill = FieldFill.INSERT)");
        	}
        	if("deleted".equals(a)) {
        		field.addJavaDocLine("@TableLogic");
        		field.addJavaDocLine("@TableField(value = \"deleted\", fill = FieldFill.INSERT)");
        	}

        	if("public_able".equals(a)) {
        		field.addJavaDocLine("@TableField(value = \"public_able\", fill = FieldFill.INSERT)");
        	}
        	if("shared_able".equals(a)) {
        		field.addJavaDocLine("@TableField(value = \"shared_able\", fill = FieldFill.INSERT)");
        	}
        	if("status".equals(a)) {
        		field.addJavaDocLine("@TableField(value = \"status\", fill = FieldFill.INSERT)");
        	}
        	normalClumonJudge = false;	
        }
        
        
        if(normalClumonJudge) {
        	field.addJavaDocLine("@ApiModelProperty(example = \"" + remarks + "\", position =  )");
        }
        
        
    }

    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        field.addJavaDocLine("@ApiModelProperty(value = \"" + introspectedTable.getFullyQualifiedTable() + "\",required = true)");
    }

    /**
     * 没发现用处
     */
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerClass.addJavaDocLine("/**xuyuzhaoisbigman");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
        sb.setLength(0);
        sb.append(" * @author ");
        sb.append(systemPro.getProperty("user.name"));
        sb.append(" ");
        sb.append(currentDateStr);
        innerClass.addJavaDocLine(" */");
    }

	@Override
	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
		//这个是添加mapper.java文件的方法注释的，我们继承basemapper，不用实现
	}
}