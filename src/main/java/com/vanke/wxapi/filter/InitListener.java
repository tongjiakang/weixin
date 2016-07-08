package com.vanke.wxapi.filter;

import com.vanke.mhj.service.basic.AccountService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by PanJM on 2016/5/10.
 */
public class InitListener implements ServletContextListener {
    private static Log logger1 = LogFactory.getLog(InitListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 获取容器与相关的Service对象
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());

        // 准备数据:
        AccountService accountService = (AccountService) ac.getBean("accountServiceImpl");

        try {
//            logger1.info("------------> ACCESS_TOKEN数据开始准备 <------------");
//            accountService.addAccessTokenCache();
//            logger1.info("------------> ACCESS_TOKEN数据已准备成功 <------------");
        } catch (Exception e) {
            e.printStackTrace();
            logger1.info("------------> 数据准备失败 <------------");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
