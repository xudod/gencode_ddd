package com.xudod.gen_code.column_info.domain.service;

import com.xudod.gen_code.column_info.domain.entity.po.ColumnInfo;
import com.xudod.gen_code.column_info.domain.mapper.ColumnInfoMapper;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import java.util.List;
import org.slf4j.LoggerFactory;
import com.xudod.gen_code.common.BusinessException;
import com.xudod.gen_code.common.GenId;
import com.xudod.gen_code.common.UpStateVo;
import com.xudod.gen_code.table_base_info.domain.entity.po.TableBaseInfo;
import com.xudod.gen_code.common.DataCorrectCheck;
import com.xudod.gen_code.common.PageParameter;
import com.xudod.gen_code.common.ResultCodeEnum;

import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 *
 * Created by xuyuzhao on 2020/05/30.
 */
@Service("columnInfoService")
public class ColumnInfoImp implements ColumnInfoService {

	@Value("${snowid.workerId}")
	private long workerId;
	
	@Value("${snowid.datacenterId}")
	private long datacenterId;
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
    private ColumnInfoMapper columnInfoMapper;
    
    private Logger logger= LoggerFactory.getLogger(ColumnInfoImp.class);
    
    /*代码分界head TODO*/
	
	@Override
	public Integer add(ColumnInfo po) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(po, "新增数据时，检测到数据实体为null，保存失败！");
			int nameRepeatCheck = columnInfoMapper.nameRepeatCheck(po.getName(), po.getTableId());
			if(nameRepeatCheck > 0) {
				throw new BusinessException(ResultCodeEnum.EXIST_REPEAT_DATA);
			}
			if(null == po.getId() || "".equals(po.getId())) {
				po.setId(GenId.getUUID(workerId, datacenterId));
			}
			po.setCreateBy(null != request.getHeader("userid") ? request.getHeader("userid") : "");
			if(null == po.getSequence()) {
				po.setSequence(columnInfoMapper.getSequence("column_info"));
			}
			int res = columnInfoMapper.insert(po);
			addUpDelResCheck(res, "更新数据失败！");
			return res;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Integer delbykey(String id) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(id, "删除数据时，检测到id字符串值为null，删除失败！");
			int res = columnInfoMapper.deleteById(id);
			addUpDelResCheck(res, "更新数据失败！");
			return res;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Integer upbykey(ColumnInfo po) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(po, "更新数据时，检测到数据实体为null，更新失败！");
			po.setModifyBy(request.getHeader("userid"));
			int res = columnInfoMapper.updateById(po);
			addUpDelResCheck(res, "更新数据失败！");
			return res;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ColumnInfo getbykey(String id) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(id, "查询数据时，检测到查询id为null，查询失败！");
			ColumnInfo po = columnInfoMapper.selectById(id);
			return po;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public IPage<ColumnInfo> getpage(PageParameter<ColumnInfo> page) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(page, "查询数据时，检测到分页对象为null，查询失败！");
			Page<ColumnInfo> ipage = new Page<ColumnInfo>();
			BeanUtils.copyProperties(page, ipage);
	        IPage<ColumnInfo> orgIPage = columnInfoMapper.selectPage(ipage, new QueryWrapper<ColumnInfo>());
	        return orgIPage;
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public Integer upStatebykey(UpStateVo upStateVo) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(upStateVo, "更新数据时，检测到数据实体为null，更新失败！");
			String header = null != request.getHeader("userid") ? request.getHeader("userid") : "reqHeaderNull";
			int res = columnInfoMapper.upStatebykey(upStateVo.getId(), upStateVo.getStatus(), header);
			addUpDelResCheck(res, "更新数据失败！");
			return res;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<ColumnInfo> getAll() {
		List<ColumnInfo> res = columnInfoMapper.getAll();
		return res;
	}
	
	private void addUpDelResCheck(int res, String msg){
		if(res < 1){
			throw new BusinessException(res, msg);
		}
	}

	/*代码分界end TODO*/
	
	@Override
	public List<ColumnInfo> getTableColumnByTableId(ColumnInfo pojo) {
		List<ColumnInfo> tableColumnByDomainWithTable = columnInfoMapper.getTableColumnByTableId(pojo.getTableId());
		return tableColumnByDomainWithTable;
	}

	@Override
	public List<ColumnInfo> addBatchColumn(List<ColumnInfo> columnInfoList) {
		String tableId = columnInfoList.get(0).getTableId();
		for (ColumnInfo columnInfo : columnInfoList) {
			add(columnInfo);
		}
		List<ColumnInfo> res = columnInfoMapper.getTableColumnByTableId(tableId);
		return res;
	}

	@Override
	public List<ColumnInfo> getTableColumnByTableIds(List<TableBaseInfo> tableList) {
		List<ColumnInfo> tableColumnByDomainWithTable = columnInfoMapper.getTableColumnByTableIds(tableList);
		return tableColumnByDomainWithTable;
	}
	
}
