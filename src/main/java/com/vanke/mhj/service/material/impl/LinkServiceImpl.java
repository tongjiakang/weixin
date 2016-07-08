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
import com.vanke.mhj.model.sys.Torganization;
import com.vanke.mhj.service.material.LinkService;
import com.vanke.mhj.service.sys.OrganizationService;
import com.vanke.mhj.vo.base.Label;
import com.vanke.mhj.vo.material.MsgLinkVo;
import com.vanke.mhj.vo.sys.Organization;

@Service
public class LinkServiceImpl implements LinkService{

	@Resource
	private BaseDao baseDao;
	
	@Resource
	private OrganizationService organizationService;
	
	@Override
	public PageModel dataGrid(MsgLinkVo msgLinkVo, PageModel ph){
		List<Condition> conditionList = Lists.newArrayList();
		if (!StringUtils.isEmpty(msgLinkVo.getOrgId())) {
			Organization org = organizationService.get(msgLinkVo.getOrgId());
			msgLinkVo.setOrgNo(org.getOrgNo());
			conditionList.add(new Condition("organization.orgNo", msgLinkVo.getOrgNo().toLowerCase() + "%", Condition.LIKE));
		}
		if(!StringUtils.isEmpty(msgLinkVo.getName())){
			conditionList.add(new Condition("name","%"+msgLinkVo.getName()+"%",Condition.LIKE));
		}
		QueryResult<MsgLink> pageResult = baseDao.getPageResult(MsgLink.class, conditionList, ph);
		List<MsgLinkVo> msgLinkVos = new ArrayList<>();
		for (MsgLink msgLink : pageResult.getReultList()) {
			MsgLinkVo v = new MsgLinkVo();
			BeanUtils.copyProperties(msgLink, v);
			Torganization org = msgLink.getOrganization();
			if (org != null) {
				v.setOrgId(org.getId());
				v.setOrgNo(org.getOrgNo());
				v.setOrgName(org.getOrgName());
			}
			msgLinkVos.add(v);
		}
		ph.setTotal(pageResult.getTotalCount());
		ph.setRows(msgLinkVos);
		return ph;
	}

	@Override
	public MsgLinkVo getMsgLink(Long id) {
		MsgLink msgLink = baseDao.find(id, MsgLink.class);
		MsgLinkVo msgLinkVo = new MsgLinkVo();
		BeanUtils.copyProperties(msgLink, msgLinkVo);
		return msgLinkVo;
	}

	@Override
	public void update(MsgLinkVo msgLinkVo) {
		MsgLink msgLink = baseDao.find(msgLinkVo.getId(), MsgLink.class);
		msgLink.setName(msgLinkVo.getName());
		msgLink.setUrl(msgLinkVo.getUrl());
		msgLink.setUpdateDate(msgLinkVo.getUpdateDate());
		msgLink.setUpdateUser(msgLinkVo.getUpdateUser());
		baseDao.update(msgLink);
	}

	@Override
	public void delete(String ids) {
		String[] idArray = ids.split(",");
		List<MsgLink> list = new ArrayList<MsgLink>();
		for (int i = 0; i < idArray.length; i++) {
			MsgLink msgLink = new MsgLink();
			msgLink.setId(Long.valueOf(idArray[i]));
			list.add(msgLink);
		}
		baseDao.delete(list);
	}

	@Override
	public void save(MsgLinkVo msgLinkVo) {
		MsgLink msgLink = new MsgLink();
		BeanUtils.copyProperties(msgLinkVo,msgLink);
		msgLink.setOrganization(baseDao.find(msgLinkVo.getOrgId(), Torganization.class));
		baseDao.save(msgLink);
	}

	@Override
	public List<Label> getLinksLabel() {
		String ql = "select t from MsgLink t";
		Map<String, Object> parameters = new HashMap<String,Object>();
		List<MsgLink> msgLinks = baseDao.getObjects(ql, parameters);
		List<Label> labels = new ArrayList<Label>();
		for (MsgLink msgLink : msgLinks) {
			Label label = new Label();
			label.setId(msgLink.getId());
			label.setText(msgLink.getName());
			labels.add(label);
		}
		return labels;
	}

	@Override
	public void changeStatus(Long id) {
		MsgLink msgLink = baseDao.find(id, MsgLink.class);
		if (msgLink.getUseStatus()==1) {
			msgLink.setUseStatus(-1);
		}else {
			msgLink.setUseStatus(1);
		}
		baseDao.update(msgLink);
		
	}
}
