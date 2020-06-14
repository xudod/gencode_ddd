package com.xudod.gen_code.project_info.domain.service;

import com.xudod.gen_code.project_info.domain.entity.po.ProjectInfo;
import com.xudod.gen_code.project_info.interfaces.vo.UpStateVo;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xudod.gen_code.common.BusinessException;
import com.xudod.gen_code.common.DataCorrectCheck;
import com.xudod.gen_code.common.PageParameter;


/**
 *
 * Created by xuyuzhao on 2020/04/11.
 */
public interface ProjectInfoService {
    
    /*代码分界head TODO*/


	Integer add(ProjectInfo pojo);

	Integer delbykey(String id);

	Integer upbykey(ProjectInfo pojo);

	ProjectInfo getbykey(String id);
	
	IPage<ProjectInfo> getpage(PageParameter<ProjectInfo> page);

	Integer upStatebykey(UpStateVo upStateVo);

	List<ProjectInfo> getAllPorjectForDomain();

	List<ProjectInfo> getAllPorject();

	/*代码分界end TODO*/

}
