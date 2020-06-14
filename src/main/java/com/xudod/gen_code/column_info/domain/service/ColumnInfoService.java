package com.xudod.gen_code.column_info.domain.service;

import com.xudod.gen_code.column_info.domain.entity.po.ColumnInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xudod.gen_code.common.PageParameter;
import com.xudod.gen_code.common.UpStateVo;
import com.xudod.gen_code.table_base_info.domain.entity.po.TableBaseInfo;

import java.util.List;


/**
 *
 * Created by xuyuzhao on 2020/05/30.
 */
public interface ColumnInfoService {
    
    /*代码分界head TODO*/

	Integer add(ColumnInfo pojo);

	Integer delbykey(String id);

	Integer upbykey(ColumnInfo pojo);

	ColumnInfo getbykey(String id);
	
	IPage<ColumnInfo> getpage(PageParameter<ColumnInfo> page);

	Integer upStatebykey(UpStateVo upStateVo);

	List<ColumnInfo> getAll();

	/*代码分界end TODO*/
	
	List<ColumnInfo> getTableColumnByTableId(ColumnInfo pojo);
	
	List<ColumnInfo> getTableColumnByTableIds(List<TableBaseInfo> tableList);

	List<ColumnInfo> addBatchColumn(List<ColumnInfo> columnInfoList);
	
}
