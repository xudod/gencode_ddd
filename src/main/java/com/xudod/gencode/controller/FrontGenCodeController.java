//package com.xudod.gencode.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.xudod.gen_code.common.BaseResp;
//import com.xudod.gen_code.common.OnlyId;
//import com.xudod.gencode.service.FrontGenCodeService;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//@Api(value = "自动化项目生成前端部分", produces = "application/json", tags = "自动化项目生成前端部分")
//@RestController
//@RequestMapping("/api/gencode/")
//public class FrontGenCodeController {
//	
//	@Autowired
//	private FrontGenCodeService frontGenCodeService;
//
//	@ApiOperation(value = "初始生成项目前端项目文件", notes = "初始生成项目前端项目文件")
//    @RequestMapping(value = "genFrontProject", method=RequestMethod.POST, consumes= "application/json; charset=utf-8")
//    ResponseEntity<BaseResp<Boolean>> genCodeAllProject(@RequestBody OnlyId pojo){
//        return new ResponseEntity<BaseResp<Boolean>> (BaseResp.returnRes(200, frontGenCodeService.genFrontProject(pojo.getId()), "项目生成成功！"), HttpStatus.OK);
//    }
//}
