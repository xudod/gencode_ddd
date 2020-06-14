package ${controllerPackage};
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cgr.common.result.BaseResp;
import ${modelPackage}.${modelNameUpperCamel};
import ${servicePackage}.${modelNameUpperCamel}Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by ${author} on ${date}.
 */
@Api(value = "${tableComment}功能接口", produces = "application/json", tags = "${tableComment}功能接口")
@RestController
@RequestMapping("/${baseRequestMapping}/")
public class ${modelNameUpperCamel}Controller {

    @Autowired
    ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @ApiOperation(value = "新增一条数据", notes = "新增一条数据")
    @RequestMapping(value = "add", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> add(@RequestBody ${modelNameUpperCamel} pojo){
    	return new ResponseEntity<BaseResp<Integer>> (${modelNameLowerCamel}Service.add(pojo), HttpStatus.OK);
    }
    
    @ApiOperation(value = "新增多条数据", notes = "新增多条数据")
    @RequestMapping(value = "adds", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> adds(@RequestBody List<${modelNameUpperCamel}> pojos){
    	return new ResponseEntity<BaseResp<Integer>> (${modelNameLowerCamel}Service.adds(pojos), HttpStatus.OK);
    }
    
    @ApiOperation(value = "根据主键删除一条数据", notes = "根据主键删除一条数据")
    @RequestMapping(value = "delbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> delbykey(@RequestBody ${modelNameUpperCamel} pojo){
        return new ResponseEntity<BaseResp<Integer>> (${modelNameLowerCamel}Service.delbykey(pojo), HttpStatus.OK);
    }

    @ApiOperation(value = "根据主键更新一条数据", notes = "根据主键更新一条数据")
    @RequestMapping(value = "upbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> upbykey(@RequestBody ${modelNameUpperCamel} pojo){
        return new ResponseEntity<BaseResp<Integer>> (${modelNameLowerCamel}Service.upbykey(pojo), HttpStatus.OK);
    }
    
    @ApiOperation(value = "根据主键查询一条数据", notes = "根据主键查询一条数据")
    @RequestMapping(value = "getbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<${modelNameUpperCamel}>> getbykey(@RequestBody ${modelNameUpperCamel} pojo){
        return new ResponseEntity<BaseResp<${modelNameUpperCamel}>> (${modelNameLowerCamel}Service.getbykey(pojo), HttpStatus.OK);
    }

//    @ApiOperation(value = "根据分页条件查询数据", notes = "根据分页条件查询数据")
//    @RequestMapping(value = "getpage", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
//    ResponseEntity<BaseResp<Page<List<${modelNameUpperCamel}>>>> getpage(@RequestBody Page<${modelNameUpperCamel}> page){
//        return new ResponseEntity<BaseResp<Page<List<${modelNameUpperCamel}>>>> (${modelNameLowerCamel}Service.getpage(page), HttpStatus.OK);
//    }

}
