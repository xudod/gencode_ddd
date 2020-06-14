package ${basePackageValue}.${ddd_name}.domain.service;

import ${basePackageValue}.${ddd_name}.${entityPackageName}.po.${modelNameUpperCamel};
import com.baomidou.mybatisplus.core.metadata.IPage;
import ${basePackageValue}.common.BusinessException;
import ${basePackageValue}.common.DataCorrectCheck;
import ${basePackageValue}.common.PageParameter;
import ${basePackageValue}.common.UpStateVo;
import java.util.List;


/**
 *
 * Created by ${author} on ${date}.
 */
public interface ${modelNameUpperCamel}Service {
    
    /*代码分界head TODO*/

	Integer add(${modelNameUpperCamel} pojo);

	Integer delbykey(String id);

	Integer upbykey(${modelNameUpperCamel} pojo);

	${modelNameUpperCamel} getbykey(String id);
	
	IPage<${modelNameUpperCamel}> getpage(PageParameter<${modelNameUpperCamel}> page);

	Integer upStatebykey(UpStateVo upStateVo);

	List<${modelNameUpperCamel}> getAll();

	/*代码分界end TODO*/
}
