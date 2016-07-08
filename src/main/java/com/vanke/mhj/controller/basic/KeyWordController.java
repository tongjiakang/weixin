package com.vanke.mhj.controller.basic;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.controller.base.BaseController;
import com.vanke.mhj.framework.constant.GlobalConstant;
import com.vanke.mhj.service.base.ServiceException;
import com.vanke.mhj.service.basic.KeyWordService;
import com.vanke.mhj.service.basic.WelcomesService;
import com.vanke.mhj.service.material.NewsService;
import com.vanke.mhj.service.material.PicMaterialService;
import com.vanke.mhj.utils.ControllerMsg;
import com.vanke.mhj.vo.base.Json;
import com.vanke.mhj.vo.base.Label;
import com.vanke.mhj.vo.base.SessionInfo;
import com.vanke.mhj.vo.basic.VKeyWord;
import com.vanke.mhj.vo.basic.WelcomesVo;

@Controller
@RequestMapping("/keyWord")
public class KeyWordController extends BaseController{

	@Resource
	private WelcomesService welcomesService;
	
	@Resource
	private PicMaterialService picMaterialService;
	
	@Resource
	private NewsService newsService;
	
	@Resource
	private KeyWordService keyWordService;
	
	/**
	 * 访问欢迎语列表
	 * 
	 * @return
	 */
	@RequestMapping("/manage")
	public String manager(HttpServletRequest request) {
		return "/weixin/basic/keyWord/keyWordList";
	}
	

	
	@RequestMapping("/getMaterial")
	@ResponseBody
	public List<Label> getMaterial(HttpServletRequest request,String type){
		SessionInfo sessionInfo = (SessionInfo)(request.getSession().getAttribute(GlobalConstant.SESSION_INFO));
		if (type.equals("news")) {
			return picMaterialService.getMaterials(sessionInfo.getOrgNo());
		}else {
			return newsService.getMaterials(sessionInfo.getOrgNo());
		}
	}
	
	/**
	 * 列表数据
	 * 
	 * @param ownerVo
	 * @param ph
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public PageModel dataGrid(HttpServletRequest request, VKeyWord vKeyWord, PageModel ph) {
		try {
			return keyWordService.dataGrid(vKeyWord, ph);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ph;
	}
	
	/**
	 * 访问欢迎语添加
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		return "/weixin/basic/keyWord/keyWordAdd";
	}
	
	/**
	 * 保存
	 * 
	 * @param vHouse
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Json save(HttpServletRequest request, VKeyWord vKeyWord) {
		Json json = new Json();
		try {
			SessionInfo sessionInfo = this.getSessinInfo(request);
			vKeyWord.setCreateUser(sessionInfo.getLoginname());
			vKeyWord.setCreateDate(new Date());
			vKeyWord.setOrgId(sessionInfo.getOrgId());
			keyWordService.save(vKeyWord);
			json.setSuccess(true);
			json.setMsg(ControllerMsg.SAVE_MSG_SUCC);
		} catch (Exception e) {
			json.setMsg(ControllerMsg.SAVE_MSG_ERROR);
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 访问欢迎语修改
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updatePage")
	public String updatePage(HttpServletRequest request, Long id) {
		VKeyWord vKeyWord = keyWordService.getKeyWord(id);
		request.setAttribute("keyWord", vKeyWord);
		return "/weixin/basic/keyWord/keyWordEdit";
	}
	
	/**
	 * 修改
	 * @param request
	 * @param vHouse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Json update(HttpServletRequest request, VKeyWord vKeyWord) {
		Json j = new Json();
		try {
			SessionInfo sessionInfo = this.getSessinInfo(request);
			vKeyWord.setUpdateUser(sessionInfo.getLoginname());
			keyWordService.update(vKeyWord);
			j.setSuccess(true);
			j.setMsg(ControllerMsg.SAVE_MSG_SUCC);
		} catch (ServiceException e) {
			j.setMsg(ControllerMsg.SAVE_MSG_ERROR);
			e.printStackTrace();
		}
		return j;
	}
	
	/**
	 * 删除
	 * @param 
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(HttpServletRequest request) {
		Json j = new Json();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			keyWordService.delete(id);
			j.setMsg(ControllerMsg.DEL_MSG_SUCC);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(ControllerMsg.DEL_MSG_ERROR);
			e.printStackTrace();
		}
		return j;
	}
	
}
