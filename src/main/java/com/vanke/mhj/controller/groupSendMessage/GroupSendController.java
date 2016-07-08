package com.vanke.mhj.controller.groupSendMessage;

import com.vanke.mhj.controller.base.BaseController;
import com.vanke.mhj.vo.base.Json;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by PanJM on 2016/7/5.
 * 群发消息Controller
 */
@Controller
@RequestMapping("/groupSend")
public class GroupSendController extends BaseController {

    @RequestMapping("/uploadNews")
    @ResponseBody
    public Json uploadNews(HttpServletRequest request) {
        Json j = new Json();
        return j;
    }
}
