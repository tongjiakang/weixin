package com.vanke.mhj.controller.material;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.controller.base.BaseController;
import com.vanke.mhj.service.base.ServiceException;
import com.vanke.mhj.service.material.LinkService;
import com.vanke.mhj.utils.ControllerMsg;
import com.vanke.mhj.vo.base.Json;
import com.vanke.mhj.vo.base.Label;
import com.vanke.mhj.vo.base.SessionInfo;
import com.vanke.mhj.vo.material.MsgLinkVo;

@Controller
@RequestMapping("/link")
public class LinkController extends BaseController{

	@Resource
	private LinkService linkService;
	
	
	/**
	 * 外部链接列表
	 * 
	 * @return
	 */
	@RequestMapping("/manage")
	public String manager(HttpServletRequest request) {
		return "/weixin/material/link/linkList";
	}
	
	/**
	 * 列表数据
	 * 
	 * @param ownerVo
	 * @param ph
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public PageModel dataGrid(HttpServletRequest request, MsgLinkVo msgLinkVo, PageModel ph) {
		try {
			if (msgLinkVo.getOrgId() == null) {
				SessionInfo sessionInfo = this.getSessinInfo(request);
				msgLinkVo.setOrgId(sessionInfo.getOrgId());
			}
			return linkService.dataGrid(msgLinkVo, ph);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ph;
	}
	
	/**
	 * 列表数据
	 * 
	 * @param ownerVo
	 * @param ph
	 * @return
	 */
	@RequestMapping("/getLinks")
	@ResponseBody
	public List<Label> getLinks(HttpServletRequest request) {
		return linkService.getLinksLabel();
	}
	
	/**
	 * 访问外部链接添加
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) throws Exception {
		return "/weixin/material/link/linkAdd";
	}
	
	/**
	 * 保存
	 * 
	 * @param vHouse
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Json save(HttpServletRequest request, MsgLinkVo msgLinkVo) throws Exception {
		Json json = new Json();
		try {
			SessionInfo sessionInfo = this.getSessinInfo(request);
			msgLinkVo.setCreateUser(sessionInfo.getLoginname());
			msgLinkVo.setCreateDate(new Date());
			msgLinkVo.setOrgId(sessionInfo.getOrgId());
			linkService.save(msgLinkVo);
			json.setSuccess(true);
			json.setMsg(ControllerMsg.SAVE_MSG_SUCC);
		} catch (Exception e) {
			json.setMsg(ControllerMsg.SAVE_MSG_ERROR);
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 访问外部链接修改
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updatePage")
	public String updatePage(HttpServletRequest request, Long id) throws Exception {
		MsgLinkVo msgLinkVo = linkService.getMsgLink(id);
		request.setAttribute("link", msgLinkVo);
		return "/weixin/material/link/linkEdit";
	}
	
	/**
	 * 修改
	 * @param request
	 * @param vHouse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Json update(HttpServletRequest request, MsgLinkVo msgLinkVo) throws Exception {
		Json j = new Json();
		try {
			SessionInfo sessionInfo = this.getSessinInfo(request);
			msgLinkVo.setUpdateUser(sessionInfo.getLoginname());
			msgLinkVo.setUpdateDate(new Date());
			linkService.update(msgLinkVo);
			j.setSuccess(true);
			j.setMsg(ControllerMsg.SAVE_MSG_SUCC);
		} catch (ServiceException e) {
			j.setMsg(ControllerMsg.SAVE_MSG_ERROR);
			e.printStackTrace();
		}
		return j;
	}
	
	/**
	 * 删除
	 * @param vHouse
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(HttpServletRequest request) {
		Json j = new Json();
		try {
			String ids = request.getParameter("id");
			linkService.delete(ids);
			j.setMsg(ControllerMsg.DEL_MSG_SUCC);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(ControllerMsg.DEL_MSG_ERROR);
			e.printStackTrace();
		}
		return j;
	}
	
	/**
	 * 改变启用状态
	 * @param 
	 * @return
	 */
	@RequestMapping("/changeStatus")
	@ResponseBody
	public Json delete(HttpServletRequest request,Long id) {
		Json j = new Json();
		try {
			linkService.changeStatus(id);
			j.setMsg(ControllerMsg.CHANGE_MSG_SUCC);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(ControllerMsg.CHANGE_MSG_ERROR);
			e.printStackTrace();
		}
		return j;
	}
}
