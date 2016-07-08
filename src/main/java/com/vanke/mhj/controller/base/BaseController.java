package com.vanke.mhj.controller.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vanke.mhj.framework.constant.GlobalConstant;
import com.vanke.mhj.service.base.BaseService;
import com.vanke.mhj.utils.StringEscapeEditor;
import com.vanke.mhj.vo.base.SessionInfo;

@Controller
@RequestMapping("/base")
public class BaseController {

    @Resource
    protected BaseService baseService;

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));

        /**
         * 防止XSS攻击
         */
        binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
    }

    /**
     * 获取SessionInfo
     * 
     * @param request
     * @return
     */
    protected SessionInfo getSessinInfo(HttpServletRequest request) {
        // 获取sessionInfo
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
        return sessionInfo;
    }
}
