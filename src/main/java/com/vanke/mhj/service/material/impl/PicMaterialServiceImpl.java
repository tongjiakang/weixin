package com.vanke.mhj.service.material.impl;

import com.common.persistence.condition.Condition;
import com.common.persistence.pagetable.PageModel;
import com.common.persistence.pagetable.QueryResult;
import com.vanke.mhj.dao.material.PicMaterialDao;
import com.vanke.mhj.model.material.MsgNews;
import com.vanke.mhj.model.material.NewsItem;
import com.vanke.mhj.model.material.NewsTemplate;
import com.vanke.mhj.model.sys.Torganization;
import com.vanke.mhj.service.material.PicMaterialService;
import com.vanke.mhj.vo.base.Json;
import com.vanke.mhj.vo.base.Label;
import com.vanke.mhj.vo.material.VNewsItem;
import com.vanke.mhj.vo.material.VPicNewsTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class PicMaterialServiceImpl implements PicMaterialService {

    @Autowired
    private PicMaterialDao picMaterialDao;

    @Override
    public PageModel dataGrid(PageModel ph,VPicNewsTemplate vPicNewsTemplate) {
        List<Condition> conditions = new ArrayList<Condition>();
        if(vPicNewsTemplate.getOrgId()!=null){
        	conditions.add(new Condition("organization.orgNo","%"+picMaterialDao.find(vPicNewsTemplate.getOrgId(), Torganization.class).getOrgNo().toLowerCase()+"%" , Condition.LIKE));
        }
        if(!StringUtils.isEmpty(vPicNewsTemplate.getTemplateName())){
        	conditions.add(new Condition("templateName","%"+vPicNewsTemplate.getTemplateName().toLowerCase()+"%" , Condition.LIKE));
        }
        List<VPicNewsTemplate> vPicNewsTemplates = new ArrayList<VPicNewsTemplate>();
        QueryResult<NewsTemplate> result = picMaterialDao.getPageResult(NewsTemplate.class, conditions, ph);
        for (NewsTemplate newsTemplate : result.getReultList()) {
            VPicNewsTemplate picNewsTemplate = new VPicNewsTemplate();
            BeanUtils.copyProperties(newsTemplate, picNewsTemplate);
            picNewsTemplate.setName(newsTemplate.getTemplateName());
            picNewsTemplate.setOrgName(newsTemplate.getOrganization() == null ? "" : newsTemplate.getOrganization().getOrgName());
            vPicNewsTemplates.add(picNewsTemplate);
        }
        ph.setRows(vPicNewsTemplates);
        ph.setTotal(result.getTotalCount());
        return ph;
    }
    
	@Override
	public List<Label> getMaterials(String orgNo) {
		String sql = "select t from NewsTemplate t where t.organization.orgNo like :orgNo ";
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("orgNo", "%"+orgNo+"%");
		List<NewsTemplate>  list = picMaterialDao.getObjects(sql, parameters);
		List<Label> labels = new ArrayList<Label>();
		for (NewsTemplate newsTemplate : list) {
			Label label = new Label();
			label.setId(newsTemplate.getId());
			label.setText(newsTemplate.getTemplateName());
			labels.add(label);
		}
		return labels;
	}
	
	
    @Override
    public Json saveTemplate(VPicNewsTemplate vPicNewsTemplate) {
        Json json = new Json();
        NewsTemplate newsTemplate = new NewsTemplate();
        BeanUtils.copyProperties(vPicNewsTemplate, newsTemplate);
        try {
            newsTemplate.setOrganization(picMaterialDao.find(vPicNewsTemplate.getOrgId(), Torganization.class));
            picMaterialDao.save(newsTemplate);
        } catch (Exception e) {
            json.setMsg("操作失败");
        }
        json.setMsg("操作成功");
        json.setSuccess(true);
        return json;
    }

    @Override
    public void updateTemplate(VPicNewsTemplate vPicNewsTemplate) {
    	
        NewsTemplate newsTemplate = picMaterialDao.find(vPicNewsTemplate.getId(), NewsTemplate.class);
        newsTemplate.setOrganization(picMaterialDao.find(vPicNewsTemplate.getOrgId(), Torganization.class));
        newsTemplate.setUpdateUser(vPicNewsTemplate.getUpdateUser());
        newsTemplate.setTemplateName(vPicNewsTemplate.getTemplateName());
        newsTemplate.setType(vPicNewsTemplate.getType());
        picMaterialDao.update(newsTemplate);

    }

	@Override
	public List<NewsItem> getArticles(long parseLong) {
		NewsTemplate newsTemplate =	picMaterialDao.find(parseLong, NewsTemplate.class);
		List<NewsItem> items = new ArrayList<NewsItem>(newsTemplate.getNewsItemList());
		Collections.sort(items, new Comparator<NewsItem>() {
			@Override
			public int compare(NewsItem o1,NewsItem o2) {
				return o1.getOrders().compareTo(o2.getOrders());
			}
		});
		return items;
	}

	@Override
	public VPicNewsTemplate getTemplateByid(Long id) {
		NewsTemplate newsTemplate = picMaterialDao.find(id, NewsTemplate.class);
        VPicNewsTemplate vPicNewsTemplate = new VPicNewsTemplate();
        BeanUtils.copyProperties(newsTemplate, vPicNewsTemplate);
        vPicNewsTemplate.setOrgId(newsTemplate.getOrganization() == null ? null : newsTemplate.getOrganization().getId());
        return vPicNewsTemplate;
	}

    @Override
    public VNewsItem getVNewsItem(Long id) {
        NewsItem newsItem = picMaterialDao.find(id, NewsItem.class);
        VNewsItem vNewsItem = new VNewsItem();
        BeanUtils.copyProperties(newsItem, vNewsItem);
        return vNewsItem;
    }

	@Override
	public void changeStatus(Long id) {
		NewsTemplate msgNews = picMaterialDao.find(id, NewsTemplate.class);
		if (msgNews.getUseStatus()==1) {
			msgNews.setUseStatus(-1);
		}else {
			msgNews.setUseStatus(1);
		}
		picMaterialDao.update(msgNews);
		
	}
}
