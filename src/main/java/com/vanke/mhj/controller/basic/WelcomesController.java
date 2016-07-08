package com.vanke.mhj.controller.basic;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.controller.base.BaseController;
import com.vanke.mhj.service.base.ServiceException;
import com.vanke.mhj.service.basic.WelcomesService;
import com.vanke.mhj.utils.ControllerMsg;
import com.vanke.mhj.vo.base.Json;
import com.vanke.mhj.vo.base.SessionInfo;
import com.vanke.mhj.vo.basic.WelcomesVo;

@Controller
@RequestMapping("/welcomes")
public class WelcomesController extends BaseController{

	@Resource
	private WelcomesService welcomesService;
	
	/**
	 * 访问欢迎语列表
	 * 
	 * @return
	 */
	@RequestMapping("/manage")
	public String manager(HttpServletRequest request) {
		return "/weixin/basic/welcomes/welcomesList";
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
	public PageModel dataGrid(HttpServletRequest request, WelcomesVo welcomesVo, PageModel ph) {
		try {
			return welcomesService.dataGrid(welcomesVo, ph);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ph;
	}
	
	/**
	 * 访问欢迎语添加
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		return "/weixin/basic/welcomes/welcomesAdd";
	}
	
	/**
	 * 保存
	 * 
	 * @param vHouse
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Json save(HttpServletRequest request, WelcomesVo welcomesVo) {
		Json json = new Json();
		try {
			SessionInfo sessionInfo = this.getSessinInfo(request);
			welcomesVo.setCreateUser(sessionInfo.getLoginname());
			welcomesVo.setCreateDate(new Date());
			welcomesVo.setOrgId(sessionInfo.getOrgId());
			welcomesService.save(welcomesVo);
			json.setSuccess(true);
			json.setMsg(ControllerMsg.SAVE_MSG_SUCC);
		} catch (Exception e) {
			 if (e instanceof ServiceException) {
	                json.setMsg(e.getMessage());
	            }else{
	                json.setMsg(ControllerMsg.SAVE_MSG_ERROR);
	                e.printStackTrace();
	            }
		}
		return json;
	}
	
	/**
	 * 访问欢迎语修改
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updatePage")
	public String updatePage(HttpServletRequest request, Long id) {
		WelcomesVo welcomesVo = welcomesService.getWelcomes(id);
		request.setAttribute("welcomes", welcomesVo);
		return "/weixin/basic/welcomes/welcomesEdit";
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
	public Json update(HttpServletRequest request, WelcomesVo welcomesVo) {
		Json j = new Json();
		try {
			SessionInfo sessionInfo = this.getSessinInfo(request);
			welcomesVo.setUpdateUser(sessionInfo.getLoginname());
			welcomesVo.setUpdateDate(new Date());
			welcomesService.update(welcomesVo);
			j.setSuccess(true);
			j.setMsg(ControllerMsg.SAVE_MSG_SUCC);
		} catch (ServiceException e) {
			 if (e instanceof ServiceException) {
	                j.setMsg(e.getMessage());
	            }else{
	                j.setMsg(ControllerMsg.SAVE_MSG_ERROR);
	                e.printStackTrace();
	            }
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
			welcomesService.delete(ids);
			j.setMsg(ControllerMsg.DEL_MSG_SUCC);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(ControllerMsg.DEL_MSG_ERROR);
			e.printStackTrace();
		}
		return j;
	}
	
}
