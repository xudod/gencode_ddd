package com.xudod.gen_code.project_info.interfaces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xudod.gen_code.project_info.domain.entity.po.ProjectInfo;
import com.xudod.gen_code.project_info.domain.service.ProjectInfoService;
import com.xudod.gen_code.project_info.interfaces.vo.UpStateVo;
import com.xudod.gen_code.common.BaseResp;
import com.xudod.gen_code.common.OnlyId;
import com.xudod.gen_code.common.PageParameter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by xuyuzhao on 2020/04/11.
 */
@Api(value = "项目信息功能接口", produces = "application/json", tags = "项目信息功能接口")
@RestController
@RequestMapping("/api/projectInfo/")
public class ProjectInfoController {

    @Autowired
    ProjectInfoService projectInfoService;

	/*代码分界head TODO*/


    @ApiOperation(value = "新增一条数据", notes = "新增一条数据")
    @RequestMapping(value = "add", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> add(@RequestBody ProjectInfo pojo){
    	return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, projectInfoService.add(pojo), "新增成功！"), HttpStatus.OK);
    }
    
    @ApiOperation(value = "根据主键删除一条数据", notes = "根据主键删除一条数据")
    @RequestMapping(value = "delbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> delbykey(@RequestBody OnlyId pojo){
        return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, projectInfoService.delbykey(pojo.getId()), "删除成功！"), HttpStatus.OK);
    }

    @ApiOperation(value = "根据主键更新一条数据", notes = "根据主键更新一条数据")
    @RequestMapping(value = "upbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> upbykey(@RequestBody ProjectInfo pojo){
        return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, projectInfoService.upbykey(pojo), "更新成功！"), HttpStatus.OK);
    }
    
    @ApiOperation(value = "根据主键查询一条数据", notes = "根据主键查询一条数据")
    @RequestMapping(value = "getbykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<ProjectInfo>> getbykey(@RequestBody OnlyId pojo){
        return new ResponseEntity<BaseResp<ProjectInfo>> (BaseResp.returnRes(200, projectInfoService.getbykey(pojo.getId()), "查询成功！"), HttpStatus.OK);
    }

    @ApiOperation(value = "根据分页条件查询数据", notes = "根据分页条件查询数据")
    @RequestMapping(value = "getpage", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<IPage<ProjectInfo>>> getpage(@RequestBody PageParameter<ProjectInfo> page){
        return new ResponseEntity<BaseResp<IPage<ProjectInfo>>> (
        	BaseResp.returnRes(200, projectInfoService.getpage(page), "查询成功！"), HttpStatus.OK);
    }

	/*代码分界end TODO*/

    @ApiOperation(value = "根据主键更新用户状态", notes = "根据主键更新用户状态")
    @RequestMapping(value = "upStatebykey", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Integer>> upStatebykey(@RequestBody UpStateVo upStateVo){
        return new ResponseEntity<BaseResp<Integer>> (BaseResp.returnRes(200, projectInfoService.upStatebykey(upStateVo), "更新成功！"), HttpStatus.OK);
    }
    
    @ApiOperation(value = "查询所有项目数据,领域/微服务用", notes = "查询所有项目数据,领域/微服务用")
    @RequestMapping(value = "getAllPorjectForDomain", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<List<ProjectInfo>>> getAllPorjectForDomain(){
        return new ResponseEntity<BaseResp<List<ProjectInfo>>> (BaseResp.returnRes(200, projectInfoService.getAllPorjectForDomain(), "查询成功！"), HttpStatus.OK);
    }
    
    @ApiOperation(value = "查询所有项目数据", notes = "查询所有项目数据")
    @RequestMapping(value = "getAll", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<List<ProjectInfo>>> getAllPorject(){
        return new ResponseEntity<BaseResp<List<ProjectInfo>>> (BaseResp.returnRes(200, projectInfoService.getAllPorject(), "查询成功！"), HttpStatus.OK);
    }
}
