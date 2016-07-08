package com.vanke.mhj.service.api.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vanke.mhj.service.api.WxGroupManageService;
import com.vanke.mhj.service.basic.AccountService;
import com.vanke.mhj.vo.api.WXgroup;
import com.vanke.mhj.vo.basic.AccountVo;
import com.vanke.wxapi.process.WxApi;
import com.vanke.wxapi.process.WxApiClient;
import com.vanke.wxapi.util.HttpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WxGroupManageServiceImpl implements WxGroupManageService{

	@Resource
	private AccountService accountService;

	@SuppressWarnings("unchecked")
	@Override
	public List<WXgroup> getGroupByAPPID(String appid) {
		AccountVo accountVo = accountService.getAccountByAppid(appid);
		String accessToken = WxApiClient.getAccessToken(accountVo);
		String url = HttpUtil.user_group.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = WxApi.httpsRequest(url, "POST", null);
		JSONArray jsonArray = jsonObject.getJSONArray("groups");
		List<WXgroup> list = JSON.parseArray(jsonArray.toJSONString(), WXgroup.class);
		return list;
	}

	@Override
	public JSONObject save(WXgroup wXgroup,Long accountId) {
		AccountVo accountVo = accountService.getAccount(accountId);
		String accessToken = WxApiClient.getAccessToken(accountVo);
		String url = WxApi.getCreateGroup(accessToken);
		JSONObject group = new JSONObject();
		group.put("name", wXgroup.getName());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("group", group);
		JSONObject result = WxApi.httpsRequest(url, "POST", jsonObject.toJSONString());
		return result;
	}

	@Override
	public JSONObject update(WXgroup wXgroup,Long accountId) {
		AccountVo accountVo = accountService.getAccount(accountId);
		String accessToken = WxApiClient.getAccessToken(accountVo);
		String url = WxApi.getUpdateGroupName(accessToken);
		JSONObject group = new JSONObject();
		group.put("name", wXgroup.getName());
		group.put("id", wXgroup.getId());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("group", group);
		JSONObject result = WxApi.httpsRequest(url, "POST", jsonObject.toJSONString());
		return result;
	}


	@Override
	public JSONObject delete(Long accountId, Long id) {
		AccountVo accountVo = accountService.getAccount(accountId);
		String accessToken = WxApiClient.getAccessToken(accountVo);
		String url = WxApi.getDeleteGroup(accessToken);
		JSONObject group = new JSONObject();
		group.put("id", id);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("group", group);
		JSONObject result = WxApi.httpsRequest(url, "POST", jsonObject.toJSONString());
		return result;
	}

}
