package ${basePackageValue}.${ddd_name}.interfaces;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import ${basePackageValue}.${ddd_name}.${entityPackageName}.po.${modelNameUpperCamel};
import ${basePackageValue}.${ddd_name}.domain.service.${modelNameUpperCamel}Service;
import ${basePackageValue}.common.UpStateVo;
import ${basePackageValue}.common.BaseResp;
import ${basePackageValue}.common.OnlyId;
import ${basePackageValue}.common.PageParameter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by ${author} on ${date}.
 */
@Api(value = "${tableComment}功能接口", produces = "application/json", tags = "${tableComment}功能接口")
@RestController
@RequestMapping("/api/${baseRequestMapping}/")
public class ${modelNameUpperCamel}Controller {

    @Autowired
    ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

	/*代码分界head TODO*/

    @ApiOperation(value = "新增一条数据", notes = "新增一条数据")
    @RequestMapping(value = "add", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> add(@RequestBody ${modelNameUpperCamel} pojo){
    	return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, ${modelNameLowerCamel}Service.add(pojo), "新增成功！"), HttpStatus.OK);
    }
    
    @ApiOperation(value = "根据主键删除一条数据", notes = "根据主键删除一条数据")
    @RequestMapping(value = "delbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> delbykey(@RequestBody OnlyId pojo){
        return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, ${modelNameLowerCamel}Service.delbykey(pojo.getId()), "删除成功！"), HttpStatus.OK);
    }

    @ApiOperation(value = "根据主键更新一条数据", notes = "根据主键更新一条数据")
    @RequestMapping(value = "upbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> upbykey(@RequestBody ${modelNameUpperCamel} pojo){
        return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, ${modelNameLowerCamel}Service.upbykey(pojo), "更新成功！"), HttpStatus.OK);
    }
    
    @ApiOperation(value = "根据主键查询一条数据", notes = "根据主键查询一条数据")
    @RequestMapping(value = "getbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<${modelNameUpperCamel}>> getbykey(@RequestBody OnlyId pojo){
        return new ResponseEntity<BaseResp<${modelNameUpperCamel}>> (BaseResp.returnRes(200, ${modelNameLowerCamel}Service.getbykey(pojo.getId()), "查询成功！"), HttpStatus.OK);
    }

    @ApiOperation(value = "根据分页条件查询数据", notes = "根据分页条件查询数据")
    @RequestMapping(value = "getpage", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<IPage<${modelNameUpperCamel}>>> getpage(@RequestBody PageParameter<${modelNameUpperCamel}> page){
        return new ResponseEntity<BaseResp<IPage<${modelNameUpperCamel}>>> (
        	BaseResp.returnRes(200, ${modelNameLowerCamel}Service.getpage(page), "查询成功！"), HttpStatus.OK);
    }

	@ApiOperation(value = "根据主键更新可用状态", notes = "根据主键更新可用状态")
    @RequestMapping(value = "upstatebykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> upstatebykey(@RequestBody UpStateVo upStateVo){
        return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, ${modelNameLowerCamel}Service.upStatebykey(upStateVo), "更新成功！"), HttpStatus.OK);
    }

    @ApiOperation(value = "查询所有数据", notes = "查询所有数据")
    @RequestMapping(value = "getall", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<List<${modelNameUpperCamel}>>> getall(@RequestBody Object object){
        return new ResponseEntity<BaseResp<List<${modelNameUpperCamel}>>> (
        	BaseResp.returnRes(200, ${modelNameLowerCamel}Service.getAll(), "查询成功！"), HttpStatus.OK);
    }
    
	/*代码分界end TODO*/

}
