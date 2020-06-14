package ${basePackageValue}.${ddd_name}.${mapperPackageName};

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${basePackageValue}.${ddd_name}.${entityPackageName}.po.${modelNameUpperCamel};

/**
 * ${tableComment}
 * Created by ${author} on ${date}.
 */
public interface ${modelNameUpperCamel}Mapper extends BaseMapper<${modelNameUpperCamel}> {

	/*代码分界head TODO*/
	
	@Select("select nextval(${r"#{tableName, jdbcType=VARCHAR}"})")
	Integer getSequence(@Param("tableName") String tableName);
	
	@Update("update ${ddd_name} set status = ${r"#{status, jdbcType=VARCHAR}"}, modify_by = ${r"#{modifyBy, jdbcType=VARCHAR}"} where id = ${r"#{id, jdbcType=VARCHAR}"}")
	int upStatebykey(@Param("id") String id, @Param("status") String status, @Param("modifyBy") String modifyBy);
	
	@Select("select * from ${ddd_name} where deleted = '0'")
	List<${modelNameUpperCamel}> getAll();

	/*代码分界end TODO*/
}
