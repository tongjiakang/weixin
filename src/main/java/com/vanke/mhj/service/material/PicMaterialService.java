package com.vanke.mhj.service.material;

import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.model.material.NewsItem;
import com.vanke.mhj.vo.base.Json;
import com.vanke.mhj.vo.base.Label;
import com.vanke.mhj.vo.material.VNewsItem;
import com.vanke.mhj.vo.material.VPicNewsTemplate;

import java.util.List;

public interface PicMaterialService {

    PageModel dataGrid(PageModel ph,VPicNewsTemplate vPicNewsTemplate);

    Json saveTemplate(VPicNewsTemplate vPicNewsTemplate);

    void updateTemplate(VPicNewsTemplate vPicNewsTemplate);

	List<NewsItem> getArticles(long parseLong);

	VPicNewsTemplate getTemplateByid(Long id);

	/**
	 * @param orgNo
	 * @return
	 */
	List<Label> getMaterials(String orgNo);

	VNewsItem getVNewsItem(Long id);

	/**
	 * @param id
	 */
	void changeStatus(Long id);

}
