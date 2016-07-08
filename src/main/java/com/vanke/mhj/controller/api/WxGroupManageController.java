package com.vanke.mhj.controller.api;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.vanke.mhj.service.api.WxGroupManageService;
import com.vanke.mhj.service.base.ServiceException;
import com.vanke.mhj.service.basic.AccountService;
import com.vanke.mhj.utils.ControllerMsg;
import com.vanke.mhj.vo.api.WXgroup;
import com.vanke.mhj.vo.base.Json;
import com.vanke.mhj.vo.basic.AccountVo;

@Controller
@RequestMapping("/wxgroupmanage")
public class WxGroupManageController {

	@Resource
	private WxGroupManageService wxGroupManageService;

	@Resource
	private AccountService accountService;

	/**
	 * 访问分组列表
	 * 
	 * @return
	 */
	@RequestMapping("/manage")
	public String manager(HttpServletRequest request) {
		return "/weixin/api/group/wxgroupmanageList";
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
	public List<WXgroup> dataGrid(HttpServletRequest request) {
		try {
			String accountId = request.getParameter("accountId");
			AccountVo accountVo = accountService.getAccount(Long.parseLong(accountId));
			return wxGroupManageService.getGroupByAPPID(accountVo.getAppid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 访问添加
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) throws Exception {
		return "/weixin/api/group/wxgroupmanageAdd";
	}

	/**
	 * 保存
	 * 
	 * @param vHouse
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Json save(HttpServletRequest request, WXgroup wXgroup, Long accountId) throws Exception {
		Json json = new Json();
		try {
			JSONObject jsonObject = wxGroupManageService.save(wXgroup, accountId);
			String message;
			if (jsonObject != null) {
				if (0 == jsonObject.getIntValue("errcode")) {
					json.setSuccess(true);
					message = "创建分组成功！";
				} else {
					json.setSuccess(false);
					message = "创建分组失败！错误码为：" + jsonObject.getIntValue("errcode") + "错误信息为："
							+ jsonObject.getString("errmsg");
				}
			} else {
				json.setSuccess(false);
				message = "创建分组失败。";
			}
			json.setMsg(message);
		} catch (Exception e) {
			json.setMsg(ControllerMsg.SAVE_MSG_ERROR);
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 访问修改
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updatePage")
	public String updatePage(HttpServletRequest request, Long id, String name) throws Exception {
		return "/weixin/api/group/wxgroupmanageEdit";
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @param vHouse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Json update(HttpServletRequest request, WXgroup wXgroup, Long accountId) throws Exception {
		Json json = new Json();
		JSONObject jsonObject = wxGroupManageService.update(wXgroup, accountId);
		String message;
		if (jsonObject != null) {
			if (0 == jsonObject.getIntValue("errcode")) {
				json.setSuccess(true);
				message = "修改分组成功！";
			} else {
				json.setSuccess(false);
				message = "修改分组失败！错误码为：" + jsonObject.getIntValue("errcode") + "错误信息为："
						+ jsonObject.getString("errmsg");
			}
		} else {
			json.setSuccess(false);
			message = "修改分组失败。";
		}
		json.setMsg(message);
		return json;
	}

	/**
	 * 删除
	 * 
	 * @param vHouse
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(HttpServletRequest request, Long id, Long accountId) {
		Json json = new Json();
		JSONObject jsonObject = wxGroupManageService.delete(accountId, id);
		String message;
		if (jsonObject != null) {
			if (0 == jsonObject.getIntValue("errcode")) {
				json.setSuccess(true);
				message = "修改分组成功！";
			} else {
				json.setSuccess(false);
				message = "修改分组失败！错误码为：" + jsonObject.getIntValue("errcode") + "错误信息为："
						+ jsonObject.getString("errmsg");
			}
		} else {
			json.setSuccess(false);
			message = "修改分组失败。";
		}
		json.setMsg(message);
		return json;
	}

}
