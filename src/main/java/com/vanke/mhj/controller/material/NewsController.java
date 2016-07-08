package com.vanke.mhj.controller.material;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.controller.base.BaseController;
import com.vanke.mhj.service.base.ServiceException;
import com.vanke.mhj.service.material.NewsService;
import com.vanke.mhj.utils.ControllerMsg;
import com.vanke.mhj.vo.base.Json;
import com.vanke.mhj.vo.base.SessionInfo;
import com.vanke.mhj.vo.material.MsgNewsVo;

@Controller
@RequestMapping("/news")
public class NewsController extends BaseController{

	@Resource
	private NewsService newsService;
	
	/**
	 * 访问消息素材列表
	 * 
	 * @return
	 */
	@RequestMapping("/manage")
	public String manager(HttpServletRequest request) {
		return "/weixin/material/news/newsList";
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
	public PageModel dataGrid(HttpServletRequest request, MsgNewsVo msgNewsVo, PageModel ph) {
		try {
			if (msgNewsVo.getOrgId() == null) {
				SessionInfo sessionInfo = this.getSessinInfo(request);
				msgNewsVo.setOrgId(sessionInfo.getOrgId());
			}
			return newsService.dataGrid(msgNewsVo, ph);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ph;
	}
	
	/**
	 * 访问消息添加
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) throws Exception {
		return "/weixin/material/news/newsAdd";
	}
	
	/**
	 * 保存
	 * 
	 * @param vHouse
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Json save(HttpServletRequest request, MsgNewsVo msgNewsVo) throws Exception {
		Json json = new Json();
		try {
			SessionInfo sessionInfo = this.getSessinInfo(request);
			msgNewsVo.setCreateUser(sessionInfo.getLoginname());
			msgNewsVo.setCreateDate(new Date());
			msgNewsVo.setOrgId(sessionInfo.getOrgId());
			newsService.save(msgNewsVo);
			json.setSuccess(true);
			json.setMsg(ControllerMsg.SAVE_MSG_SUCC);
		} catch (Exception e) {
			json.setMsg(ControllerMsg.SAVE_MSG_ERROR);
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 访问消息修改
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updatePage")
	public String updatePage(HttpServletRequest request, Long id) throws Exception {
		MsgNewsVo msgNewsVo = newsService.getMsgNews(id);
		request.setAttribute("news", msgNewsVo);
		return "/weixin/material/news/newsEdit";
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
	public Json update(HttpServletRequest request, MsgNewsVo msgNewsVo) throws Exception {
		Json j = new Json();
		try {
			SessionInfo sessionInfo = this.getSessinInfo(request);
			msgNewsVo.setUpdateUser(sessionInfo.getLoginname());
			msgNewsVo.setUpdateDate(new Date());
			newsService.update(msgNewsVo);
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
			newsService.delete(ids);
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
			newsService.changeStatus(id);
			j.setMsg(ControllerMsg.CHANGE_MSG_SUCC);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(ControllerMsg.CHANGE_MSG_ERROR);
			e.printStackTrace();
		}
		return j;
	}
}
