//package com.xudod.gencode.controller;
//
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//import com.xudod.gen_code.common.BaseResp;
//import com.xudod.gen_code.common.OnlyId;
//import com.xudod.gencode.service.GenCodeService;
//
//
///**
// * Created by XUDOD on 2019/10/13.
// */
//@Api(value = "自动化项目生成", produces = "application/json", tags = "自动化项目生成")
//@RestController
//@RequestMapping("/api/gencode/")
//public class GenCodeController {
//
//    @Autowired
//    GenCodeService genCodeService;
//
//    @ApiOperation(value = "初始生成整个项目", notes = "初始生成整个项目")
//    @RequestMapping(value = "genCodeAllProject", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
//    ResponseEntity<BaseResp<Boolean>> genCodeAllProject(@RequestBody OnlyId pojo){
//        return new ResponseEntity<BaseResp<Boolean>> (BaseResp.returnRes(200, genCodeService.genCodeAllProject(pojo.getId()), "项目生成成功！"), HttpStatus.OK);
//    }
    
//    @ApiOperation(value = "新建项目", notes = "新建项目")
//    @RequestMapping(value = "createProject", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
//    ResponseEntity<BaseResp<Integer>> createProject(@RequestBody GenCodeInfo genCodeInfo) throws IOException{
//        return new ResponseEntity<BaseResp<Integer>> (genCodeService.createProject(genCodeInfo), HttpStatus.OK);
//    }
//    
//    @ApiOperation(value = "初始化数据保存", notes = "初始化数据保存")
//    @RequestMapping(value = "initDataSave", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
//    ResponseEntity<BaseResp<Integer>> initDataSave(@RequestBody GenCodeInfo genCodeInfo) throws IOException{
//        return new ResponseEntity<BaseResp<Integer>> (genCodeService.initDataSave(genCodeInfo), HttpStatus.OK);
//    }
//    
//    @ApiOperation(value = "初始化项目目录", notes = "初始化项目目录")
//    @RequestMapping(value = "initProjectFolder", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
//    ResponseEntity<BaseResp<Integer>> initProjectFolder(@RequestBody GenCodeInfo genCodeInfo) throws IOException{
//        return new ResponseEntity<BaseResp<Integer>> (genCodeService.initProjectFolder(genCodeInfo), HttpStatus.OK);
//    }

//}
