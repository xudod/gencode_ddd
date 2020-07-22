<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${basePackageValue}.${ddd_name}.${mapperPackageName}.${modelNameUpperCamel}Mapper">
  <resultMap id="BaseResultMap" type="${basePackageValue}.${ddd_name}.${entityPackageName}.po.${modelNameUpperCamel}">
    <#list columnList as column>
    <#if column.name == "id">
    <id column="id" jdbcType="CHAR" property="id" />
	<#else>
    <result column="${column.name}" jdbcType=<#if column.columnType == "varchar">"VARCHAR"</#if><#if column.columnType == "int">"INTEGER"</#if><#if column.columnType == "char">"CHAR"</#if><#if column.columnType == "timestamp">"TIMESTAMP"</#if> property="${column.nameUpperCamel}" />
	</#if>
	</#list>
  </resultMap>
  <sql id="Base_Column_List">
    <#list columnList as column>${column.name}<#if column_has_next>,</#if></#list>
  </sql>
</mapper>