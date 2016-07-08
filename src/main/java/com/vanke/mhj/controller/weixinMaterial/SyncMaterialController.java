package com.vanke.mhj.controller.weixinMaterial;

import com.vanke.mhj.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by PanJM on 2016/7/7.
 * 图文素材同步
 */
@Controller("/syncMaterial")
public class SyncMaterialController extends BaseController {

    @RequestMapping("/manager")
    public String manager() throws Exception {
        return "/weixin/weixinMaterial/syncMaterial/syncMaterialList";
    }
}
