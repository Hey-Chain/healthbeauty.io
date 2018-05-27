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
import com.thinkgem.jeesite.modules.finance.entity.Bill;
import com.thinkgem.jeesite.modules.finance.entity.Payment;
import com.thinkgem.jeesite.modules.crm.entity.CrmBalanceInOut;
import com.thinkgem.jeesite.modules.crm.service.CrmBalanceInOutService;
import com.thinkgem.jeesite.modules.finance.dao.PaymentDao;

/**
 * 付款管理Service
 * @author HEY-Chain
 * @version 2018-04-02
 */
@Service
@Transactional(readOnly = true)
public class PaymentService extends CrudService<PaymentDao, Payment> {

	@Autowired
	private BillService billService;

	@Autowired
	private CrmBalanceInOutService balanceInOutService;
	
	public Payment get(String id) {
		return super.get(id);
	}
	
	public List<Payment> findList(Payment payment) {
		return super.findList(payment);
	}
	
	public Page<Payment> findPage(Page<Payment> page, Payment payment) {
		return super.findPage(page, payment);
	}
	
	@Transactional(readOnly = false)
	public void save(Payment payment) {

		Bill payBill = billService.get(payment.getBillId());
		payBill.setIsPaid("1");
		billService.save(payBill);

		CrmBalanceInOut outModel = new CrmBalanceInOut();
		outModel.setChangeAmount(payment.getAmount());
		outModel.setMemberCardId(payment.getMemberCardId());
		outModel.setOperatetType(0);
		balanceInOutService.save(outModel);
		
		super.save(payment);
	}
	
	@Transactional(readOnly = false)
	public void delete(Payment payment) {
		super.delete(payment);
	}
	
}