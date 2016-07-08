package com.vanke.mhj.service.api.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.service.api.WxUserManageService;
import com.vanke.mhj.vo.api.WXuserinfo;
import com.vanke.mhj.vo.base.Json;
import com.vanke.mhj.vo.basic.AccountVo;
import com.vanke.wxapi.process.WxApi;
import com.vanke.wxapi.process.WxApiClient;
import com.vanke.wxapi.util.HttpUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxUserManageServiceImpl implements WxUserManageService{

	@SuppressWarnings("unchecked")
	@Override
	public PageModel dataGrid(AccountVo accountVo, PageModel ph) {
		String accessToken = WxApiClient.getAccessToken(accountVo);
		String openid = "";
		String url = HttpUtil.user_manage_url.replace("ACCESS_TOKEN",accessToken).replace("NEXT_OPENID",openid);
		// 查询所有分组
		String urlGroup = WxApi.getGroupInfo(accessToken);
		JSONObject jsonObjectGroup = WxApi.httpsRequest(urlGroup, "POST", null);
		Map<Integer, String> groupName = new HashMap<Integer, String>();
		JSONArray jsonArray = jsonObjectGroup.getJSONArray("groups");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject group = jsonArray.getJSONObject(i);
			groupName.put(group.getIntValue("id"), group.getString("name"));
		}
		JSONObject jsonObject = WxApi.httpsRequest(url, "POST",null);
		int count = jsonObject.getIntValue("count");
		List<WXuserinfo> list = new ArrayList<>();
		if(count!=0){
			JSONObject data = jsonObject.getJSONObject("data");
			List<String> openids = (List<String>) data.get("openid");
			int i_start = 0;
			if(ph.getPage()!=1){
				i_start = ph.getPageSize()*(ph.getPage()-1);
				if((count-i_start)>ph.getPageSize()){
					count = i_start + ph.getPageSize();
				}
			}else{
				if(count>ph.getPageSize()){
					count = ph.getPageSize();
				}
			}
			for(int i=i_start;i<count;i++){
				String info_url = HttpUtil.user_info_url.replace("ACCESS_TOKEN",accessToken).replace("OPENID",openids.get(i));
				JSONObject jsonObject_info = WxApi.httpsRequest(info_url, "POST",null);
				WXuserinfo wXuserinfo = new WXuserinfo();
				wXuserinfo = JSONObject.toJavaObject(jsonObject_info, WXuserinfo.class);
				wXuserinfo.setGroupName(groupName.get(wXuserinfo.getGroupid()));
				list.add(wXuserinfo);
				System.out.println(jsonObject_info.toJSONString());
			}
		}
		ph.setRows(list);
		ph.setTotal(jsonObject.getLong("total"));
		System.out.println(jsonObject.toJSONString());
		return ph;
	}

	@Override
	public JSONObject move(AccountVo accountVo, int group, String openids) {
		String accessToken = WxApiClient.getAccessToken(accountVo);
		String urlMoveGroup = WxApi.getMoveGroup(accessToken);
		JSONObject requestBody = new JSONObject();
		requestBody.put("openid_list", openids.split(","));
		requestBody.put("to_groupid", group);
		JSONObject result = WxApi.httpsRequest(urlMoveGroup, "POST",requestBody.toJSONString());
		return result;
	}

}
