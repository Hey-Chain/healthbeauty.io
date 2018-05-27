/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.finance.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.crm.entity.CrmCustomer;
import com.thinkgem.jeesite.modules.crm.service.CrmCustomerService;
import com.thinkgem.jeesite.modules.finance.entity.Bill;
import com.thinkgem.jeesite.modules.finance.entity.Billitem;
import com.thinkgem.jeesite.modules.finance.entity.Payment;
import com.thinkgem.jeesite.modules.finance.service.BillService;
import com.thinkgem.jeesite.modules.finance.service.PaymentService;

/**
 * 付款管理Controller
 * @author HEY-Chain
 * @version 2018-04-02
 */
@Controller
@RequestMapping(value = "${adminPath}/finance/payment")
public class PaymentController extends BaseController {

	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private CrmCustomerService customerService;
		
	@ModelAttribute
	public Payment get(@RequestParam(required=false) String id) {
		Payment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = paymentService.get(id);
		}
		if (entity == null){
			entity = new Payment();
		}
		return entity;
	}
	
	@RequiresPermissions("finance:payment:view")
	@RequestMapping(value = {"list", ""})
	public String list(Payment payment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Payment> page = paymentService.findPage(new Page<Payment>(request, response), payment); 
		model.addAttribute("page", page);
		return "modules/finance/paymentList";
	}

	/**
	 * TODO: 给InOut表添加模块，用于标记什么业务导致的余额变化
	 * @param payment
	 * @param model
	 * @return
	 */
	@RequiresPermissions("finance:payment:view")
	@RequestMapping(value = "form")
	public String form(Payment payment, Model model) {
		if(payment.getIsNewRecord() && StringUtils.isNotBlank(payment.getBillId())) {
			Bill bill = billService.get(payment.getBillId());
			payment.setBillId(bill.getId());
			payment.setBillNumber(bill.getBillNumber());
			
			CrmCustomer cust = customerService.get(bill.getCustomerId());
			payment.setCustomerName(cust.getCustomerName());
			payment.setMemberCardId(cust.getMemberCardId());
			payment.setMemberCard(cust.getMemberCardNumber());

			List<Billitem> itemList = bill.getBillitemList();			
			Double payable = 0.0;
			for (Billitem billitem : itemList) {
				payable += billitem.getDealprice();
			}
			payment.setPayable(payable);
		}
		
		model.addAttribute("payment", payment);
		return "modules/finance/paymentForm";
	}

	@RequiresPermissions("finance:payment:edit")
	@RequestMapping(value = "save")
	public String save(Payment payment, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, payment)){
			return form(payment, model);
		}

		paymentService.save(payment);
		addMessage(redirectAttributes, "保存付款单成功");
		return "redirect:"+Global.getAdminPath()+"/finance/payment/?repage";
	}
	
	@RequiresPermissions("finance:payment:edit")
	@RequestMapping(value = "delete")
	public String delete(Payment payment, RedirectAttributes redirectAttributes) {
		paymentService.delete(payment);
		addMessage(redirectAttributes, "删除付款单成功");
		return "redirect:"+Global.getAdminPath()+"/finance/payment/?repage";
	}

}