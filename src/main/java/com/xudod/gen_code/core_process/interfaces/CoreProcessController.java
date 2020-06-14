package com.xudod.gen_code.core_process.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xudod.gen_code.common.BaseResp;
import com.xudod.gen_code.common.OnlyId;
import com.xudod.gen_code.core_process.domain.service.GenDBService;
import com.xudod.gencode.service.GenCodeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by xuyuzhao on 2020/06/03.
 */
@Api(value = "代码生成核心功能", produces = "application/json", tags = "代码生成核心功能")
@RestController
@RequestMapping("/api/gencode/")
public class CoreProcessController {

    @Autowired
    GenCodeService genCodeService;

    @Autowired
    GenDBService genDBService;
	
    @ApiOperation(value = "初始生成整个项目", notes = "初始生成整个项目")
    @RequestMapping(value = "genCodeAllProject", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Boolean>> genCodeAllProject(@RequestBody OnlyId pojo){
        return new ResponseEntity<BaseResp<Boolean>> (BaseResp.returnRes(200, genCodeService.genCodeAllProject(pojo.getId()), "项目生成成功！"), HttpStatus.OK);
    }
    
    @ApiOperation(value = "初始生成数据库表", notes = "初始生成数据库表")
    @RequestMapping(value = "genDB", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
    ResponseEntity<BaseResp<Boolean>> genDB(@RequestBody OnlyId pojo){
        return new ResponseEntity<BaseResp<Boolean>> (BaseResp.returnRes(200, genDBService.genDB(pojo.getId()), "项目生成成功！"), HttpStatus.OK);
    }
    
}
