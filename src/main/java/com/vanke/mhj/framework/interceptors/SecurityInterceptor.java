package com.vanke.mhj.framework.interceptors;

import com.vanke.mhj.framework.constant.GlobalConstant;
import com.vanke.mhj.vo.base.SessionInfo;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 权限拦截器
 * 
 */
public class SecurityInterceptor implements HandlerInterceptor {


    private List<String> excludeUrls;// 不需要拦截的资源

    public List<String> getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    /**
     * 完成页面的render后调用
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
    }

    /**
     * 在调用controller具体方法后拦截
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在调用controller具体方法前拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);

        if (url.equals("/login") || url.equals("/logout")) {
            return true;
        }
        if (url.indexOf("/wxapi")>-1) {
            return true;
        }

        if (excludeUrls.contains(url)) {
            return true;
        }

        if (sessionInfo == null) {
            request.setAttribute("msg", "您还没有登录或登录已超时，请重新登录，然后再刷新本功能！");
            request.getRequestDispatcher("/error/noSession.jsp").forward(request, response);
            return false;
        }
        return true;
    }

}
