package com.vanke.mhj.controller.basic;

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
import com.vanke.mhj.service.basic.AccountService;
import com.vanke.mhj.utils.ControllerMsg;
import com.vanke.mhj.vo.base.Json;
import com.vanke.mhj.vo.base.Label;
import com.vanke.mhj.vo.base.SessionInfo;
import com.vanke.mhj.vo.basic.AccountVo;

@Controller
@RequestMapping("/account")
public class AccountController extends BaseController{

	@Resource
	private AccountService accountService;
	
	/**
	 * 访问公众号列表
	 * 
	 * @return
	 */
	@RequestMapping("/manage")
	public String manager(HttpServletRequest request) {
		return "/weixin/basic/account/accountList";
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
	public PageModel dataGrid(HttpServletRequest request, AccountVo accountVo, PageModel ph) {
		try {
			if (accountVo.getOrgId() == null) {
				SessionInfo sessionInfo = this.getSessinInfo(request);
				accountVo.setOrgId(sessionInfo.getOrgId());
			}
			return accountService.dataGrid(accountVo, ph);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ph;
	}
	
	@RequestMapping("/getAcounts")
	@ResponseBody
	public List<Label> getAcountsLabel(HttpServletRequest request) {
		SessionInfo sessionInfo = this.getSessinInfo(request);
		return accountService.getAcountsLabel(sessionInfo.getOrgNo());
	}
	
	/**
	 * 访问公众号添加
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) throws Exception {
		return "/weixin/basic/account/accountAdd";
	}
	
	/**
	 * 保存
	 * 
	 * @param vHouse
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Json save(HttpServletRequest request, AccountVo accountVo) throws Exception {
		Json json = new Json();
		try {
			SessionInfo sessionInfo = this.getSessinInfo(request);
			accountVo.setCreateUser(sessionInfo.getLoginname());
			accountVo.setCreateDate(new Date());
			accountVo.setOrgId(sessionInfo.getOrgId());
			accountService.save(accountVo);
			json.setSuccess(true);
			json.setMsg(ControllerMsg.SAVE_MSG_SUCC);
		} catch (Exception e) {
			json.setMsg(ControllerMsg.SAVE_MSG_ERROR);
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 访问公众号修改
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updatePage")
	public String updatePage(HttpServletRequest request, Long id) throws Exception {
		AccountVo accountVo = accountService.getAccount(id);
		request.setAttribute("account", accountVo);
		return "/weixin/basic/account/accountEdit";
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
	public Json update(HttpServletRequest request, AccountVo accountVo) throws Exception {
		Json j = new Json();
		try {
			SessionInfo sessionInfo = this.getSessinInfo(request);
			accountVo.setUpdateUser(sessionInfo.getLoginname());
			accountVo.setUpdateDate(new Date());
			accountService.update(accountVo);
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
			accountService.delete(ids);
			j.setMsg(ControllerMsg.DEL_MSG_SUCC);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(ControllerMsg.DEL_MSG_ERROR);
			e.printStackTrace();
		}
		return j;
	}
	
}
