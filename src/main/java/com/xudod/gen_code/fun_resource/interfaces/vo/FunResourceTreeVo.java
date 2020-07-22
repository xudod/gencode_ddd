package com.xudod.gen_code.fun_resource.interfaces.vo;

import java.util.List;

public class FunResourceTreeVo {
	
	private String id;

    private String showNameCn;

    private String code;

    private String url;

    private String pid;
    
    private List<FunResourceTreeVo> funResourceTreeVoList;

	public String getShowNameCn() {
		return showNameCn;
	}

	public void setShowNameCn(String showNameCn) {
		this.showNameCn = showNameCn;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<FunResourceTreeVo> getFunResourceTreeVoList() {
		return funResourceTreeVoList;
	}

	public void setFunResourceTreeVoList(List<FunResourceTreeVo> funResourceTreeVoList) {
		this.funResourceTreeVoList = funResourceTreeVoList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
