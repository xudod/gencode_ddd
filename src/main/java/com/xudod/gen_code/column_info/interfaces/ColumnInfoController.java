package com.xudod.gen_code.column_info.interfaces;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xudod.gen_code.column_info.domain.entity.po.ColumnInfo;
import com.xudod.gen_code.column_info.domain.service.ColumnInfoService;
import com.xudod.gen_code.common.UpStateVo;
import com.xudod.gen_code.common.BaseResp;
import com.xudod.gen_code.common.OnlyId;
import com.xudod.gen_code.common.PageParameter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by xuyuzhao on 2020/05/30.
 */
@Api(value = "领域内表对应的字段信息功能接口", produces = "application/json", tags = "领域内表对应的字段信息功能接口")
@RestController
@RequestMapping("/api/columnInfo/")
public class ColumnInfoController {

    @Autowired
    ColumnInfoService columnInfoService;

	/*代码分界head TODO*/

    @ApiOperation(value = "新增一条数据", notes = "新增一条数据")
    @RequestMapping(value = "add", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> add(@RequestBody ColumnInfo pojo){
    	return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, columnInfoService.add(pojo), "新增成功！"), HttpStatus.OK);
    }
    
    @ApiOperation(value = "根据主键删除一条数据", notes = "根据主键删除一条数据")
    @RequestMapping(value = "delbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> delbykey(@RequestBody OnlyId pojo){
        return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, columnInfoService.delbykey(pojo.getId()), "删除成功！"), HttpStatus.OK);
    }

    @ApiOperation(value = "根据主键更新一条数据", notes = "根据主键更新一条数据")
    @RequestMapping(value = "upbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> upbykey(@RequestBody ColumnInfo pojo){
        return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, columnInfoService.upbykey(pojo), "更新成功！"), HttpStatus.OK);
    }
    
    @ApiOperation(value = "根据主键查询一条数据", notes = "根据主键查询一条数据")
    @RequestMapping(value = "getbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<ColumnInfo>> getbykey(@RequestBody OnlyId pojo){
        return new ResponseEntity<BaseResp<ColumnInfo>> (BaseResp.returnRes(200, columnInfoService.getbykey(pojo.getId()), "查询成功！"), HttpStatus.OK);
    }

    @ApiOperation(value = "根据分页条件查询数据", notes = "根据分页条件查询数据")
    @RequestMapping(value = "getpage", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<IPage<ColumnInfo>>> getpage(@RequestBody PageParameter<ColumnInfo> page){
        return new ResponseEntity<BaseResp<IPage<ColumnInfo>>> (
        	BaseResp.returnRes(200, columnInfoService.getpage(page), "查询成功！"), HttpStatus.OK);
    }

	@ApiOperation(value = "根据主键更新可用状态", notes = "根据主键更新可用状态")
    @RequestMapping(value = "upstatebykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> upstatebykey(@RequestBody UpStateVo upStateVo){
        return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, columnInfoService.upStatebykey(upStateVo), "更新成功！"), HttpStatus.OK);
    }

    @ApiOperation(value = "查询所有数据", notes = "查询所有数据")
    @RequestMapping(value = "getall", method=RequestMethod.POST)
    ResponseEntity<BaseResp<List<ColumnInfo>>> getall(){
        return new ResponseEntity<BaseResp<List<ColumnInfo>>> (
        	BaseResp.returnRes(200, columnInfoService.getAll(), "查询成功！"), HttpStatus.OK);
    }
    
	/*代码分界end TODO*/

    @ApiOperation(value = "查询领域表的所有字段数据", notes = "查询领域表的所有字段数据")
    @RequestMapping(value = "getTableColumnByTableId", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<List<ColumnInfo>>> getTableColumnByTableId(@RequestBody ColumnInfo pojo){
        return new ResponseEntity<BaseResp<List<ColumnInfo>>> (
        	BaseResp.returnRes(200, columnInfoService.getTableColumnByTableId(pojo), "查询成功！"), HttpStatus.OK);
    }

    @ApiOperation(value = "批量插入", notes = "批量插入")
    @RequestMapping(value = "addBatchColumn", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<List<ColumnInfo>>> addBatchColumn(@RequestBody List<ColumnInfo> columnInfoList){
        return new ResponseEntity<BaseResp<List<ColumnInfo>>> (
        	BaseResp.returnRes(200, columnInfoService.addBatchColumn(columnInfoList), "批量插入成功！"), HttpStatus.OK);
    }
    
}
