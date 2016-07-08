package com.vanke.mhj.service.basic;

import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.vo.base.Label;
import com.vanke.mhj.vo.basic.AccountVo;

import java.util.List;

public interface AccountService {

	void delete(String ids);

	void update(AccountVo accountVo);

	AccountVo getAccount(Long id);

	void save(AccountVo accountVo);

	PageModel dataGrid(AccountVo accountVo, PageModel ph);

	AccountVo getAccountByAppid(String appid);

	/**
	 * @return
	 */
	List<Label> getAcountsLabel(String orgNo);

//	void addAccessTokenCache();
}
