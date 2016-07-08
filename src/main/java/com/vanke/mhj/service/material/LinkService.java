package com.vanke.mhj.service.material;

import java.util.List;

import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.vo.base.Label;
import com.vanke.mhj.vo.material.MsgLinkVo;

public interface LinkService {

	PageModel dataGrid(MsgLinkVo msgLinkVo, PageModel ph);

	MsgLinkVo getMsgLink(Long id);

	void update(MsgLinkVo msgLinkVo);

	void delete(String ids);

	void save(MsgLinkVo msgLinkVo);

	/**
	 * @return
	 */
	List<Label> getLinksLabel();

	/**
	 * @param id
	 */
	void changeStatus(Long id);

}
