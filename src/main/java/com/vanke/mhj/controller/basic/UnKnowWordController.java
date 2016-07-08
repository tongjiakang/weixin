package com.vanke.mhj.controller.basic;

import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.controller.base.BaseController;
import com.vanke.mhj.service.base.ServiceException;
import com.vanke.mhj.service.basic.UnKnowWordService;
import com.vanke.mhj.utils.ControllerMsg;
import com.vanke.mhj.vo.base.Json;
import com.vanke.mhj.vo.base.SessionInfo;
import com.vanke.mhj.vo.basic.VUnKnowWord;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by PanJM on 2016/6/29.
 */
@Controller
@RequestMapping("/unKnowWord")
public class UnKnowWordController extends BaseController{

    @Resource
    private UnKnowWordService unKnowWordService;

    /**
     * 访问未识别回复语
     * @return
     */
    @RequestMapping("/manage")
    public String manager() {
        return "/weixin/basic/unKnowWord/unKnowWordList";
    }

    /**
     * 列表
     * @param request
     * @param vUnKnowWord
     * @param ph
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public PageModel dataGrid(HttpServletRequest request, VUnKnowWord vUnKnowWord, PageModel ph) {
        try {
            PageModel pageModel = unKnowWordService.dataGrid(vUnKnowWord, ph);
            return pageModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ph;
    }

    /**
     * 跳转新增页面
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage() {
        return "/weixin/basic/unKnowWord/unKnowWordAdd";
    }

    /**
     * 新增
     * @param request
     * @param vUnKnowWord
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(HttpServletRequest request, VUnKnowWord vUnKnowWord) {
        Json json = new Json();
        try {
            SessionInfo sessionInfo = this.getSessinInfo(request);
            vUnKnowWord.setCreateUser(sessionInfo.getLoginname());
            vUnKnowWord.setCreateDate(new Date());
            unKnowWordService.save(vUnKnowWord);
            json.setSuccess(true);
            json.setMsg(ControllerMsg.SAVE_MSG_SUCC);
        } catch (Exception e) {
            if (e instanceof ServiceException) {
                json.setMsg(e.getMessage());
            }else{
                json.setMsg(ControllerMsg.SAVE_MSG_ERROR);
                e.printStackTrace();
            }
        }
        return json;
    }

    /**
     * 跳转修改页面
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(Model model,Long id) {
        try {
            VUnKnowWord unKnowWord = unKnowWordService.getVUnKnowWord(id);
            model.addAttribute("unKnowWord", unKnowWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/weixin/basic/unKnowWord/unKnowWordEdit";
    }

    /**
     * 修改
     * @param request
     * @param vUnKnowWord
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(HttpServletRequest request, VUnKnowWord vUnKnowWord) {
        Json json = new Json();
        try {
            SessionInfo sessionInfo = this.getSessinInfo(request);
            vUnKnowWord.setUpdateUser(sessionInfo.getLoginname());
            vUnKnowWord.setUpdateDate(new Date());
            unKnowWordService.edit(vUnKnowWord);
            json.setSuccess(true);
            json.setMsg(ControllerMsg.SAVE_MSG_SUCC);
        } catch (Exception e) {
            if (e instanceof ServiceException) {
                json.setMsg(e.getMessage());
            }else{
                json.setMsg(ControllerMsg.SAVE_MSG_ERROR);
                e.printStackTrace();
            }
        }
        return json;
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Long id) {
        Json json = new Json();
        try {
            unKnowWordService.delete(id);
            json.setSuccess(true);
            json.setMsg(ControllerMsg.DEL_MSG_SUCC);
        } catch (Exception e) {
            if (e instanceof ServiceException) {
                json.setMsg(e.getMessage());
            }else{
                json.setMsg(ControllerMsg.DEL_MSG_ERROR);
                e.printStackTrace();
            }
        }
        return json;
    }
}
