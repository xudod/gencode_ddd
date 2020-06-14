package com.xudod.gen_code.domain_info.interfaces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xudod.gen_code.domain_info.domain.entity.po.DomainInfo;
import com.xudod.gen_code.domain_info.domain.service.DomainInfoService;
import com.xudod.gen_code.project_info.interfaces.vo.UpStateVo;
import com.xudod.gen_code.table_base_info.domain.entity.po.TableBaseInfo;
import com.xudod.gen_code.common.BaseResp;
import com.xudod.gen_code.common.OnlyId;
import com.xudod.gen_code.common.PageParameter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by xuyuzhao on 2020/04/11.
 */
@Api(value = "领域/微服务信息功能接口", produces = "application/json", tags = "领域/微服务信息功能接口")
@RestController
@RequestMapping("/api/domainInfo/")
public class DomainInfoController {

    @Autowired
    DomainInfoService domainInfoService;

	/*代码分界head TODO*/

    @ApiOperation(value = "新增一条数据", notes = "新增一条数据")
    @RequestMapping(value = "add", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> add(@RequestBody DomainInfo pojo){
    	return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, domainInfoService.add(pojo), "新增成功！"), HttpStatus.OK);
    }
    
    @ApiOperation(value = "根据主键删除一条数据", notes = "根据主键删除一条数据")
    @RequestMapping(value = "delbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> delbykey(@RequestBody OnlyId pojo){
        return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, domainInfoService.delbykey(pojo.getId()), "删除成功！"), HttpStatus.OK);
    }

    @ApiOperation(value = "根据主键更新一条数据", notes = "根据主键更新一条数据")
    @RequestMapping(value = "upbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> upbykey(@RequestBody DomainInfo pojo){
        return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, domainInfoService.upbykey(pojo), "更新成功！"), HttpStatus.OK);
    }
    
    @ApiOperation(value = "根据主键查询一条数据", notes = "根据主键查询一条数据")
    @RequestMapping(value = "getbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<DomainInfo>> getbykey(@RequestBody OnlyId pojo){
        return new ResponseEntity<BaseResp<DomainInfo>> (BaseResp.returnRes(200, domainInfoService.getbykey(pojo.getId()), "查询成功！"), HttpStatus.OK);
    }

    @ApiOperation(value = "根据分页条件查询数据", notes = "根据分页条件查询数据")
    @RequestMapping(value = "getpage", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<IPage<DomainInfo>>> getpage(@RequestBody PageParameter<DomainInfo> page){
        return new ResponseEntity<BaseResp<IPage<DomainInfo>>> (
        	BaseResp.returnRes(200, domainInfoService.getpage(page), "查询成功！"), HttpStatus.OK);
    }

	/*代码分界end TODO*/

    @ApiOperation(value = "根据主键更新用户状态", notes = "根据主键更新用户状态")
    @RequestMapping(value = "upStatebykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> upStatebykey(@RequestBody UpStateVo upStateVo){
        return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, domainInfoService.upStatebykey(upStateVo), "更新成功！"), HttpStatus.OK);
    }
    
    @ApiOperation(value = "根据项目id查询所有领域列表", notes = "根据项目id查询所有领域列表")
    @RequestMapping(value = "getDomainListByProjectId", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<List<DomainInfo>>> getDomainListByProjectId(@RequestBody OnlyId pojo){
        return new ResponseEntity<BaseResp<List<DomainInfo>>> (BaseResp.returnRes(200, domainInfoService.getDomainListByProjectId(pojo.getId()), "查询成功！"), HttpStatus.OK);
    }
    
    @ApiOperation(value = "获取领域内所有表信息", notes = "获取领域内所有表信息")
    @RequestMapping(value = "getTableInfo", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<List<TableBaseInfo>>> getTableInfo(@RequestBody OnlyId pojo){
        return new ResponseEntity<BaseResp<List<TableBaseInfo>>> (BaseResp.returnRes(200, domainInfoService.getTableInfo(pojo.getId()), "查询成功！"), HttpStatus.OK);
    }
}
