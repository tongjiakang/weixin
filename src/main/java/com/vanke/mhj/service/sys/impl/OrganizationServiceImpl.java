package com.vanke.mhj.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.common.persistence.condition.Condition;
import com.google.common.collect.Lists;
import com.vanke.mhj.dao.sys.OrganizationDao;
import com.vanke.mhj.model.sys.Torganization;
import com.vanke.mhj.model.sys.Tuser;
import com.vanke.mhj.service.base.ServiceException;
import com.vanke.mhj.service.sys.OrganizationService;
import com.vanke.mhj.utils.ControllerMsg;
import com.vanke.mhj.utils.contants.CommonContants;
import com.vanke.mhj.vo.base.SessionInfo;
import com.vanke.mhj.vo.base.Tree;
import com.vanke.mhj.vo.sys.Organization;

@Service
public class OrganizationServiceImpl implements OrganizationService
{

	@Resource
	private OrganizationDao dao;

	@Override
	public List<Organization> treeGrid(String orgNo) throws Exception
	{
		List<Condition> conList = new ArrayList<Condition>();
		if (!StringUtils.isEmpty(orgNo))
		{
			conList.add(new Condition("orgNo", orgNo.toLowerCase() + "%", Condition.LIKE));
		}
		List<Organization> lr = new ArrayList<Organization>();
		List<Torganization> l = dao.getPageResult(Torganization.class, conList, null).getReultList();
		if ((l != null) && (l.size() > 0))
		{
			for (Torganization t : l)
			{
				Organization r = new Organization();
				BeanUtils.copyProperties(t, r);
				if (t.getOrganization() != null)
				{
					r.setPid(t.getOrganization().getId());
					r.setPname(t.getOrganization().getOrgName());
				}
				lr.add(r);
			}
		}
		return lr;
	}

	@Override
	public void add(Organization org) throws Exception
	{
		Torganization t = new Torganization();
		t.setOrgName(org.getOrgName());
		t.setOrgType(org.getOrgType());
		t.setSeq(org.getSeq());
		t.setIconCls(org.getIconCls());
		t.setAddress(org.getAddress());
		t.setCreateUser(org.getCreateUser());
		// BeanUtils.copyProperties(org, t);
		if (org.getPid() != null)
		{
			t.setOrganization(dao.find(org.getPid(), Torganization.class));
		}
		if (org.getAreaId() != null)
		{
			//t.setArea(dao.find(org.getAreaId(), Tarea.class));
		}
		String orgNo = "";
		if (org.getOrgType().equals("company"))
		{
			orgNo = "C";
		}
		else if (org.getOrgType().equals("department"))
		{
			orgNo = "D";
		}
		orgNo += String.valueOf(dao.getSequenceNum("ORG"));
		t.setOrgNo(orgNo);
		dao.save(t);
	}

	@Override
	public void delete(Long id) throws Exception
	{
		Torganization t = dao.find(id, Torganization.class);
		del(t);
	}

	private void del(Torganization t) throws Exception
	{
		if (t.getOrgNo().equals(CommonContants.HEAD_QUARTER_ORGNO))
		{
			throw new ServiceException(ControllerMsg.ORG_CANNOT_DELETE_DEFAULT_ORG_ERROR);
		}
		List<Tuser> list = dao.getUsersByCurrentOrg(t);
		if (list != null && list.size() > 0)
		{
			throw new ServiceException(ControllerMsg.ORG_USED_ERROR);
		}
		else
		{
			if ((t.getOrganizations() != null) && (t.getOrganizations().size() > 0))
			{
				for (Torganization r : t.getOrganizations())
				{
					del(r);
				}
			}
			dao.delete(t);
		}
	}

	@Override
	public void edit(Organization r) throws Exception
	{
		Torganization t = dao.find(r.getId(), Torganization.class);
		t.setIconCls(r.getIconCls());
		t.setOrgName(r.getOrgName());
		t.setSeq(r.getSeq());
		if (r.getPid() != null)
		{
			t.setOrganization(dao.find(r.getPid(), Torganization.class));
		}
		t.setAddress(r.getAddress());
		//t.setArea(dao.find(r.getAreaId(), Tarea.class));
		t.setUpdateUser(r.getUpdateUser());
		dao.update(t);
	}

	@Override
	public Organization get(Long id)  
	{
		Torganization t = dao.find(id, Torganization.class);
		Organization r = new Organization();
		BeanUtils.copyProperties(t, r);
		if (t.getOrganization() != null)
		{
			r.setPid(t.getOrganization().getId());
			r.setPname(t.getOrganization().getOrgName());
			//r.setAreaId(t.getArea().getId());
		}
		return r;
	}

	@Override
	public List<Tree> tree(String orgNo) throws Exception
	{
		List<Condition> conList = new ArrayList<Condition>();
		if (!StringUtils.isEmpty(orgNo))
		{
			conList.add(new Condition("orgNo", orgNo.toLowerCase() + "%", Condition.LIKE));
		}
		List<Torganization> l = dao.getPageResult(Torganization.class, conList, null).getReultList();
		List<Tree> lt = new ArrayList<Tree>();
		if ((l != null) && (l.size() > 0))
		{
			for (Torganization r : l)
			{
				Tree tree = new Tree();
				tree.setId(r.getId().toString());
				if (r.getOrganization() != null)
				{
					tree.setPid(r.getOrganization().getId().toString());
				}
				tree.setText(r.getOrgName());
				tree.setIconCls(r.getIconCls());
				lt.add(tree);
			}
		}
		return lt;
	}

	@Override
	public List<Tree> treeOwn(String orgType, String orgNo) throws Exception
	{
		List<Condition> conList = new ArrayList<Condition>();
		if (!StringUtils.isEmpty(orgType))
		{
			conList.add(new Condition("orgType", orgType, Condition.EQUAL_TO));
		}
		if (!StringUtils.isEmpty(orgNo))
		{
			conList.add(new Condition("orgNo", "%" + orgNo.toLowerCase() + "%", Condition.LIKE));
		}
		List<Torganization> l = dao.getPageResult(Torganization.class, conList, null).getReultList();
		List<Tree> lt = new ArrayList<Tree>();
		if ((l != null) && (l.size() > 0))
		{
			for (Torganization r : l)
			{
				Tree tree = new Tree();
				tree.setId(r.getId().toString());
				if (r.getOrganization() != null)
				{
					tree.setPid(r.getOrganization().getId().toString());
				}
				tree.setText(r.getOrgName());
				tree.setIconCls(r.getIconCls());
				lt.add(tree);
			}
		}
		return lt;
	}

	@Override
	public List<Tree> loadRelatedOrgs(SessionInfo sessionInfo) throws Exception
	{
		List<Tree> data = Lists.newArrayList();
		String roleType = sessionInfo.getRoleType();
		List<Torganization> orgs = null;
		// 如果是地区管理员或者分司管理员，则只加载该组织机构下的所有机构.
		if (roleType.equals(CommonContants.AREA_ADMIN) || roleType.equals(CommonContants.COMP_ADMIN))
		{
			orgs = dao.geTorganizationsByOrgNo(sessionInfo.getOrgNo());
		}
		// 如果是系统管理员，则加载所有的组织机构.
		else
		{
			orgs = dao.getResultList(Torganization.class, null, null);
		}

		for (Torganization o : orgs)
		{
			Tree tree = new Tree();
			tree.setId(o.getId().toString());
			if (o.getOrganization() != null)
			{
				tree.setPid(o.getOrganization().getId().toString());
			}
			tree.setText(o.getOrgName());
			tree.setIconCls(o.getIconCls());
			data.add(tree);
		}

		return data;
	}

}
