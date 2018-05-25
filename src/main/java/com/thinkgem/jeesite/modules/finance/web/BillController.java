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
import com.thinkgem.jeesite.modules.finance.service.BillService;
import com.thinkgem.jeesite.modules.oa.entity.OaProject;
import com.thinkgem.jeesite.modules.oa.service.OaProjectService;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * 收费管理Controller
 * @author HEY-Chain
 * @version 2018-03-31
 */
@Controller
@RequestMapping(value = "${adminPath}/finance/bill")
public class BillController extends BaseController {

	@Autowired
	private BillService billService;
	
	@Autowired
	private CrmCustomerService customerService;
	
	@Autowired
	private OaProjectService projectService;

	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public Bill get(@RequestParam(required=false) String id) {
		Bill entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = billService.get(id);
		}
		if (entity == null){
			entity = new Bill();
		}
		return entity;
	}
	
	@RequiresPermissions("finance:bill:view")
	@RequestMapping(value = {"list", ""})
	public String list(Bill bill, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Bill> page = billService.findPage(new Page<Bill>(request, response), bill); 
		model.addAttribute("page", page);
		return "modules/finance/billList";
	}

	@RequiresPermissions("finance:bill:view")
	@RequestMapping(value = "form")
	public String form(Bill bill, Model model) {
		if(bill.getIsNewRecord() && StringUtils.isNotBlank(bill.getCustomerId())) {
			CrmCustomer customer = customerService.get(bill.getCustomerId());
			
			bill.setCustomerName(customer.getCustomerName());
		}
		
		//List<CrmCustomer> customerList = customerService.findList(new CrmCustomer());
		//model.addAttribute("customerList", customerList);
		
		List<User> doctors = systemService.findUserByRoleId(Role.DOCTOR_ROLE_ID);
		model.addAttribute("doctorList", doctors);
		
		List<OaProject> projectList = projectService.findList(new OaProject());
		model.addAttribute("projectList", projectList);
		
		model.addAttribute("bill", bill);
		return "modules/finance/billForm";
	}

	@RequiresPermissions("finance:bill:edit")
	@RequestMapping(value = "save")
	public String save(Bill bill, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bill)){
			return form(bill, model);
		}
		billService.save(bill);
		addMessage(redirectAttributes, "保存收费单成功");
		return "redirect:"+Global.getAdminPath()+"/finance/bill/?repage";
	}
	
	@RequiresPermissions("finance:bill:edit")
	@RequestMapping(value = "delete")
	public String delete(Bill bill, RedirectAttributes redirectAttributes) {
		billService.delete(bill);
		addMessage(redirectAttributes, "删除收费单成功");
		return "redirect:"+Global.getAdminPath()+"/finance/bill/?repage";
	}

}