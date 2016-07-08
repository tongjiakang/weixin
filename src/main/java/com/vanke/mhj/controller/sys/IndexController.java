package com.vanke.mhj.controller.sys;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vanke.mhj.controller.base.BaseController;
import com.vanke.mhj.framework.constant.GlobalConstant;
import com.vanke.mhj.service.base.ServiceException;
import com.vanke.mhj.service.sys.UserService;
import com.vanke.mhj.vo.base.Json;
import com.vanke.mhj.vo.base.SessionInfo;
import com.vanke.mhj.vo.sys.User;

@Controller
@RequestMapping("/*")
public class IndexController extends BaseController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
        if ((sessionInfo != null) && (sessionInfo.getId() != null)) {
            return "/index";
        }
        return "/login";
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Json login(User user, HttpServletRequest request) {
        Json j = new Json();
        User sysuser;
        try {
            HttpSession session = request.getSession();
            sysuser = userService.login(user);
            j.setSuccess(true);
            SessionInfo sessionInfo = new SessionInfo();
            sessionInfo.setId(sysuser.getId());
            sessionInfo.setLoginname(sysuser.getLoginname());
            sessionInfo.setUsername(sysuser.getUserName());
            sessionInfo.setOrgNo(sysuser.getOrgNo());
            sessionInfo.setOrgId(sysuser.getOrgId());
            sessionInfo.setAreaId(sysuser.getAreaId());
            sessionInfo.setRoleId(sysuser.getRoleId());
            sessionInfo.setRoleType(sysuser.getRoleType());
            session.setAttribute(GlobalConstant.SESSION_INFO, sessionInfo);

            // 记录登录操作日志
            this.setOperationLog(request);
            j.setMsg("登录成功!");
        } catch (Exception e) {
            if (e instanceof ServiceException) {
                j.setMsg(e.getMessage());
            } else {
                j.setMsg("登录失败，请联系管理员!");
            }
            e.printStackTrace();
        }

        return j;
    }

    @ResponseBody
    @RequestMapping("/logout")
    public Json logout(HttpServletRequest request) {
        Json j = new Json();
        HttpSession session = request.getSession();
        if (session != null) {
            // 记录退出操作日志
            this.setOperationLog(request);
            // 注销session
            session.invalidate();
        }
        j.setSuccess(true);
        return j;
    }

    /**
     * 记录登录/退出日志
     * 
     * @param request
     */
    private void setOperationLog(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
    }

}
