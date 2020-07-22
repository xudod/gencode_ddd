package ${basePackageValue}.${ddd_name}.${entityPackageName}.po;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;


@ApiModel(value = "表的基本信息")
public class ${modelNameUpperCamel} {

<#list columnList as column>
	<#if column.nameUpperCamel == "status" || column.nameUpperCamel == "version" || column.nameUpperCamel == "deleted" || column.nameUpperCamel == "createTime" || column.nameUpperCamel == "modifyTime">
	<#if column.nameUpperCamel == "status">
    @ApiModelProperty(example = "是否可用", hidden = true, position = ${column_index})
    @TableField(value = "status", fill = FieldFill.INSERT)
    private String status;
	<#elseif column.nameUpperCamel == "version">
    @ApiModelProperty(example = "乐观锁", hidden = true, position = ${column_index})
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    private Integer version;
	<#elseif column.nameUpperCamel == "deleted">
    @ApiModelProperty(example = "删除状态", hidden = true, position = ${column_index})
    @TableLogic
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    private String deleted;
	<#elseif column.nameUpperCamel == "createTime">
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(example = "创建时间", hidden = true, position = ${column_index})
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
	<#elseif column.nameUpperCamel == "modifyTime">
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(example = "修改时间", hidden = true, position = ${column_index})
    @TableField(value = "modify_time", fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
	</#if>
	<#else>
	@ApiModelProperty(example = "${column.pageTitle?if_exists}", position = ${column_index})
	<#if column.columnType == "varchar">
	private String ${column.nameUpperCamel};
	<#elseif column.columnType == "char">
	private String ${column.nameUpperCamel};
	<#elseif column.columnType == "int">
	private Integer ${column.nameUpperCamel};
	</#if>
	</#if>
	
</#list>

<#list columnList as column>
	<#if column.columnType == "varchar" || column.columnType == "char">
	/**
     * ${column.pageTitle?if_exists}
     * @return ${column.nameUpperCamel} ${column.pageTitle?if_exists}
     */
    public String get${column.nameHeadUpperCamel}() {
        return ${column.nameUpperCamel};
    }

    /**
     * ${column.pageTitle?if_exists}
     * @param ${column.nameUpperCamel} ${column.pageTitle?if_exists}
     */
    public void set${column.nameHeadUpperCamel}(String ${column.nameUpperCamel}) {
        this.${column.nameUpperCamel} = ${column.nameUpperCamel};
    }
	<#elseif column.columnType == "int">
	/**
     * ${column.pageTitle?if_exists}
     * @return ${column.nameUpperCamel} ${column.pageTitle?if_exists}
     */
    public Integer get${column.nameHeadUpperCamel}() {
        return ${column.nameUpperCamel};
    }

    /**
     * ${column.pageTitle?if_exists}
     * @param ${column.nameUpperCamel} ${column.pageTitle?if_exists}
     */
    public void set${column.nameHeadUpperCamel}(Integer ${column.nameUpperCamel}) {
        this.${column.nameUpperCamel} = ${column.nameUpperCamel};
    }
	<#elseif column.columnType == "timestamp">
	/**
     * ${column.pageTitle?if_exists}
     * @return ${column.nameUpperCamel} ${column.pageTitle?if_exists}
     */
    public Date get${column.nameHeadUpperCamel}() {
        return ${column.nameUpperCamel};
    }

    /**
     * ${column.pageTitle?if_exists}
     * @param ${column.nameUpperCamel} ${column.pageTitle?if_exists}
     */
    public void set${column.nameHeadUpperCamel}(Date ${column.nameUpperCamel}) {
        this.${column.nameUpperCamel} = ${column.nameUpperCamel};
    }
	</#if>
</#list>
	
}