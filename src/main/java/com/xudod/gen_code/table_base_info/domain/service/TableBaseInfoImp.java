package com.xudod.gen_code.table_base_info.domain.service;

import com.xudod.gen_code.table_base_info.domain.entity.po.TableBaseInfo;
import com.xudod.gen_code.table_base_info.domain.mapper.TableBaseInfoMapper;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import java.util.List;
import org.slf4j.LoggerFactory;
import com.xudod.gen_code.common.BusinessException;
import com.xudod.gen_code.common.GenId;
import com.xudod.gen_code.common.UpStateVo;
import com.xudod.gen_code.common.DataCorrectCheck;
import com.xudod.gen_code.common.PageParameter;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 *
 * Created by xuyuzhao on 2020/06/03.
 */
@Service("tableBaseInfoService")
public class TableBaseInfoImp implements TableBaseInfoService {

	@Value("${snowid.workerId}")
	private long workerId;
	
	@Value("${snowid.datacenterId}")
	private long datacenterId;
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
    private TableBaseInfoMapper tableBaseInfoMapper;
    
    private Logger logger= LoggerFactory.getLogger(TableBaseInfoImp.class);
    
    /*代码分界head TODO*/

	
	@Override
	public Integer add(TableBaseInfo po) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(po, "新增数据时，检测到数据实体为null，保存失败！");
			if(null == po.getId() || "".equals(po.getId())) {
				po.setId(GenId.getUUID(workerId, datacenterId));
			}
			po.setCreateBy(null != request.getHeader("userid") ? request.getHeader("userid") : "");
			po.setSequence(tableBaseInfoMapper.getSequence("table_base_info"));
			int res = tableBaseInfoMapper.insert(po);
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
			int res = tableBaseInfoMapper.deleteById(id);
			addUpDelResCheck(res, "更新数据失败！");
			return res;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Integer upbykey(TableBaseInfo po) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(po, "更新数据时，检测到数据实体为null，更新失败！");
			po.setModifyBy(request.getHeader("userid"));
			int res = tableBaseInfoMapper.updateById(po);
			addUpDelResCheck(res, "更新数据失败！");
			return res;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public TableBaseInfo getbykey(String id) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(id, "查询数据时，检测到查询id为null，查询失败！");
			TableBaseInfo po = tableBaseInfoMapper.selectById(id);
			return po;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public IPage<TableBaseInfo> getpage(PageParameter<TableBaseInfo> page) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(page, "查询数据时，检测到分页对象为null，查询失败！");
			Page<TableBaseInfo> ipage = new Page<TableBaseInfo>();
			BeanUtils.copyProperties(page, ipage);
	        IPage<TableBaseInfo> orgIPage = tableBaseInfoMapper.selectPage(ipage, new QueryWrapper<TableBaseInfo>());
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
			int res = tableBaseInfoMapper.upStatebykey(upStateVo.getId(), upStateVo.getStatus(), header);
			addUpDelResCheck(res, "更新数据失败！");
			return res;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<TableBaseInfo> getAll() {
		List<TableBaseInfo> res = tableBaseInfoMapper.getAll();
		return res;
	}
	
	private void addUpDelResCheck(int res, String msg){
		if(res < 1){
			throw new BusinessException(res, msg);
		}
	}
	
	/*代码分界end TODO*/

	@Override
	public List<TableBaseInfo> getTableByDomainId(String id) {
		List<TableBaseInfo> res = tableBaseInfoMapper.getTableByDomainId(id);
		return res;
	}
	
	@Override
	public String addTableNameInDomain(TableBaseInfo tableBaseInfo) {
//		List<String> existTableName = tableName.getExistTableName();
//		String tableStr = "";
//		for (int i = 0; i < existTableName.size(); i++) {
//			tableStr += existTableName.get(i) + ",";
//		}
//		tableStr += tableName.getTableName();
//		int res = domainInfoMapper.addTableNameInDomain(tableName.getId(), tableStr, "");
		String uuid = "";
		if (null == tableBaseInfo.getId() || "".equals(tableBaseInfo.getId())) {
			uuid = GenId.getUUID(workerId, datacenterId);
			tableBaseInfo.setId(uuid);
		}else {
			uuid = tableBaseInfo.getId();
		}
		Integer res = add(tableBaseInfo);
		return uuid;
	}

	@Override
	public Integer delTableNameInDomain(TableBaseInfo tableBaseInfo) {
		int res = tableBaseInfoMapper.delTableNameInDomain(tableBaseInfo.getTableName(), tableBaseInfo.getDomainId());
		return res;
	}

	@Override
	public List<TableBaseInfo> getTableByDomainIds(List<String> domainList) {
		List<TableBaseInfo> res = tableBaseInfoMapper.getTableByDomainIds(domainList);
		return res;
	}

	
}
