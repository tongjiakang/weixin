package com.vanke.mhj.service.basic;

import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.model.basic.Welcomes;
import com.vanke.mhj.vo.basic.WelcomesVo;

public interface WelcomesService {

	PageModel dataGrid(WelcomesVo welcomesVo, PageModel ph);

	void save(WelcomesVo welcomesVo);

	WelcomesVo getWelcomes(Long id);
	
	Welcomes getWelcomesByAccountId(Long id);

	void update(WelcomesVo welcomesVo);
	
	void delete(String ids);

}
