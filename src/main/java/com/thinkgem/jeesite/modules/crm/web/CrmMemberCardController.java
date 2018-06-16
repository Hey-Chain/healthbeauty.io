/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crm.web;

import java.util.List;

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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.crm.entity.CrmBalanceInOut;
import com.thinkgem.jeesite.modules.crm.entity.CrmMemberCard;
import com.thinkgem.jeesite.modules.crm.service.CrmBalanceInOutService;
import com.thinkgem.jeesite.modules.crm.service.CrmMemberCardService;

/**
 * 会员卡管理Controller
 * @author HEY-Chain
 * @version 2018-05-13
 */
@Controller
@RequestMapping(value = "${adminPath}/crm/crmMemberCard")
public class CrmMemberCardController extends BaseController {

	@Autowired
	private CrmMemberCardService crmMemberCardService;

	@Autowired
	private CrmBalanceInOutService crmBalanceInOutService;
	
	@ModelAttribute
	public CrmMemberCard get(@RequestParam(required=false) String id) {
		CrmMemberCard entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = crmMemberCardService.get(id);
		}
		if (entity == null){
			entity = new CrmMemberCard();
		}
		return entity;
	}
	
	@RequiresPermissions("crm:crmMemberCard:view")
	@RequestMapping(value = {"list", ""})
	public String list(CrmMemberCard crmMemberCard, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CrmMemberCard> page = crmMemberCardService.findPage(new Page<CrmMemberCard>(request, response), crmMemberCard); 
		model.addAttribute("page", page);
		return "modules/crm/crmMemberCardList";
	}

	@RequiresPermissions("crm:crmMemberCard:view")
	@RequestMapping(value = "form")
	public String form(CrmMemberCard crmMemberCard, Model model) {
		model.addAttribute("crmMemberCard", crmMemberCard);
		return "modules/crm/crmMemberCardForm";
	}

	@RequiresPermissions("crm:crmMemberCard:edit")
	@RequestMapping(value = "save")
	public String save(CrmMemberCard crmMemberCard, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, crmMemberCard)){
			return form(crmMemberCard, model);
		}

		crmMemberCardService.save(crmMemberCard);
		
		addMessage(redirectAttributes, "保存会员卡成功");
		return "redirect:"+Global.getAdminPath()+"/crm/crmMemberCard/?repage";
	}
	
	@RequiresPermissions("crm:crmMemberCard:edit")
	@RequestMapping(value = "delete")
	public String delete(CrmMemberCard crmMemberCard, RedirectAttributes redirectAttributes) {
		crmMemberCardService.delete(crmMemberCard);
		addMessage(redirectAttributes, "删除会员卡成功");
		return "redirect:"+Global.getAdminPath()+"/crm/crmMemberCard/?repage";
	}

	/**
	 * 根据会员卡号获取客户
	 * @param memberCard
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "byMemberCard/{memberCard}")
	public CrmMemberCard getByMemberCard(@PathVariable("memberCard") String memberCard) {
		CrmMemberCard searchMemberCard = new CrmMemberCard();
		searchMemberCard.setCardNumber(memberCard);
		searchMemberCard.setDelFlag("0");
		List<CrmMemberCard> resultList = crmMemberCardService.findList(searchMemberCard);
		return resultList.size() == 0 ? new CrmMemberCard(): resultList.get(0);
	}

	/**
	 * 获取客户余额
	 * @param memberCard
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getBalance/{memberCardId}/{customerId}")
	public Double getBalance(@PathVariable("memberCardId") String memberCardId, String customerId) {
		CrmBalanceInOut crmBalanceIncrease = new CrmBalanceInOut();
		crmBalanceIncrease.setMemberCardId(memberCardId);
		crmBalanceIncrease.setCustomerId(customerId);
		crmBalanceIncrease.setDelFlag("0");
		
		Double balanceAmount = Double.valueOf("0");
		List<CrmBalanceInOut> resultList = crmBalanceInOutService.findList(crmBalanceIncrease);
		for (CrmBalanceInOut crmBalanceInOut : resultList) {
			if(crmBalanceInOut.getOperateType() == 1)
				balanceAmount += crmBalanceInOut.getChangeAmount();
			else
				balanceAmount -= crmBalanceInOut.getChangeAmount();
		}
		
		return balanceAmount;
	}
	
	/**
	 * 更新会员卡余额
	 */
	@RequiresPermissions("crm:crmMemberCard:edit")
	@RequestMapping(value = "addMemberBalance")
	public String addMemberBalance(String inchargeMemberCardId, String inchargeCustomerId, Double inchargeAmount, RedirectAttributes redirectAttributes) {
		// 余额增加
		CrmBalanceInOut crmBalanceInOut = new CrmBalanceInOut();
		crmBalanceInOut.setMemberCardId(inchargeMemberCardId);
		crmBalanceInOut.setCustomerId(inchargeCustomerId);
		crmBalanceInOut.setOperateType(1);
		crmBalanceInOut.setChangeAmount(inchargeAmount);
		crmBalanceInOutService.save(crmBalanceInOut);
		
		redirectAttributes.addFlashAttribute("message", "充值成功，金额=" + inchargeAmount.toString());
		return "redirect:" + adminPath + "/crm/crmCustomer";
	}
	
}