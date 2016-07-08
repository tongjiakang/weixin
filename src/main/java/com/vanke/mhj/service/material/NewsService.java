package com.vanke.mhj.service.material;

import java.util.List;

import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.vo.base.Label;
import com.vanke.mhj.vo.material.MsgNewsVo;

public interface NewsService {

	PageModel dataGrid(MsgNewsVo msgNewsVo, PageModel ph) throws Exception;

	void save(MsgNewsVo msgNewsVo) throws Exception;

	MsgNewsVo getMsgNews(Long id) throws Exception;

	void update(MsgNewsVo msgNewsVo) throws Exception;

	void delete(String ids) throws Exception;

	/**
	 * @param orgNo
	 * @return
	 */
	List<Label> getMaterials(String orgNo);

	/**
	 * @param id
	 */
	void changeStatus(Long id);

}
