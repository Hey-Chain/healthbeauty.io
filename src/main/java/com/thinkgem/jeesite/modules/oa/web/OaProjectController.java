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
import com.thinkgem.jeesite.modules.oa.entity.OaProject;
import com.thinkgem.jeesite.modules.oa.service.OaProjectService;

/**
 * 医疗项目管理Controller
 * @author HEY-Chain
 * @version 2018-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaProject")
public class OaProjectController extends BaseController {

	@Autowired
	private OaProjectService oaProjectService;
	
	@ModelAttribute
	public OaProject get(@RequestParam(required=false) String id) {
		OaProject entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaProjectService.get(id);
		}
		if (entity == null){
			entity = new OaProject();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaProject:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaProject oaProject, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaProject> page = oaProjectService.findPage(new Page<OaProject>(request, response), oaProject); 
		model.addAttribute("page", page);
		return "modules/oa/oaProjectList";
	}

	@RequiresPermissions("oa:oaProject:view")
	@RequestMapping(value = "form")
	public String form(OaProject oaProject, Model model) {
		model.addAttribute("oaProject", oaProject);
		return "modules/oa/oaProjectForm";
	}

	@RequiresPermissions("oa:oaProject:edit")
	@RequestMapping(value = "save")
	public String save(OaProject oaProject, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaProject)){
			return form(oaProject, model);
		}
		oaProjectService.save(oaProject);
		addMessage(redirectAttributes, "保存医疗项目成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaProject/?repage";
	}
	
	@RequiresPermissions("oa:oaProject:edit")
	@RequestMapping(value = "delete")
	public String delete(OaProject oaProject, RedirectAttributes redirectAttributes) {
		oaProjectService.delete(oaProject);
		addMessage(redirectAttributes, "删除医疗项目成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaProject/?repage";
	}

}