/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.crm.entity.CrmMemberCard;
import com.thinkgem.jeesite.modules.crm.utils.CrmUtils;
import com.thinkgem.jeesite.modules.crm.dao.CrmMemberCardDao;

/**
 * 会员卡管理Service
 * @author HEY-Chain
 * @version 2018-05-13
 */
@Service
@Transactional(readOnly = true)
public class CrmMemberCardService extends CrudService<CrmMemberCardDao, CrmMemberCard> {

	public CrmMemberCard get(String id) {
		return super.get(id);
	}
	
	public List<CrmMemberCard> findList(CrmMemberCard crmMemberCard) {
		return super.findList(crmMemberCard);
	}
	
	public Page<CrmMemberCard> findPage(Page<CrmMemberCard> page, CrmMemberCard crmMemberCard) {
		return super.findPage(page, crmMemberCard);
	}
	
	@Transactional(readOnly = false)
	public void save(CrmMemberCard crmMemberCard) {
		super.save(crmMemberCard);
		CrmUtils.removeCache("crmCardList"+crmMemberCard.getUseFlag());
	}
	
	@Transactional(readOnly = false)
	public void delete(CrmMemberCard crmMemberCard) {
		super.delete(crmMemberCard);
	}
}