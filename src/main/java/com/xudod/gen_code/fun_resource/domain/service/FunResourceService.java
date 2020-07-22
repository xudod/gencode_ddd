package com.xudod.gen_code.fun_resource.domain.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xudod.gen_code.common.PageParameter;
import com.xudod.gen_code.fun_resource.domain.entity.po.FunResource;
import com.xudod.gen_code.fun_resource.interfaces.vo.FunResourceTreeVo;


/**
 *
 * Created by xudod on 2020/04/25.
 */
public interface FunResourceService {
    
    /*代码分界head TODO*/

	Integer add(FunResource pojo);

	Integer delbykey(String id);

	Integer upbykey(FunResource pojo);

	FunResource getbykey(String id);
	
	IPage<FunResource> getpage(PageParameter<FunResource> page);

	/*代码分界end TODO*/
	
	List<FunResource> getAll();
	
	List<FunResourceTreeVo> getalltree(String sysCode);
}
