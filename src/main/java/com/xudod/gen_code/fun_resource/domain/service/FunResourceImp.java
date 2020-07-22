package com.xudod.gen_code.fun_resource.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xudod.gen_code.common.BusinessException;
import com.xudod.gen_code.common.DataCorrectCheck;
import com.xudod.gen_code.common.GenId;
import com.xudod.gen_code.common.PageParameter;
import com.xudod.gen_code.fun_resource.domain.entity.po.FunResource;
import com.xudod.gen_code.fun_resource.domain.mapper.FunResourceMapper;
import com.xudod.gen_code.fun_resource.interfaces.vo.FunResourceTreeVo;

/**
 *
 * Created by xudod on 2020/04/25.
 */
@Service("funResourceService")
public class FunResourceImp implements FunResourceService {

	@Value("${snowid.workerId}")
	private long workerId;
	
	@Value("${snowid.datacenterId}")
	private long datacenterId;
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
    private FunResourceMapper funResourceMapper;
    
    private Logger logger= LoggerFactory.getLogger(FunResourceImp.class);
    
    /*代码分界head TODO*/
	
	@Override
	public Integer add(FunResource po) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(po, "新增数据时，检测到数据实体为null，保存失败！");
			if(null == po.getId() || "".equals(po.getId())) {
				po.setId(GenId.getUUID(workerId, datacenterId));
			}
			po.setCreateBy(null != request.getHeader("userid") ? request.getHeader("userid") : "");
			po.setSequence(funResourceMapper.getSequence("fun_resource"));
			int res = funResourceMapper.insert(po);
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
			int res = funResourceMapper.deleteById(id);
			addUpDelResCheck(res, "更新数据失败！");
			return res;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Integer upbykey(FunResource po) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(po, "更新数据时，检测到数据实体为null，更新失败！");
			po.setModifyBy(request.getHeader("userid"));
			int res = funResourceMapper.updateById(po);
			addUpDelResCheck(res, "更新数据失败！");
			return res;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public FunResource getbykey(String id) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(id, "查询数据时，检测到查询id为null，查询失败！");
			FunResource po = funResourceMapper.selectById(id);
			return po;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public IPage<FunResource> getpage(PageParameter<FunResource> page) {
		try {
			DataCorrectCheck.PojoIsNotNullCheck(page, "查询数据时，检测到分页对象为null，查询失败！");
			Page<FunResource> ipage = new Page<FunResource>();
			BeanUtils.copyProperties(page, ipage);
	        IPage<FunResource> orgIPage = funResourceMapper.selectPage(ipage, new QueryWrapper<FunResource>());
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
	public List<FunResource> getAll() {
		List<FunResource> res = funResourceMapper.getAll();
		return res;
	}

	@Override
	public List<FunResourceTreeVo> getalltree(String sysCode) {
		List<FunResource> all = funResourceMapper.getAllOnlyMenu(sysCode);
		List<FunResource> funResourceRoot = all.stream().filter(x -> (null == x.getPid() || "0".equals(x.getPid()))).collect(Collectors.toList());
		List<FunResourceTreeVo> createTree = createTree(all, funResourceRoot);
		return createTree;
	}

	private List<FunResourceTreeVo> createTree(List<FunResource> all, List<FunResource> funResourceRoot) {
		List<FunResourceTreeVo> funResourceTreeVoList = new ArrayList<FunResourceTreeVo>();
		FunResourceTreeVo funResourceTreeVo = new FunResourceTreeVo();
		for (FunResource funR : funResourceRoot) {
			funResourceTreeVo = new FunResourceTreeVo();
			BeanUtils.copyProperties(funR, funResourceTreeVo);
			List<FunResource> collect1 = all.stream().filter(x -> (funR.getCode().equals(x.getPid()))).collect(Collectors.toList());
			if(null != collect1 && collect1.size() > 0) {
				List<FunResourceTreeVo> createTree = createTree(all, collect1);
				funResourceTreeVo.setFunResourceTreeVoList(createTree);
			}
			funResourceTreeVoList.add(funResourceTreeVo);
		}
		return funResourceTreeVoList;
	}
	
}
