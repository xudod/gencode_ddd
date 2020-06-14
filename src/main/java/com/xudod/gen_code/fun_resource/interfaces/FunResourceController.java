package com.xudod.gen_code.fun_resource.interfaces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xudod.gen_code.common.BaseResp;
import com.xudod.gen_code.common.OnlyId;
import com.xudod.gen_code.common.PageParameter;
import com.xudod.gen_code.fun_resource.domain.entity.po.FunResource;
import com.xudod.gen_code.fun_resource.domain.service.FunResourceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by xudod on 2020/04/25.
 */
@Api(value = "权限功能资源功能接口", produces = "application/json", tags = "权限功能资源功能接口")
@RestController
@RequestMapping("/api/funResource/")
public class FunResourceController {

    @Autowired
    FunResourceService funResourceService;

	/*代码分界head TODO*/

    @ApiOperation(value = "新增一条数据", notes = "新增一条数据")
    @RequestMapping(value = "add", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> add(@RequestBody FunResource pojo){
    	return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, funResourceService.add(pojo), "新增成功！"), HttpStatus.OK);
    }
    
    @ApiOperation(value = "根据主键删除一条数据", notes = "根据主键删除一条数据")
    @RequestMapping(value = "delbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> delbykey(@RequestBody OnlyId pojo){
        return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, funResourceService.delbykey(pojo.getId()), "删除成功！"), HttpStatus.OK);
    }

    @ApiOperation(value = "根据主键更新一条数据", notes = "根据主键更新一条数据")
    @RequestMapping(value = "upbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> upbykey(@RequestBody FunResource pojo){
        return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, funResourceService.upbykey(pojo), "更新成功！"), HttpStatus.OK);
    }
    
    @ApiOperation(value = "根据主键查询一条数据", notes = "根据主键查询一条数据")
    @RequestMapping(value = "getbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<FunResource>> getbykey(@RequestBody OnlyId pojo){
        return new ResponseEntity<BaseResp<FunResource>> (BaseResp.returnRes(200, funResourceService.getbykey(pojo.getId()), "查询成功！"), HttpStatus.OK);
    }

    @ApiOperation(value = "根据分页条件查询数据", notes = "根据分页条件查询数据")
    @RequestMapping(value = "getpage", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<IPage<FunResource>>> getpage(@RequestBody PageParameter<FunResource> page){
        return new ResponseEntity<BaseResp<IPage<FunResource>>> (
        	BaseResp.returnRes(200, funResourceService.getpage(page), "查询成功！"), HttpStatus.OK);
    }

	/*代码分界end TODO*/

    @ApiOperation(value = "查询所有数据", notes = "查询所有数据")
    @RequestMapping(value = "getAll", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<List<FunResource>>> getAll(@RequestBody Object object){
        return new ResponseEntity<BaseResp<List<FunResource>>> (
        	BaseResp.returnRes(200, funResourceService.getAll(), "查询成功！"), HttpStatus.OK);
    }

}
