package com.vanke.mhj.service.api;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.vanke.mhj.vo.api.WXgroup;

public interface WxGroupManageService {

	List<WXgroup> getGroupByAPPID(String appid);

	JSONObject save(WXgroup wXgroup, Long accountId);

	JSONObject update(WXgroup wXgroup, Long accountId);


	/**
	 * @param accountId
	 * @param id
	 * @return
	 */
	JSONObject delete(Long accountId, Long id); 
	
}
