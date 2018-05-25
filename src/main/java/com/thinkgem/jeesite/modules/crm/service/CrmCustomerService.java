/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.crm.dao.CrmCustomerDao;
import com.thinkgem.jeesite.modules.crm.entity.CrmBalanceInOut;
import com.thinkgem.jeesite.modules.crm.entity.CrmCustomer;
import com.thinkgem.jeesite.modules.crm.entity.CrmMemberCard;
import com.thinkgem.jeesite.modules.crm.utils.CustomerUtils;

/**
 * 客户管理Service
 * @author HEY-Chain
 * @version 2018-03-21
 */
@Service
@Transactional(readOnly = true)
public class CrmCustomerService extends CrudService<CrmCustomerDao, CrmCustomer> {

	@Autowired
	private CrmMemberCardService memberCardService;

	@Autowired
	private CrmBalanceInOutService balanceInOutService;
	
	public CrmCustomer get(String id) {
		return super.get(id);
	}
	
	public List<CrmCustomer> findList(CrmCustomer crmCustomer) {
		return super.findList(crmCustomer);
	}
	
	public Page<CrmCustomer> findPage(Page<CrmCustomer> page, CrmCustomer crmCustomer) {
		return super.findPage(page, crmCustomer);
	}
	
	@Transactional(readOnly = false)
	public void save(CrmCustomer crmCustomer) {
		super.save(crmCustomer);
	}
	
	@Transactional(readOnly = false)
	public void linkMemberCard(CrmCustomer customer, CrmMemberCard memberCard) {
		memberCardService.save(memberCard);
		super.save(customer);
	}
	
	@Transactional(readOnly = false)
	public void delete(CrmCustomer crmCustomer) {
		super.delete(crmCustomer);
	}
	
	public List<CrmCustomer> findCustomerByGroup(String customerGroupKey) {
		return (List<CrmCustomer>)CustomerUtils.getByCustomerGroupKey(customerGroupKey);
	}

	/**
	 * 退卡：
	 * 1. 结算客户的余额 - 大与0表示客户有于额，小于0则表示有欠款
	 * 2. 逻辑删除相关记录
	 * 3。  移除客户会员卡关系
	 * */
	@Transactional(readOnly = false)
	public void closeMemberCard(CrmCustomer cust, CrmMemberCard memberCard, Double clearAmount) {
		// 结算客户的余额 余
		CrmBalanceInOut balanceInOut = new CrmBalanceInOut();
		balanceInOut.setMemberCardId(memberCard.getId());
		balanceInOut.setDelFlag("0");
		balanceInOut.setChangeAmount(Math.abs(clearAmount));		
		balanceInOut.setOperatetType(clearAmount > 0 ? 0: 1);
		balanceInOut.setRemarks("退卡清款");
		balanceInOutService.save(balanceInOut);
		
		// 清除相关记录
		balanceInOutService.deleteByMemberCard(balanceInOut);
		
		// 移除关系
		memberCard.setUseFlag("0");
		memberCardService.save(memberCard);
		
		cust.setMemberCardId("");
		cust.setMemberCardNumber("");
		dao.update(cust);
	}
	
}