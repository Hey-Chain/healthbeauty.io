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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.crm.service.CrmCustomerService;
import com.thinkgem.jeesite.modules.oa.entity.Attendance;
import com.thinkgem.jeesite.modules.oa.entity.Reservation;
import com.thinkgem.jeesite.modules.oa.service.AttendanceService;
import com.thinkgem.jeesite.modules.oa.service.ReservationService;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * 就诊管理Controller
 * @author HEY-Chain
 * @version 2018-03-27
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/attendance")
public class AttendanceController extends BaseController {

	@Autowired
	private AttendanceService attendanceService;

	@Autowired
	private SystemService systemService;
	
	@Autowired
	private ReservationService reservationService;

	@Autowired
	private CrmCustomerService customerService;
	
	@ModelAttribute
	public Attendance get(@RequestParam(required=false) String id) {
		Attendance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = attendanceService.get(id);
		}
		if (entity == null){
			entity = new Attendance();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:attendance:view")
	@RequestMapping(value = {"list", ""})
	public String list(Attendance attendance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Attendance> page = attendanceService.findPage(new Page<Attendance>(request, response), attendance); 
		model.addAttribute("page", page);
		return "modules/oa/attendanceList";
	}

	@RequiresPermissions("oa:attendance:view")
	@RequestMapping(value = "form")
	public String form(Attendance attendance, Model model) {
		
		if(attendance.getIsNewRecord() && StringUtils.isNotBlank(attendance.getCustomerId())) {
			attendance.setCustomerName(customerService.get(attendance.getCustomerId()).getCustomerName());
		}
		
		List<User> doctors = systemService.findUserByRoleId(Role.COUNSELOR_ROLE_ID);
		
		model.addAttribute("counselorList", doctors);
		model.addAttribute("attendance", attendance);
		
		return "modules/oa/attendanceForm";
	}

	@RequiresPermissions("oa:attendance:edit")
	@RequestMapping(value = "save")
	@Transactional()
	public String save(Attendance attendance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, attendance)){
			return form(attendance, model);
		}

		if(attendance.getIsNewRecord() && StringUtils.isNotBlank(attendance.getReservationId())) {
			Reservation reservation = reservationService.get(attendance.getReservationId());
			reservation.setStatus("1");
			reservationService.save(reservation);
		}
		
		attendanceService.save(attendance);
		
		addMessage(redirectAttributes, "保存就诊信息成功");
		return "redirect:"+Global.getAdminPath()+"/oa/attendance/?repage";
	}
	
	@RequiresPermissions("oa:attendance:edit")
	@RequestMapping(value = "delete")
	public String delete(Attendance attendance, RedirectAttributes redirectAttributes) {
		attendanceService.delete(attendance);
		addMessage(redirectAttributes, "删除就诊信息成功");
		return "redirect:"+Global.getAdminPath()+"/oa/attendance/?repage";
	}

}