package com.vanke.mhj.service.api;

import com.alibaba.fastjson.JSONObject;
import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.vo.basic.AccountVo;

public interface WxUserManageService {

	PageModel dataGrid(AccountVo accountVo, PageModel ph);

	/**
	 * @param accountVo
	 * @param group
	 * @param openids
	 * @return
	 */
	JSONObject move(AccountVo accountVo, int group, String openids);

}
