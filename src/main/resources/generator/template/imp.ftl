package ${basePackageValue}.${ddd_name}.domain.service;

import ${basePackageValue}.${ddd_name}.${entityPackageName}.po.${modelNameUpperCamel};
import ${basePackageValue}.${ddd_name}.${mapperPackageName}.${modelNameUpperCamel}Mapper;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import java.util.List;
import org.slf4j.LoggerFactory;
import ${basePackageValue}.common.BusinessException;
import ${basePackageValue}.common.GenId;
import ${basePackageValue}.common.UpStateVo;
import ${basePackageValue}.common.DataCorrectCheck;
import ${basePackageValue}.common.PageParameter;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 *
 * Created by ${author} on ${date}.
 */
@Service("${modelNameLowerCamel}Service")
public class ${modelNameUpperCamel}Imp implements ${modelNameUpperCamel}Service {

	${workerId}
	private long workerId;
	
	${datacenterId}
	private long datacenterId;
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;
    
    private Logger logger= LoggerFactory.getLogger(${modelNameUpperCamel}Imp.class);
    
    /*代码分界head TODO*/
	
	@Override
	public Integer add(${modelNameUpperCamel} po) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(po, "新增数据时，检测到数据实体为null，保存失败！");
			if(null == po.getId() || "".equals(po.getId())) {
				po.setId(GenId.getUUID(workerId, datacenterId));
			}
			po.setCreateBy(null != request.getHeader("userid") ? request.getHeader("userid") : "");
			po.setSequence(${modelNameLowerCamel}Mapper.getSequence("${tableName}"));
			int res = ${modelNameLowerCamel}Mapper.insert(po);
			addUpDelResCheck(res, "更新数据失败！");
			return res;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Integer delbykey(String id) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(id, "删除数据时，检测到id字符串值为null，删除失败！");
			int res = ${modelNameLowerCamel}Mapper.deleteById(id);
			addUpDelResCheck(res, "更新数据失败！");
			return res;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Integer upbykey(${modelNameUpperCamel} po) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(po, "更新数据时，检测到数据实体为null，更新失败！");
			po.setModifyBy(request.getHeader("userid"));
			int res = ${modelNameLowerCamel}Mapper.updateById(po);
			addUpDelResCheck(res, "更新数据失败！");
			return res;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ${modelNameUpperCamel} getbykey(String id) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(id, "查询数据时，检测到查询id为null，查询失败！");
			${modelNameUpperCamel} po = ${modelNameLowerCamel}Mapper.selectById(id);
			return po;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public IPage<${modelNameUpperCamel}> getpage(PageParameter<${modelNameUpperCamel}> page) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(page, "查询数据时，检测到分页对象为null，查询失败！");
			Page<${modelNameUpperCamel}> ipage = new Page<${modelNameUpperCamel}>();
			BeanUtils.copyProperties(page, ipage);
	        IPage<${modelNameUpperCamel}> orgIPage = ${modelNameLowerCamel}Mapper.selectPage(ipage, new QueryWrapper<${modelNameUpperCamel}>());
	        return orgIPage;
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public Integer upStatebykey(UpStateVo upStateVo) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(upStateVo, "更新数据时，检测到数据实体为null，更新失败！");
			String header = null != request.getHeader("userid") ? request.getHeader("userid") : "reqHeaderNull";
			int res = ${modelNameLowerCamel}Mapper.upStatebykey(upStateVo.getId(), upStateVo.getStatus(), header);
			addUpDelResCheck(res, "更新数据失败！");
			return res;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<${modelNameUpperCamel}> getAll() {
		List<${modelNameUpperCamel}> res = ${modelNameLowerCamel}Mapper.getAll();
		return res;
	}
	
	private void addUpDelResCheck(int res, String msg){
		if(res < 1){
			throw new BusinessException(res, msg);
		}
	}
	
	/*代码分界end TODO*/
	
}
