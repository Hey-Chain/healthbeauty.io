/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cust.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cust.entity.CrmCustomer;
import com.thinkgem.jeesite.modules.cust.dao.CrmCustomerDao;

/**
 * 客户管理Service
 * @author HEY-Chain
 * @version 2018-03-21
 */
@Service
@Transactional(readOnly = true)
public class CrmCustomerService extends CrudService<CrmCustomerDao, CrmCustomer> {

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
	public void delete(CrmCustomer crmCustomer) {
		super.delete(crmCustomer);
	}
	
}