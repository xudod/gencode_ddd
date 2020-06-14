package com.xudod.gen_code.column_info.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xudod.gen_code.column_info.domain.entity.po.ColumnInfo;
import com.xudod.gen_code.table_base_info.domain.entity.po.TableBaseInfo;

/**
 * 领域内表对应的字段信息
 * Created by xuyuzhao on 2020/05/30.
 */
public interface ColumnInfoMapper extends BaseMapper<ColumnInfo> {

	/*代码分界head TODO*/
	
	@Select("select nextval(#{tableName, jdbcType=VARCHAR})")
	Integer getSequence(@Param("tableName") String tableName);
	
	@Update("update column_info set status = #{status, jdbcType=VARCHAR}, modify_by = #{modifyBy, jdbcType=VARCHAR} where id = #{id, jdbcType=VARCHAR}")
	int upStatebykey(@Param("id") String id, @Param("status") String status, @Param("modifyBy") String modifyBy);
	
	@Select("select * from column_info where deleted = '0'")
	List<ColumnInfo> getAll();

	/*代码分界end TODO*/
	
	@Select("select * from column_info where table_id = #{tableId, jdbcType=VARCHAR} and deleted = '0' order by part_sequence")
	List<ColumnInfo> getTableColumnByTableId(String tableId);

	@Select("select count(*) from column_info where name = #{name, jdbcType=VARCHAR} and table_id = #{tableId, jdbcType=VARCHAR} and deleted = '0'")
	int nameRepeatCheck(@Param("name") String name, @Param("tableId") String tableId);

	//@Select("select * from column_info where table_id in (#{tableIds, jdbcType=VARCHAR}) and deleted = '0' order by part_sequence")
	@Select({
		"<script>",
	        "select * from column_info where table_id in",
	            "<foreach collection='list' item='item' open='(' separator=',' close=')'>",
	            "#{item.id}",
	            "</foreach>",
	            "and deleted = '0' order by part_sequence",
	    "</script>"
	})
	List<ColumnInfo> getTableColumnByTableIds(List<TableBaseInfo> tableList);
	
}
