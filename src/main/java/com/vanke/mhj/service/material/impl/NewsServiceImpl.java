package com.vanke.mhj.service.material.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.common.persistence.condition.Condition;
import com.common.persistence.pagetable.PageModel;
import com.common.persistence.pagetable.QueryResult;
import com.google.common.collect.Lists;
import com.vanke.mhj.dao.BaseDao;
import com.vanke.mhj.model.material.MsgLink;
import com.vanke.mhj.model.material.MsgNews;
import com.vanke.mhj.model.material.NewsTemplate;
import com.vanke.mhj.model.sys.Torganization;
import com.vanke.mhj.service.material.NewsService;
import com.vanke.mhj.service.sys.OrganizationService;
import com.vanke.mhj.vo.base.Label;
import com.vanke.mhj.vo.material.MsgNewsVo;
import com.vanke.mhj.vo.sys.Organization;

@Service
public class NewsServiceImpl implements NewsService{

	@Resource
	private BaseDao baseDao;
	
	@Resource
	private OrganizationService organizationService;
	
	@Override
	public PageModel dataGrid(MsgNewsVo msgNewsVo, PageModel ph) {
		List<Condition> conditionList = Lists.newArrayList();
		if (!StringUtils.isEmpty(msgNewsVo.getOrgId())) {
			Organization org = organizationService.get(msgNewsVo.getOrgId());
			msgNewsVo.setOrgNo(org.getOrgNo());
			conditionList.add(new Condition("organization.orgNo", msgNewsVo.getOrgNo().toLowerCase() + "%", Condition.LIKE));
		}
		if(!StringUtils.isEmpty(msgNewsVo.getName())){
			conditionList.add(new Condition("name","%"+msgNewsVo.getName()+"%",Condition.LIKE));
		}
		QueryResult<MsgNews> pageResult = baseDao.getPageResult(MsgNews.class, conditionList, ph);
		List<MsgNewsVo> msgNewsVos = new ArrayList<>();
		for (MsgNews msgNews : pageResult.getReultList()) {
			MsgNewsVo v = new MsgNewsVo();
			BeanUtils.copyProperties(msgNews, v);
			Torganization org = msgNews.getOrganization();
			if (org != null) {
				v.setOrgId(org.getId());
				v.setOrgNo(org.getOrgNo());
				v.setOrgName(org.getOrgName());
			}
			msgNewsVos.add(v);
		}
		ph.setTotal(pageResult.getTotalCount());
		ph.setRows(msgNewsVos);
		return ph;
	}
	
	@Override
	public List<Label> getMaterials(String orgNo) {
		String sql = "select t from MsgNews t where t.organization.orgNo like :orgNo ";
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("orgNo", "%"+orgNo+"%");
		List<MsgNews>  list = baseDao.getObjects(sql, parameters);
		List<Label> labels = new ArrayList<Label>();
		for (MsgNews newsTemplate : list) {
			Label label = new Label();
			label.setId(newsTemplate.getId());
			label.setText(newsTemplate.getName());
			labels.add(label);
		}
		return labels;
	}
	
	@Override
	public void save(MsgNewsVo msgNewsVo) {
		MsgNews msgNews = new MsgNews();
		BeanUtils.copyProperties(msgNewsVo,msgNews);
		msgNews.setOrganization(baseDao.find(msgNewsVo.getOrgId(), Torganization.class));
		baseDao.save(msgNews);
	}

	@Override
	public MsgNewsVo getMsgNews(Long id) {
		MsgNews msgNews = baseDao.find(id, MsgNews.class);
		MsgNewsVo msgNewsVo = new MsgNewsVo();
		BeanUtils.copyProperties(msgNews, msgNewsVo);
		return msgNewsVo;
	}

	@Override
	public void update(MsgNewsVo msgNewsVo) {
		MsgNews msgNews = baseDao.find(msgNewsVo.getId(), MsgNews.class);
		msgNews.setName(msgNewsVo.getName());
		msgNews.setContent(msgNewsVo.getContent());
		msgNews.setUpdateDate(msgNewsVo.getUpdateDate());
		msgNews.setUpdateUser(msgNewsVo.getUpdateUser());
		baseDao.update(msgNews);
	}

	@Override
	public void delete(String ids) {
		String[] idArray = ids.split(",");
		List<MsgNews> list = new ArrayList<MsgNews>();
		for (int i = 0; i < idArray.length; i++) {
			MsgNews msgNews = new MsgNews();
			msgNews.setId(Long.valueOf(idArray[i]));
			list.add(msgNews);
		}
		baseDao.delete(list);
	}

	@Override
	public void changeStatus(Long id) {
		MsgNews msgNews = baseDao.find(id, MsgNews.class);
		if (msgNews.getUseStatus()==1) {
			msgNews.setUseStatus(-1);
		}else {
			msgNews.setUseStatus(1);
		}
		baseDao.update(msgNews);
	}


}
