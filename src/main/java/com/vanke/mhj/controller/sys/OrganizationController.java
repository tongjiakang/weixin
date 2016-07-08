package com.vanke.mhj.controller.sys;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.vanke.mhj.controller.base.BaseController;
import com.vanke.mhj.service.sys.OrganizationService;
import com.vanke.mhj.utils.ControllerMsg;
import com.vanke.mhj.vo.base.Json;
import com.vanke.mhj.vo.base.SessionInfo;
import com.vanke.mhj.vo.base.Tree;
import com.vanke.mhj.vo.sys.Organization;

@Controller
@RequestMapping("/sys/organization")
public class OrganizationController extends BaseController {

	@Resource
	private OrganizationService organizationService;

	@RequestMapping("/manager")
	public String manager() throws Exception {
		return "/weixin/sys/organization";
	}

	@RequestMapping("/treeGrid")
	@ResponseBody
	public List<Organization> treeGrid(HttpServletRequest request) throws Exception {
		try {
			SessionInfo sessionInfo = this.getSessinInfo(request);
			return organizationService.treeGrid(sessionInfo.getOrgNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Lists.newArrayList();
	}

	@RequestMapping("/loadRelatedOrgs")
	@ResponseBody
	public List<Tree> loadRelatedOrgs(HttpServletRequest request) throws Exception {
		try {
			SessionInfo sessionInfo = this.getSessinInfo(request);
			return organizationService.loadRelatedOrgs(sessionInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Lists.newArrayList();
	}

	@RequestMapping("/tree")
	@ResponseBody
	public List<Tree> tree(HttpServletRequest request) throws Exception {
		try {
			SessionInfo sessionInfo = this.getSessinInfo(request);
			return organizationService.tree(sessionInfo.getOrgNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Lists.newArrayList();
	}

	@RequestMapping("/treeOwn")
	@ResponseBody
	public List<Tree> treeCmp(HttpServletRequest request) throws Exception {
		try {
			SessionInfo sessionInfo = this.getSessinInfo(request);
			return organizationService.treeOwn("company", sessionInfo.getOrgNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Lists.newArrayList();
	}

	@RequestMapping("/addPage")
	public String addPage() throws Exception {
		return "/weixin/sys/organizationAdd";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(HttpServletRequest request, Organization organization) throws Exception {
		Json j = new Json();
		try {
			SessionInfo sessionInfo = this.getSessinInfo(request);
			organization.setCreateUser(sessionInfo.getLoginname());
			organization.setUpdateUser(sessionInfo.getLoginname());
			organizationService.add(organization);
			j.setSuccess(true);
			j.setMsg(ControllerMsg.SAVE_MSG_SUCC);
		} catch (Exception e) {
			j.setMsg(ControllerMsg.SAVE_MSG_ERROR);
		}
		return j;
	}

	@RequestMapping("/get")
	@ResponseBody
	public Organization get(Long id) throws Exception {
		try {
			return organizationService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) throws Exception {
		Organization o = organizationService.get(id);
		request.setAttribute("organization", o);
		return "/weixin/sys/organizationEdit";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(HttpServletRequest request, Organization org) throws Exception {
		Json j = new Json();
		try {
			SessionInfo sessionInfo = this.getSessinInfo(request);
			org.setCreateUser(sessionInfo.getLoginname());
			org.setUpdateUser(sessionInfo.getLoginname());
			organizationService.edit(org);
			j.setSuccess(true);
			j.setMsg(ControllerMsg.SAVE_MSG_SUCC);
		} catch (Exception e) {
			j.setMsg(ControllerMsg.SAVE_MSG_ERROR);
		}
		return j;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Long id) throws Exception {
		Json j = new Json();
		try {
			organizationService.delete(id);
			j.setMsg(ControllerMsg.DEL_MSG_SUCC);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(ControllerMsg.DEL_MSG_ERROR);
		}
		return j;
	}
}
