package com.xudod.gen_code.table_base_info.interfaces;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xudod.gen_code.table_base_info.domain.entity.po.TableBaseInfo;
import com.xudod.gen_code.table_base_info.domain.service.TableBaseInfoService;
import com.xudod.gen_code.common.UpStateVo;
import com.xudod.gen_code.common.BaseResp;
import com.xudod.gen_code.common.OnlyId;
import com.xudod.gen_code.common.PageParameter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by xuyuzhao on 2020/06/03.
 */
@Api(value = "表的基本信息功能接口", produces = "application/json", tags = "表的基本信息功能接口")
@RestController
@RequestMapping("/api/tableBaseInfo/")
public class TableBaseInfoController {

    @Autowired
    TableBaseInfoService tableBaseInfoService;

	/*代码分界head TODO*/


    @ApiOperation(value = "新增一条数据", notes = "新增一条数据")
    @RequestMapping(value = "add", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> add(@RequestBody TableBaseInfo pojo){
    	return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, tableBaseInfoService.add(pojo), "新增成功！"), HttpStatus.OK);
    }
    
    @ApiOperation(value = "根据主键删除一条数据", notes = "根据主键删除一条数据")
    @RequestMapping(value = "delbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> delbykey(@RequestBody OnlyId pojo){
        return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, tableBaseInfoService.delbykey(pojo.getId()), "删除成功！"), HttpStatus.OK);
    }

    @ApiOperation(value = "根据主键更新一条数据", notes = "根据主键更新一条数据")
    @RequestMapping(value = "upbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> upbykey(@RequestBody TableBaseInfo pojo){
        return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, tableBaseInfoService.upbykey(pojo), "更新成功！"), HttpStatus.OK);
    }
    
    @ApiOperation(value = "根据主键查询一条数据", notes = "根据主键查询一条数据")
    @RequestMapping(value = "getbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<TableBaseInfo>> getbykey(@RequestBody OnlyId pojo){
        return new ResponseEntity<BaseResp<TableBaseInfo>> (BaseResp.returnRes(200, tableBaseInfoService.getbykey(pojo.getId()), "查询成功！"), HttpStatus.OK);
    }

    @ApiOperation(value = "根据分页条件查询数据", notes = "根据分页条件查询数据")
    @RequestMapping(value = "getpage", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<IPage<TableBaseInfo>>> getpage(@RequestBody PageParameter<TableBaseInfo> page){
        return new ResponseEntity<BaseResp<IPage<TableBaseInfo>>> (
        	BaseResp.returnRes(200, tableBaseInfoService.getpage(page), "查询成功！"), HttpStatus.OK);
    }

	@ApiOperation(value = "根据主键更新可用状态", notes = "根据主键更新可用状态")
    @RequestMapping(value = "upstatebykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> upstatebykey(@RequestBody UpStateVo upStateVo){
        return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, tableBaseInfoService.upStatebykey(upStateVo), "更新成功！"), HttpStatus.OK);
    }

    @ApiOperation(value = "查询所有数据", notes = "查询所有数据")
    @RequestMapping(value = "getall", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<List<TableBaseInfo>>> getall(@RequestBody Object object){
        return new ResponseEntity<BaseResp<List<TableBaseInfo>>> (
        	BaseResp.returnRes(200, tableBaseInfoService.getAll(), "查询成功！"), HttpStatus.OK);
    }
    
	/*代码分界end TODO*/
    
    @ApiOperation(value = "领域添加表信息", notes = "领域添加表信息")
    @RequestMapping(value = "addTableNameInDomain", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<String>> addTableNameInDomain(@RequestBody TableBaseInfo tableBaseInfo){
        return new ResponseEntity<BaseResp<String>> (BaseResp.returnRes(200, tableBaseInfoService.addTableNameInDomain(tableBaseInfo), "添加成功！"), HttpStatus.OK);
    }
    
    @ApiOperation(value = "删除表字段信息", notes = "删除表字段信息")
    @RequestMapping(value = "delTableNameInDomain", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> delTableNameInDomain(@RequestBody TableBaseInfo tableBaseInfo){
        return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, tableBaseInfoService.delTableNameInDomain(tableBaseInfo), "删除成功！"), HttpStatus.OK);
    }


}
