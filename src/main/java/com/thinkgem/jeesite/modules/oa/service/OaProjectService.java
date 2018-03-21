/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.OaProject;
import com.thinkgem.jeesite.modules.oa.dao.OaProjectDao;

/**
 * 医疗项目管理Service
 * @author HEY-Chain
 * @version 2018-03-21
 */
@Service
@Transactional(readOnly = true)
public class OaProjectService extends CrudService<OaProjectDao, OaProject> {

	public OaProject get(String id) {
		return super.get(id);
	}
	
	public List<OaProject> findList(OaProject oaProject) {
		return super.findList(oaProject);
	}
	
	public Page<OaProject> findPage(Page<OaProject> page, OaProject oaProject) {
		return super.findPage(page, oaProject);
	}
	
	@Transactional(readOnly = false)
	public void save(OaProject oaProject) {
		super.save(oaProject);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaProject oaProject) {
		super.delete(oaProject);
	}
	
}