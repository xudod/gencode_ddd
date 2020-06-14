package com.xudod.gen_code.project_info.domain.service;

import com.xudod.gen_code.project_info.domain.entity.po.ProjectInfo;
import com.xudod.gen_code.project_info.domain.mapper.ProjectInfoMapper;
import com.xudod.gen_code.project_info.interfaces.vo.UpStateVo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.xudod.gen_code.common.BusinessException;
import com.xudod.gen_code.common.GenId;
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
 * Created by xuyuzhao on 2020/04/11.
 */
@Service("projectInfoService")
public class ProjectInfoImp implements ProjectInfoService {

	@Value("${snowid.workerId}")
	private long workerId;
	
	@Value("${snowid.datacenterId}")
	private long datacenterId;
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
    private ProjectInfoMapper projectInfoMapper;
    
    private Logger logger= LoggerFactory.getLogger(ProjectInfoImp.class);
    
    /*代码分界head TODO*/

	
	@Override
	public Integer add(ProjectInfo po) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(po, "新增数据时，检测到数据实体为null，保存失败！");
			if(null == po.getId() || "".equals(po.getId())) {
				po.setId(GenId.getUUID(workerId, datacenterId));
			}
			po.setCreateBy(null != request.getHeader("userid") ? request.getHeader("userid") : "");
			po.setSequence(projectInfoMapper.getSequence("project_info"));
			int res = projectInfoMapper.insert(po);
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
			int res = projectInfoMapper.deleteById(id);
			addUpDelResCheck(res, "更新数据失败！");
			return res;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Integer upbykey(ProjectInfo po) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(po, "更新数据时，检测到数据实体为null，更新失败！");
			po.setModifyBy(request.getHeader("userid"));
			int res = projectInfoMapper.updateById(po);
			addUpDelResCheck(res, "更新数据失败！");
			return res;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ProjectInfo getbykey(String id) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(id, "查询数据时，检测到查询id为null，查询失败！");
			ProjectInfo po = projectInfoMapper.selectById(id);
			return po;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public IPage<ProjectInfo> getpage(PageParameter<ProjectInfo> page) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(page, "查询数据时，检测到分页对象为null，查询失败！");
			Page<ProjectInfo> ipage = new Page<ProjectInfo>();
			BeanUtils.copyProperties(page, ipage);
	        IPage<ProjectInfo> orgIPage = projectInfoMapper.selectPage(ipage, new QueryWrapper<ProjectInfo>());
	        return orgIPage;
		} catch (Exception e) {
			throw e;
		}

	}
	
	private void addUpDelResCheck(int res, String msg){
		if(res < 1){
			throw new BusinessException(res, msg);
		}
	}
	
	
	/*代码分界end TODO*/


	@Override
	public Integer upStatebykey(UpStateVo upStateVo) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(upStateVo, "更新数据时，检测到数据实体为null，更新失败！");
			String header = null != request.getHeader("userid") ? request.getHeader("userid") : "reqHeaderNull";
			int res = projectInfoMapper.upStatebykey(upStateVo.getId(), upStateVo.getUseStatus(), header);
			addUpDelResCheck(res, "更新数据失败！");
			return res;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<ProjectInfo> getAllPorjectForDomain() {
		List<ProjectInfo> projectList = projectInfoMapper.getAllPorjectForDomain();
		return projectList;
	}
	
	@Override
	public List<ProjectInfo> getAllPorject() {
		List<ProjectInfo> projectList = projectInfoMapper.getAllPorject();
		return projectList;
	}
	
}
