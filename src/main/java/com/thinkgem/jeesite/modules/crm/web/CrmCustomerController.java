/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crm.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.crm.entity.CrmCustomer;
import com.thinkgem.jeesite.modules.crm.entity.CrmMemberCard;
import com.thinkgem.jeesite.modules.crm.service.CrmCustomerService;
import com.thinkgem.jeesite.modules.crm.service.CrmMemberCardService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.DictService;

/**
 * 客户管理Controller
 * @author HEY-Chain
 * @version 2018-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/crm/crmCustomer")
public class CrmCustomerController extends BaseController {

	@Autowired
	private CrmCustomerService customerService;

	@Autowired
	private CrmMemberCardService memberCardService;

	@Autowired
	private DictService dictService;
	
	@ModelAttribute
	public CrmCustomer get(@RequestParam(required=false) String id) {
		CrmCustomer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerService.get(id);
		}
		if (entity == null){
			entity = new CrmCustomer();
		}
		return entity;
	}
	
	@RequiresPermissions("crm:crmCustomer:view")
	@RequestMapping(value = {"list", ""})
	public String list(CrmCustomer crmCustomer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CrmCustomer> page = customerService.findPage(new Page<CrmCustomer>(request, response), crmCustomer); 
		model.addAttribute("page", page);
		return "modules/crm/crmCustomerList";
	}

	@RequiresPermissions("crm:crmCustomer:view")
	@RequestMapping(value = "form")
	public String form(CrmCustomer crmCustomer, Model model) {
		model.addAttribute("crmCustomer", crmCustomer);
		return "modules/crm/crmCustomerForm";
	}

	@RequiresPermissions("crm:crmCustomer:edit")
	@RequestMapping(value = "save")
	public String save(CrmCustomer crmCustomer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, crmCustomer)){
			return form(crmCustomer, model);
		}
		customerService.save(crmCustomer);
		addMessage(redirectAttributes, "保存客户信息成功");
		return "redirect:"+Global.getAdminPath()+"/crm/crmCustomer/?repage";
	}
	
	@RequiresPermissions("crm:crmCustomer:edit")
	@RequestMapping(value = "delete")
	public String delete(CrmCustomer crmCustomer, RedirectAttributes redirectAttributes) {
		customerService.delete(crmCustomer);
		addMessage(redirectAttributes, "删除客户信息成功");
		return "redirect:"+Global.getAdminPath()+"/crm/crmCustomer/?repage";
	}

	/**
	 * TODO: 添加客户与会员卡的关系表
	 * 更新会员余额
	 */
	@RequiresPermissions("crm:crmCustomer:edit")
	@RequestMapping(value = "linkMemberCard")
	public String linkMemberCard(String openCustomerId, String openMemberCardId, RedirectAttributes redirectAttributes) {
		CrmMemberCard memberCard = memberCardService.get(openMemberCardId);
		if("1".equals(memberCard.getUseFlag())) {
			addMessage(redirectAttributes, "改开号已被占用，请更换卡号！");
		}
		else {
			CrmCustomer cust = customerService.get(openCustomerId);
			if(cust == null) {
				addMessage(redirectAttributes, "请先保存客户信息!");
			}
			else if(StringUtils.isNotEmpty(cust.getMemberCardId())) {
				addMessage(redirectAttributes, "该客户已绑定会员卡，不要重复绑定!");
			}
			else {
				cust.setMemberCardId(memberCard.getId());
				cust.setMemberCardNumber(memberCard.getCardNumber());
				memberCard.setUseFlag("1");
				
				customerService.linkMemberCard(cust, memberCard);
				redirectAttributes.addFlashAttribute("message", "开卡成功，卡号=" + memberCard.getCardNumber());
			}
		}
		return "redirect:" + adminPath + "/crm/crmCustomer";
	}

	/**
	 * 退卡
	 * 1：移除与客户的绑定关系 
	 * 2：删除历史交易记录
	 */
	@ResponseBody
	@RequiresPermissions("crm:crmCustomer:edit")
	@RequestMapping(value = "closeMemberCard")
	public String closeMemberCard(String customerId, Double clearAmount) {
		CrmCustomer cust = customerService.get(customerId);
		if(!StringUtils.isNoneBlank(cust.getMemberCardId())) {
			return "没有找到该用户的会员卡信息！";
		}
		else {
			CrmMemberCard memberCard = memberCardService.get(cust.getMemberCardId()); 
			customerService.closeMemberCard(cust, memberCard, clearAmount);
			return "ok";
		}
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
		List<CrmCustomer> customerList = customerService.findCustomerByGroup(customerGroupKey);
		
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

	/**
	 * 根据会员卡号获取客户
	 * @param memberCard
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "byMemberCard/{memberCard}")
	public CrmCustomer getByMemberCard(@PathVariable("memberCard") String memberCard) {
		CrmCustomer searchCustomer = new CrmCustomer();
		searchCustomer.setMemberCardId(memberCard);
		List<CrmCustomer> customerResult = customerService.findList(searchCustomer);
		return customerResult.size() == 0 ? new CrmCustomer(): customerResult.get(0);
	}
}