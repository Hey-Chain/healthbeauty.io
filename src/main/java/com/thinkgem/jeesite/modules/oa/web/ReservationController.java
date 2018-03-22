/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

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
import com.thinkgem.jeesite.modules.oa.entity.Reservation;
import com.thinkgem.jeesite.modules.oa.service.ReservationService;

/**
 * 预约管理Controller
 * @author HEY-Chain
 * @version 2018-03-23
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/reservation")
public class ReservationController extends BaseController {

	@Autowired
	private ReservationService reservationService;
	
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