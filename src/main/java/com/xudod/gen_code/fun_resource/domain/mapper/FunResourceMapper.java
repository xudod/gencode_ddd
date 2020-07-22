package com.xudod.gen_code.fun_resource.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xudod.gen_code.fun_resource.domain.entity.po.FunResource;

/**
 * 权限功能资源
 * Created by xudod on 2020/04/25.
 */
public interface FunResourceMapper extends BaseMapper<FunResource> {

	/*代码分界head TODO*/
	
	@Select("select nextval(#{tableName, jdbcType=VARCHAR})")
	Integer getSequence(@Param("tableName") String tableName);
	
	/*代码分界end TODO*/
	
	@Select("select id,show_name_cn,code,type,url from fun_resource")
	List<FunResource> getAll();

	@Select("select id, show_name_cn, code, url, pid from fun_resource where belong_sys = #{code, jdbcType=VARCHAR} and (type = 'MENU_ONE' or type = 'MENU_TWO') and deleted = '0'")
	List<FunResource> getAllOnlyMenu(@Param("code") String code);
}
