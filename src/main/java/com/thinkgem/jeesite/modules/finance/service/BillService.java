/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.finance.entity.Bill;
import com.thinkgem.jeesite.modules.finance.dao.BillDao;
import com.thinkgem.jeesite.modules.finance.entity.Billitem;
import com.thinkgem.jeesite.modules.finance.dao.BillitemDao;

/**
 * 收费管理Service
 * @author HEY-Chain
 * @version 2018-03-31
 */
@Service
@Transactional(readOnly = true)
public class BillService extends CrudService<BillDao, Bill> {

	@Autowired
	private BillitemDao billitemDao;
	
	public Bill get(String id) {
		Bill bill = super.get(id);
		bill.setBillitemList(billitemDao.findList(new Billitem(bill)));
		return bill;
	}
	
	public List<Bill> findList(Bill bill) {
		return super.findList(bill);
	}
	
	public Page<Bill> findPage(Page<Bill> page, Bill bill) {
		return super.findPage(page, bill);
	}
	
	@Transactional(readOnly = false)
	public void save(Bill bill) {
		super.save(bill);
		for (Billitem billitem : bill.getBillitemList()){
			if (billitem.getId() == null){
				continue;
			}
			if (Billitem.DEL_FLAG_NORMAL.equals(billitem.getDelFlag())){
				if (StringUtils.isBlank(billitem.getId())){
					billitem.setBill(bill);
					billitem.preInsert();
					billitemDao.insert(billitem);
				}else{
					billitem.preUpdate();
					billitemDao.update(billitem);
				}
			}else{
				billitemDao.delete(billitem);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Bill bill) {
		super.delete(bill);
		billitemDao.delete(new Billitem(bill));
	}
	
}