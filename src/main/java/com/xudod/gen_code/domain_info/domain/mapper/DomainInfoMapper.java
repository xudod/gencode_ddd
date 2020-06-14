package com.xudod.gen_code.domain_info.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xudod.gen_code.domain_info.domain.entity.po.DomainInfo;

/**
 * 领域/微服务信息
 * Created by xuyuzhao on 2020/04/11.
 */
public interface DomainInfoMapper extends BaseMapper<DomainInfo> {

	/*代码分界head TODO*/
	
	@Select("select nextval(#{tableName, jdbcType=VARCHAR})")
	Integer getSequence(@Param("tableName") String tableName);
	
	/*代码分界end TODO*/
	
	@Select("select * from domain_info where project_id = #{id, jdbcType=VARCHAR} and deleted = '0'")
	List<DomainInfo> getDomainListByProjectId(String id);

	@Update("update domain_info set use_status = #{useStatus, jdbcType=VARCHAR}, modify_by = #{modifyBy, jdbcType=VARCHAR} where id = #{id, jdbcType=VARCHAR}")
	int upStatebykey(@Param("id") String id, @Param("useStatus") String useStatus, @Param("modifyBy") String modifyBy);

	@Select("select db_table_list from domain_info where id = #{id, jdbcType=VARCHAR} and deleted = '0'")
	String getTableInfo(String id);

	@Update("update domain_info set db_table_list = #{tableStr, jdbcType=VARCHAR}, modify_by = #{modifyBy, jdbcType=VARCHAR} where id = #{id, jdbcType=VARCHAR}")
	int addTableNameInDomain(@Param("id") String id, @Param("tableStr") String tableStr, @Param("modifyBy") String modifyBy);
}
