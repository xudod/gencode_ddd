package com.xudod.gen_code.domain_info.domain.service;

import com.xudod.gen_code.domain_info.domain.entity.po.DomainInfo;
import com.xudod.gen_code.project_info.interfaces.vo.UpStateVo;
import com.xudod.gen_code.table_base_info.domain.entity.po.TableBaseInfo;

import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xudod.gen_code.common.PageParameter;


/**
 *
 * Created by xuyuzhao on 2020/04/11.
 */
public interface DomainInfoService {
    
    /*代码分界head TODO*/

	Integer add(DomainInfo pojo);

	Integer delbykey(String id);

	Integer upbykey(DomainInfo pojo);

	DomainInfo getbykey(String id);
	
	IPage<DomainInfo> getpage(PageParameter<DomainInfo> page);

	List<DomainInfo> getDomainListByProjectId(String id);

	Integer upStatebykey(UpStateVo upStateVo);

	/*代码分界end TODO*/
	
	List<TableBaseInfo> getTableInfo(String id);
}
