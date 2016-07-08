package com.vanke.wxapi.service;

import com.alibaba.fastjson.JSONObject;
import com.vanke.mhj.model.material.NewsItem;
import com.vanke.mhj.vo.basic.AccountVo;
import com.vanke.wxapi.vo.MsgRequest;

import java.util.List;

/**
 * 我的微信服务接口，主要用于结合自己的业务和微信接口
 */
public interface MyService {
	
	//消息处理
	public String processMsg(MsgRequest msgRequest,AccountVo accountVo,String projectName);

	//发布菜单
	public JSONObject publishMenu(String gid,AccountVo accountVo);
	
	//删除菜单
	public JSONObject deleteMenu(AccountVo accountVo);
	
	//获取用户列表
	public boolean syncAccountFansList(AccountVo accountVo);


	List<NewsItem> getArticles(long parseLong);
}



