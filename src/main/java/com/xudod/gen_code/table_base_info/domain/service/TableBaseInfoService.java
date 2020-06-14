package com.xudod.gen_code.table_base_info.domain.service;

import com.xudod.gen_code.table_base_info.domain.entity.po.TableBaseInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xudod.gen_code.common.PageParameter;
import com.xudod.gen_code.common.UpStateVo;

import java.util.List;


/**
 *
 * Created by xuyuzhao on 2020/06/03.
 */
public interface TableBaseInfoService {
    
    /*代码分界head TODO*/


	Integer add(TableBaseInfo pojo);

	Integer delbykey(String id);

	Integer upbykey(TableBaseInfo pojo);

	TableBaseInfo getbykey(String id);
	
	IPage<TableBaseInfo> getpage(PageParameter<TableBaseInfo> page);

	Integer upStatebykey(UpStateVo upStateVo);

	List<TableBaseInfo> getAll();

	/*代码分界end TODO*/

	List<TableBaseInfo> getTableByDomainId(String id);

	List<TableBaseInfo> getTableByDomainIds(List<String> domainList);

	String addTableNameInDomain(TableBaseInfo tableBaseInfo);

	Integer delTableNameInDomain(TableBaseInfo tableBaseInfo);
	
}
