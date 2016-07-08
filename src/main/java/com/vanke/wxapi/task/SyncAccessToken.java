package com.vanke.wxapi.task;

import com.vanke.mhj.service.basic.AccountService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PanJm on 2016/6/30.
 */
@Component
public class SyncAccessToken {

    @Resource
    private AccountService accountService;

    /**
     * 每十分钟更新一遍AccessToken缓存
     * @throws Exception
     */
//    @Scheduled(cron = "0 0/10 * * * ?")
    public void taskResource() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = formatter.format(new Date());
        System.out.println("更新AccessToken开始;时间:" + date);
//        accountService.addAccessTokenCache();
        System.out.println("更新AccessToken结束");
    }
}
