/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.crm.entity.CrmBalanceInOut;
import com.thinkgem.jeesite.modules.crm.entity.CrmMemberCard;
import com.thinkgem.jeesite.modules.crm.dao.CrmBalanceInOutDao;

/**
 * 金额增加流水Service
 * @author HEY-Chain
 * @version 2018-05-22
 */
@Service
@Transactional(readOnly = true)
public class CrmBalanceInOutService extends CrudService<CrmBalanceInOutDao, CrmBalanceInOut> {

	public CrmBalanceInOut get(String id) {
		return super.get(id);
	}
	
	public List<CrmBalanceInOut> findList(CrmBalanceInOut crmBalanceInOut) {
		return super.findList(crmBalanceInOut);
	}
	
	public Page<CrmBalanceInOut> findPage(Page<CrmBalanceInOut> page, CrmBalanceInOut crmBalanceInOut) {
		return super.findPage(page, crmBalanceInOut);
	}
	
	@Transactional(readOnly = false)
	public void save(CrmBalanceInOut crmBalanceInOut) {
		super.save(crmBalanceInOut);
	}
	
	@Transactional(readOnly = false)
	public void delete(CrmBalanceInOut crmBalanceInOut) {
		super.delete(crmBalanceInOut);
	}

	@Transactional(readOnly = false)
	public int deleteByMemberCard(CrmBalanceInOut memberCard) {
		return dao.deleteByMemberCard(memberCard);
	}
}