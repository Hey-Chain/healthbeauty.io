/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 医疗项目管理Entity
 * @author HEY-Chain
 * @version 2018-03-21
 */
public class OaProject extends DataEntity<OaProject> {
	
	private static final long serialVersionUID = 1L;
	private String projectCode;		// 项目编号(助记码)
	private String projectName;		// 项目名称
	
	public OaProject() {
		super();
	}

	public OaProject(String id){
		super(id);
	}

	@Length(min=1, max=32, message="项目编号(助记码)长度必须介于 1 和 32 之间")
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@Length(min=1, max=32, message="项目名称长度必须介于 1 和 32 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
}