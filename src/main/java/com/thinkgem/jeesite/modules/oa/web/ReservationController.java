/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

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
import com.thinkgem.jeesite.modules.oa.entity.OaProject;
import com.thinkgem.jeesite.modules.oa.entity.Reservation;
import com.thinkgem.jeesite.modules.oa.service.OaProjectService;
import com.thinkgem.jeesite.modules.oa.service.ReservationService;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * 预约管理Controller
 * @author HEY-Chain
 * @version 2018-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/reservation")
public class ReservationController extends BaseController {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private OaProjectService projectService;

	@Autowired
	private CrmCustomerService customerService;
	
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public Reservation get(@RequestParam(required=false) String id) {
		Reservation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reservationService.get(id);
		}
		if (entity == null){
			entity = new Reservation();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:reservation:view")
	@RequestMapping(value = {"list", ""})
	public String list(Reservation reservation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Reservation> page = reservationService.findPage(new Page<Reservation>(request, response), reservation); 
		model.addAttribute("page", page);
		return "modules/oa/reservationList";
	}

	@RequiresPermissions("oa:reservation:view")
	@RequestMapping(value = "form")
	public String form(Reservation reservation, Model model) {
		
		List<CrmCustomer> customers = customerService.findList(new CrmCustomer());
		model.addAttribute("customerList", customers);		
		if(reservation.getIsNewRecord() && StringUtils.isNotBlank(reservation.getCustomerId())) {
			for (CrmCustomer cust : customers) {
				if(cust.getId().equals(reservation.getCustomerId())) {
					reservation.setCustomerName(cust.getCustomerName());
					reservation.setMemberCard(cust.getMemberCardNumber());
					break;
				}
			}
		}
		
		List<OaProject> projectList = projectService.findList(new OaProject());
		model.addAttribute("projectList", projectList);
		
		List<User> doctors = systemService.findUserByRoleId(Role.DOCTOR_ROLE_ID);
		model.addAttribute("doctorList", doctors);

		model.addAttribute("reservation", reservation);
		return "modules/oa/reservationForm";
	}

	@RequiresPermissions("oa:reservation:edit")
	@RequestMapping(value = "save")
	public String save(Reservation reservation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reservation)){
			return form(reservation, model);
		}
		reservationService.save(reservation);
		addMessage(redirectAttributes, "保存预约成功");
		return "redirect:"+Global.getAdminPath()+"/oa/reservation/?repage";
	}
	
	@RequiresPermissions("oa:reservation:edit")
	@RequestMapping(value = "delete")
	public String delete(Reservation reservation, RedirectAttributes redirectAttributes) {
		reservationService.delete(reservation);
		addMessage(redirectAttributes, "删除预约成功");
		return "redirect:"+Global.getAdminPath()+"/oa/reservation/?repage";
	}

}