package com.xudod.gen_code.table_base_info.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xudod.gen_code.domain_info.domain.entity.po.DomainInfo;
import com.xudod.gen_code.table_base_info.domain.entity.po.TableBaseInfo;

/**
 * 表的基本信息
 * Created by xuyuzhao on 2020/06/03.
 */
public interface TableBaseInfoMapper extends BaseMapper<TableBaseInfo> {

	/*代码分界head TODO*/

	
	@Select("select nextval(#{tableName, jdbcType=VARCHAR})")
	Integer getSequence(@Param("tableName") String tableName);
	
	@Update("update table_base_info set status = #{status, jdbcType=VARCHAR}, modify_by = #{modifyBy, jdbcType=VARCHAR} where id = #{id, jdbcType=VARCHAR}")
	int upStatebykey(@Param("id") String id, @Param("status") String status, @Param("modifyBy") String modifyBy);
	
	@Select("select * from table_base_info where deleted = '0'")
	List<TableBaseInfo> getAll();

	/*代码分界end TODO*/

	@Select("select * from table_base_info where domain_id = #{domainId, jdbcType=VARCHAR} and deleted = '0'")
	List<TableBaseInfo> getTableByDomainId(@Param("domainId") String domainId);

	@Update("update table_base_info set deleted = '1' where domain_id = #{domainId, jdbcType=VARCHAR} and table_name = #{tableName, jdbcType=VARCHAR}")
	int delTableNameInDomain(String tableName, String domainId);

	//@Select("select * from table_base_info where domain_id in (#{domainIds, jdbcType=VARCHAR})")
	@Select({
		"<script>",
	        "select * from table_base_info where domain_id in",
	            "<foreach collection='list' item='id' open='(' separator=',' close=')'>",
	            "#{id}",
	            "</foreach>",
	    "</script>"
	})
	List<TableBaseInfo> getTableByDomainIds(List<String> domainList);
}
