package com.vanke.mhj.controller.sys;

import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.controller.base.BaseController;
import com.vanke.mhj.framework.constant.GlobalConstant;
import com.vanke.mhj.service.sys.UserService;
import com.vanke.mhj.utils.ControllerMsg;
import com.vanke.mhj.utils.contants.CommonContants;
import com.vanke.mhj.vo.base.Json;
import com.vanke.mhj.vo.base.SessionInfo;
import com.vanke.mhj.vo.sys.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {

	@Resource
	private UserService userService;

	@RequestMapping("/manager")
	public String manager() throws Exception {
		return "/weixin/sys/user";
	}

	@RequestMapping("/dataGrid")
	@ResponseBody
	public PageModel dataGrid(HttpServletRequest request, User user, PageModel ph) throws Exception {
		try {
			SessionInfo sessionInfo = this.getSessinInfo(request);
			user.setOrgNo(sessionInfo.getOrgNo());
			return userService.dataGrid(user, ph);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ph;
	}

	@RequestMapping("/editPwdPage")
	public String editPwdPage() throws Exception {
		return "/weixin/sys/userEditPwd";
	}

	@RequestMapping("/editUserPwd")
	@ResponseBody
	public Json editUserPwd(HttpServletRequest request, String oldPwd, String pwd) throws Exception {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			userService.editUserPwd(sessionInfo, oldPwd, pwd);
			j.setSuccess(true);
			j.setMsg(ControllerMsg.USER_CHANGE_PASSWORD_SUCC);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/addPage")
	public String addPage() throws Exception {
		return "/weixin/sys/userAdd";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(User user) throws Exception {
		Json j = new Json();
		try {
			userService.add(user);
			j.setSuccess(true);
			j.setMsg(ControllerMsg.SAVE_MSG_SUCC);
		} catch (Exception e) {
			if(e instanceof Exception){
				j.setMsg(e.getMessage());
			}else{
				j.setMsg(ControllerMsg.SAVE_MSG_ERROR);
			}
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping("/get")
	@ResponseBody
	public User get(Long id) throws Exception {
		try {
			return userService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Long id) throws Exception {
		Json j = new Json();
		try {
			userService.delete(id);
			j.setMsg(ControllerMsg.DEL_MSG_SUCC);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(ControllerMsg.DEL_MSG_ERROR);
		}
		return j;
	}

	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) throws Exception {
		User u = userService.get(id);
		request.setAttribute("user", u);
		return "/weixin/sys/userEdit";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(User user) throws Exception {
		Json j = new Json();
		try {
			userService.edit(user);
			j.setSuccess(true);
			j.setMsg(ControllerMsg.SAVE_MSG_SUCC);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/resetPassword")
	@ResponseBody
	public Json resetPassword(HttpServletRequest request, Long id) throws Exception {
		Json j = new Json();
		try {
			userService.resetPassword(id);
			j.setSuccess(true);
			j.setMsg(ControllerMsg.USER_RESET_PASSWORD_SUCC + CommonContants.DEFAULT_PWD);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg(ControllerMsg.USER_RESET_PASSWORD_ERROR);
		}
		return j;
	}

}
