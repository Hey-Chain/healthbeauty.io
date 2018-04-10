/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cust.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.cust.entity.CrmCustomer;
import com.thinkgem.jeesite.modules.cust.service.CrmCustomerService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.DictService;

/**
 * 客户管理Controller
 * @author HEY-Chain
 * @version 2018-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/cust/crmCustomer")
public class CrmCustomerController extends BaseController {

	@Autowired
	private CrmCustomerService crmCustomerService;
	
	@Autowired
	private DictService dictService;
	
	@ModelAttribute
	public CrmCustomer get(@RequestParam(required=false) String id) {
		CrmCustomer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = crmCustomerService.get(id);
		}
		if (entity == null){
			entity = new CrmCustomer();
		}
		return entity;
	}
	
	@RequiresPermissions("cust:crmCustomer:view")
	@RequestMapping(value = {"list", ""})
	public String list(CrmCustomer crmCustomer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CrmCustomer> page = crmCustomerService.findPage(new Page<CrmCustomer>(request, response), crmCustomer); 
		model.addAttribute("page", page);
		return "modules/cust/crmCustomerList";
	}

	@RequiresPermissions("cust:crmCustomer:view")
	@RequestMapping(value = "form")
	public String form(CrmCustomer crmCustomer, Model model) {
		model.addAttribute("crmCustomer", crmCustomer);
		return "modules/cust/crmCustomerForm";
	}

	@RequiresPermissions("cust:crmCustomer:edit")
	@RequestMapping(value = "save")
	public String save(CrmCustomer crmCustomer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, crmCustomer)){
			return form(crmCustomer, model);
		}
		crmCustomerService.save(crmCustomer);
		addMessage(redirectAttributes, "保存客户信息成功");
		return "redirect:"+Global.getAdminPath()+"/cust/crmCustomer/?repage";
	}
	
	@RequiresPermissions("cust:crmCustomer:edit")
	@RequestMapping(value = "delete")
	public String delete(CrmCustomer crmCustomer, RedirectAttributes redirectAttributes) {
		crmCustomerService.delete(crmCustomer);
		addMessage(redirectAttributes, "删除客户信息成功");
		return "redirect:"+Global.getAdminPath()+"/cust/crmCustomer/?repage";
	}

	/**
	 * 获取机构JSON数据。
	 * @param customerGroupKey 客户组Key
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String customerGroupKey,
			//@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			//@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, 
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		
		Dict dict = new Dict();
		dict.setDelFlag("0");
		dict.setType("customer_group");
		List<Dict> dictList = dictService.findList(dict);
		List<CrmCustomer> customerList = crmCustomerService.findCustomerByGroup(customerGroupKey);
		
		for (int i=0; i<dictList.size(); i ++) {
			Dict d = dictList.get(i);
			
			Map<String, Object> map = Maps.newHashMap();
			map.put("pId", 0);
			map.put("id", d.getValue());
			map.put("name", org.apache.commons.lang3.StringUtils.replace(d.getLabel(), " ", ""));
			map.put("open", "false");
			map.put("isParent", "true");
			mapList.add(map);
		}

		for (int j=0; j<customerList.size(); j++){
			Map<String, Object> map = Maps.newHashMap();
			
			CrmCustomer e = customerList.get(j);
			map.put("id", e.getId());
			map.put("pId", e.getCustomerGroup());
			map.put("name", org.apache.commons.lang3.StringUtils.replace(e.getCustomerName(), " ", ""));
			map.put("open", "false");
			mapList.add(map);
		}
		
		return mapList;
	}

	@ResponseBody
	@RequestMapping(value = "getByMemberCard")
	public CrmCustomer getByMemberCard(String memberCard) {
		CrmCustomer searchCustomer = new CrmCustomer();
		searchCustomer.setMembercard(memberCard);
		return crmCustomerService.get(searchCustomer);
	}
}