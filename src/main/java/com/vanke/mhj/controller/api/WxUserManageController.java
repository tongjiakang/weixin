package com.vanke.mhj.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.controller.base.BaseController;
import com.vanke.mhj.service.api.WxUserManageService;
import com.vanke.mhj.service.basic.AccountService;
import com.vanke.mhj.vo.base.Json;
import com.vanke.mhj.vo.basic.AccountVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/wxusermanage")
public class WxUserManageController extends BaseController{
	
	@Resource
	private WxUserManageService wxUserManageService;
	
	@Resource
	private AccountService accountService;
	
	/**
	 * 访问用户列表
	 * 
	 * @return
	 */
	@RequestMapping("/manage")
	public String manager(HttpServletRequest request) {
		return "/weixin/api/user/wxusermanageList";
	}
	
	/**
	 * 列表数据
	 * 
	 * @param ph
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public PageModel dataGrid(HttpServletRequest request,PageModel ph) {
		try {
			String accountId = request.getParameter("accountId");
			AccountVo accountVo = accountService.getAccount(Long.parseLong(accountId));
			PageModel pageModel = wxUserManageService.dataGrid(accountVo, ph);
			return pageModel;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ph;
	}
	
	
	/**
	 * 移动分组
	 * 
	 * @param ph
	 * @return
	 */
	@RequestMapping("/moveGroupPage")
	public String moveGroupPage(){
		return "/weixin/api/user/wxusermanageMoveGroup";
	}
	
	/**
	 * 移动分组
	 * 
	 * @param ph
	 * @return
	 */
	@RequestMapping("/move")
	@ResponseBody
	public Json move(int group,Long accountId, String openids){
		Json json = new Json();
		AccountVo accountVo = accountService.getAccount( accountId);
		JSONObject result = wxUserManageService.move(accountVo,group,openids);
		String message;
		if(result!=null){
			if (0 == result.getIntValue( "errcode")) {
				json.setSuccess(true);
					message = "移动分组成功！";
			}
			else {
				message = "移动分组失败！错误码为："+result.getIntValue("errcode")+"错误信息为："+result.getString("errmsg");
			}
		}else{
			message = "移动分组失败！移动分组URL地址不正确。";
		}
		json.setMsg(message);
		return json;
	}
	

}
