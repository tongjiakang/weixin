package com.vanke.mhj.controller.basic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vanke.mhj.controller.base.BaseController;
import com.vanke.mhj.service.base.ServiceException;
import com.vanke.mhj.service.basic.AccountService;
import com.vanke.mhj.service.basic.CustomMenuService;
import com.vanke.mhj.service.basic.KeyWordService;
import com.vanke.mhj.service.basic.WelcomesService;
import com.vanke.mhj.service.material.NewsService;
import com.vanke.mhj.service.material.PicMaterialService;
import com.vanke.mhj.utils.ControllerMsg;
import com.vanke.mhj.vo.base.Json;
import com.vanke.mhj.vo.base.SessionInfo;
import com.vanke.mhj.vo.base.Tree;
import com.vanke.mhj.vo.basic.AccountVo;
import com.vanke.mhj.vo.basic.VCustomMenu;
import com.vanke.wxapi.process.WxApi;
import com.vanke.wxapi.process.WxApiClient;
import com.vanke.wxapi.util.HttpUtil;
import com.vanke.wxapi.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/customMenu")
public class CustomMenuController extends BaseController{

	@Resource
	private WelcomesService welcomesService;
	
	@Resource
	private PicMaterialService picMaterialService;
	
	@Resource
	private NewsService newsService;
	
	@Resource
	private KeyWordService keyWordService;
	
	@Resource
	private CustomMenuService customMenuService;

	@Resource
	private AccountService accountService;
	
	/**
	 * 自定义菜单列表
	 * 
	 * @return
	 */
	@RequestMapping("/manage")
	public String manager(HttpServletRequest request) {
		return "/weixin/basic/customMenu/customMenuList";
	}
	
	
	/**
	 * 列表数据
	 * @return
	 */
	@RequestMapping("/treeGrid")
	@ResponseBody
	public List<VCustomMenu> treeGrid(HttpServletRequest request,VCustomMenu vCustomMenu) {
		try {
			return customMenuService.treeGrid(vCustomMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<VCustomMenu>();
	}
	
	@RequestMapping("/tree")
	@ResponseBody
	public List<Tree> tree(HttpServletRequest request,VCustomMenu vCustomMenuP) {
		try {
			return customMenuService.tree(vCustomMenuP);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Tree>();
	}
	
	/**
	 * 访问欢迎语添加
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		return "/weixin/basic/customMenu/customMenuAdd";
	}
	
	/**
	 * 保存
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Json save(HttpServletRequest request, VCustomMenu vCustomMenu) {
		Json json = new Json();
		try {
			SessionInfo sessionInfo = this.getSessinInfo(request);
			vCustomMenu.setCreateUser(sessionInfo.getLoginname());
			vCustomMenu.setCreateDate(new Date());
			customMenuService.save(vCustomMenu);
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
		VCustomMenu vCustomMenu = customMenuService.getCustomMenu(id);
		request.setAttribute("customMenu", vCustomMenu);
		return "/weixin/basic/customMenu/customMenuEdit";
	}
	
	/**
	 * 修改
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Json update(HttpServletRequest request,  VCustomMenu vCustomMenu) {
		Json j = new Json();
		try {
			SessionInfo sessionInfo = this.getSessinInfo(request);
			vCustomMenu.setUpdateUser(sessionInfo.getLoginname());
			customMenuService.update(vCustomMenu);
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
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(HttpServletRequest request) {
		Json j = new Json();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			customMenuService.delete(id);
			j.setMsg(ControllerMsg.DEL_MSG_SUCC);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(ControllerMsg.DEL_MSG_ERROR);
			e.printStackTrace();
		}
		return j;
	}
	
	/**
	 * 同步菜单到微信
	 * @return
	 */
	@RequestMapping("/syncMenu")
	@ResponseBody
	public Json syncMenu(HttpServletRequest request,Long acountId) {
		Json j = new Json();
		try {
			//Long acountId = 9L;
			List<VCustomMenu> firstMenus=customMenuService.getFirstMenu(acountId);
			AccountVo accountVo = accountService.getAccount(acountId);
			Menu menu = new Menu();
			// 一级菜单
			Button firstArr[] = new Button[firstMenus.size()];
			int a=0;
			for (VCustomMenu vCustomMenu : firstMenus) {
				List<VCustomMenu> secondMenus = customMenuService.getSecondMenu(vCustomMenu.getId());
				if (secondMenus.size()==0) {
					if("link".equals(vCustomMenu.getMenuType())){
						ViewButton viewButton = new ViewButton();
						viewButton.setName(vCustomMenu.getMenuName());
						viewButton.setType("view");
						viewButton.setUrl(vCustomMenu.getUrlLink());
						firstArr[a] = viewButton;
					}else if("msg".equals(vCustomMenu.getMenuType())){
						CommonButton cb = new CommonButton();
						cb.setKey(vCustomMenu.getKey());
						cb.setName(vCustomMenu.getMenuName());
						cb.setType("click");
						firstArr[a] = cb;
					}
				}else{
					ComplexButton complexButton = new ComplexButton();
					complexButton.setName(vCustomMenu.getMenuName());
					Button[] secondARR = new Button[secondMenus.size()];
					for (int i = 0; i < secondMenus.size(); i++) {
						VCustomMenu children = secondMenus.get(i);
						String type = children.getMenuType();
						if ("link".equals(type)) {
							ViewButton viewButton = new ViewButton();
							viewButton.setName(children.getMenuName());
							viewButton.setType("view");
							viewButton.setUrl(children.getUrlLink());
							secondARR[i] = viewButton;

						} else if ("msg".equals(type)) {
							CommonButton cb1 = new CommonButton();
							cb1.setName(children.getMenuName());
							cb1.setType("click");
							cb1.setKey(children.getKey());
							secondARR[i] = cb1;
						}

					}
					complexButton.setSub_button(secondARR);
					firstArr[a] = complexButton;
				}
				a++;
			}
			menu.setButton(firstArr);
			String accessToken = WxApiClient.getAccessToken(accountVo);
			String url = HttpUtil.menu_create_url.replace("ACCESS_TOKEN",
					accessToken);
			JSONObject jsonObject = WxApi.httpsRequest(url, "POST", JSON.toJSONString(menu));
			String message = null;
			if(jsonObject!=null){
				if (0 == jsonObject.getIntValue( "errcode")) {
						message = "同步菜单信息数据成功！";
				}
				else {
					message = "同步菜单信息数据失败！错误码为："+jsonObject.getIntValue("errcode")+"错误信息为："+jsonObject.getString("errmsg");
				}
			}else{
				message = "同步菜单信息数据失败！同步自定义菜单URL地址不正确。";
			}
			j.setMsg(message);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg("同步失败");
			e.printStackTrace();
		}
		return j;
	}
	
}
