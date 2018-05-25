/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crm.web;

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
import com.thinkgem.jeesite.modules.crm.entity.CrmBalanceInOut;
import com.thinkgem.jeesite.modules.crm.service.CrmBalanceInOutService;

/**
 * 金额增加流水Controller
 * @author HEY-Chain
 * @version 2018-05-22
 */
@Controller
@RequestMapping(value = "${adminPath}/crm/crmBalanceInOut")
public class CrmBalanceInOutController extends BaseController {

	@Autowired
	private CrmBalanceInOutService crmBalanceInOutService;
	
	@ModelAttribute
	public CrmBalanceInOut get(@RequestParam(required=false) String id) {
		CrmBalanceInOut entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = crmBalanceInOutService.get(id);
		}
		if (entity == null){
			entity = new CrmBalanceInOut();
		}
		return entity;
	}
	
	@RequiresPermissions("crm:crmBalanceInOut:view")
	@RequestMapping(value = {"list", ""})
	public String list(CrmBalanceInOut crmBalanceInOut, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CrmBalanceInOut> page = crmBalanceInOutService.findPage(new Page<CrmBalanceInOut>(request, response), crmBalanceInOut); 
		model.addAttribute("page", page);
		return "modules/crm/crmBalanceInOutList";
	}

	@RequiresPermissions("crm:crmBalanceInOut:view")
	@RequestMapping(value = "form")
	public String form(CrmBalanceInOut crmBalanceInOut, Model model) {
		model.addAttribute("crmBalanceInOut", crmBalanceInOut);
		return "modules/crm/crmBalanceInOutForm";
	}

	@RequiresPermissions("crm:crmBalanceInOut:edit")
	@RequestMapping(value = "save")
	public String save(CrmBalanceInOut crmBalanceInOut, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, crmBalanceInOut)){
			return form(crmBalanceInOut, model);
		}
		crmBalanceInOutService.save(crmBalanceInOut);
		addMessage(redirectAttributes, "保存增加流水成功");
		return "redirect:"+Global.getAdminPath()+"/crm/crmBalanceInOut/?repage";
	}
	
	@RequiresPermissions("crm:crmBalanceInOut:edit")
	@RequestMapping(value = "delete")
	public String delete(CrmBalanceInOut crmBalanceInOut, RedirectAttributes redirectAttributes) {
		crmBalanceInOutService.delete(crmBalanceInOut);
		addMessage(redirectAttributes, "删除增加流水成功");
		return "redirect:"+Global.getAdminPath()+"/crm/crmBalanceInOut/?repage";
	}

}