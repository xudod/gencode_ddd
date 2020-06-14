package com.xudod.gen_code.project_info.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xudod.gen_code.project_info.domain.entity.po.ProjectInfo;

/**
 * 项目信息
 * Created by xuyuzhao on 2020/04/11.
 */
public interface ProjectInfoMapper extends BaseMapper<ProjectInfo> {

	/*代码分界head TODO*/
	
	@Select("select nextval(#{tableName, jdbcType=VARCHAR})")
	Integer getSequence(@Param("tableName") String tableName);

	/*代码分界end TODO*/
	
	@Update("update project_info set use_status = #{useStatus, jdbcType=VARCHAR}, modify_by = #{modifyBy, jdbcType=VARCHAR} where id = #{id, jdbcType=VARCHAR}")
	int upStatebykey(@Param("id") String id, @Param("useStatus") String useStatus, @Param("modifyBy") String modifyBy);
	
	@Select("select id, project_name from project_info where deleted = '0'")
	List<ProjectInfo> getAllPorjectForDomain();
	
	@Select("select * from project_info where deleted = '0'")
	List<ProjectInfo> getAllPorject();
}
