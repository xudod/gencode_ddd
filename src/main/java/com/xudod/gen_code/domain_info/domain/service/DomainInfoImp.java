package com.xudod.gen_code.domain_info.domain.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xudod.gen_code.common.BusinessException;
import com.xudod.gen_code.common.DataCorrectCheck;
import com.xudod.gen_code.common.GenId;
import com.xudod.gen_code.common.PageParameter;
import com.xudod.gen_code.domain_info.domain.entity.po.DomainInfo;
import com.xudod.gen_code.domain_info.domain.mapper.DomainInfoMapper;
import com.xudod.gen_code.project_info.interfaces.vo.UpStateVo;
import com.xudod.gen_code.table_base_info.domain.entity.po.TableBaseInfo;
import com.xudod.gen_code.table_base_info.domain.service.TableBaseInfoService;

/**
 *
 * Created by xuyuzhao on 2020/04/11.
 */
@Service("domainInfoService")
public class DomainInfoImp implements DomainInfoService {

	@Value("${snowid.workerId}")
	private long workerId;

	@Value("${snowid.datacenterId}")
	private long datacenterId;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private DomainInfoMapper domainInfoMapper;
	
	@Autowired
	private TableBaseInfoService tableBaseInfoService;

	/* 代码分界head TODO */

	@Override
	public Integer add(DomainInfo po) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(po, "新增数据时，检测到数据实体为null，保存失败！");
			if (null == po.getId() || "".equals(po.getId())) {
				po.setId(GenId.getUUID(workerId, datacenterId));
			}
			po.setCreateBy(null != request.getHeader("userid") ? request.getHeader("userid") : "");
			po.setSequence(domainInfoMapper.getSequence("domain_info"));
			int res = domainInfoMapper.insert(po);
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
			int res = domainInfoMapper.deleteById(id);
			addUpDelResCheck(res, "更新数据失败！");
			return res;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Integer upbykey(DomainInfo po) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(po, "更新数据时，检测到数据实体为null，更新失败！");
			po.setModifyBy(request.getHeader("userid"));
			int res = domainInfoMapper.updateById(po);
			addUpDelResCheck(res, "更新数据失败！");
			return res;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public DomainInfo getbykey(String id) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(id, "查询数据时，检测到查询id为null，查询失败！");
			DomainInfo po = domainInfoMapper.selectById(id);
			List<TableBaseInfo> tableByDomainId = tableBaseInfoService.getTableByDomainId(id);
			String tableListStr = "";
			for (TableBaseInfo tableBaseInfo : tableByDomainId) {
				tableListStr += tableBaseInfo.getTableName() + ",";
			}
			tableListStr = tableListStr.substring(0, tableListStr.length() - 1);
			po.setDbTableList(tableListStr);
			return po;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public IPage<DomainInfo> getpage(PageParameter<DomainInfo> page) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(page, "查询数据时，检测到分页对象为null，查询失败！");
			Page<DomainInfo> ipage = new Page<DomainInfo>();
			BeanUtils.copyProperties(page, ipage);
			IPage<DomainInfo> orgIPage = domainInfoMapper.selectPage(ipage, new QueryWrapper<DomainInfo>());
			return orgIPage;
		} catch (Exception e) {
			throw e;
		}

	}

	private void addUpDelResCheck(int res, String msg) {
		if (res < 1) {
			throw new BusinessException(res, msg);
		}
	}

	/* 代码分界end TODO */

	@Override
	public Integer upStatebykey(UpStateVo upStateVo) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(upStateVo, "更新数据时，检测到数据实体为null，更新失败！");
			String header = null != request.getHeader("userid") ? request.getHeader("userid") : "reqHeaderNull";
			int res = domainInfoMapper.upStatebykey(upStateVo.getId(), upStateVo.getUseStatus(), header);
			addUpDelResCheck(res, "更新数据失败！");
			return res;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<DomainInfo> getDomainListByProjectId(String id) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(id, "查询数据时，检测到查询id为null，查询失败！");
			List<DomainInfo> domainInfoList = domainInfoMapper.getDomainListByProjectId(id);
			return domainInfoList;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<TableBaseInfo> getTableInfo(String id) {
		try {
			List<TableBaseInfo> tableByDomainId = tableBaseInfoService.getTableByDomainId(id);
//			String tableStr = domainInfoMapper.getTableInfo(id);
//			String[] split = tableStr.split(",");
//			ArrayList<String> arrayList = new ArrayList<String>();
//			for (int i = 0; i < tableByDomainId.size(); i++) {
//				arrayList.add(tableByDomainId.get(i).getTableName());
//			}
			return tableByDomainId;
		} catch (Exception e) {
			throw e;
		}
	}

}
