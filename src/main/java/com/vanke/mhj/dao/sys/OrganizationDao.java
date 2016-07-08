package com.vanke.mhj.dao.sys;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.persistence.condition.Condition;
import com.google.common.collect.Lists;
import com.vanke.mhj.dao.BaseDao;
import com.vanke.mhj.model.sys.Torganization;
import com.vanke.mhj.model.sys.Tuser;
import com.vanke.mhj.utils.contants.CommonContants;

/**
 * Evan Yang 2016年2月5日
 */
@Repository("OrganizationDao")
public class OrganizationDao extends BaseDao {

	public List<Tuser> getUsersByCurrentOrg(Torganization org) throws Exception {
		String jpql = "select t from Tuser t left join t.organization org where org.id=" + org.getId();

		return find(Tuser.class, jpql);
	}

	public Torganization getOrganizationByNo(String orgNo) throws Exception {
		if (!"".equals(orgNo)) {
			String hql = "select t from Torganization t where t.orgNo = '" + orgNo + "'";
			return this.find(Torganization.class, hql).get(0);
		}
		return null;
	}

	public List<BigInteger> getOrganizationLikeNo(String orgNo) throws Exception {
		if (!"".equals(orgNo)) {
			String hql = "select t.id from Torganization t where t.orgNo like '%" + orgNo + "%'";
			return this.find(BigInteger.class, hql);
		}
		return new ArrayList<BigInteger>();
	}

	public Torganization getHeadQuarter() throws Exception {
		List<Condition> conList = Lists.newArrayList();
		conList.add(new Condition("orgNo", CommonContants.HEAD_QUARTER_ORGNO, Condition.EQUAL_TO));
		Torganization t = getSingleResult(Torganization.class, conList);
		return t;
	}
	
	public List<Torganization> geTorganizationsByOrgNo(String orgNo) throws Exception
	{
		List<Condition> conList = Lists.newArrayList();
		conList.add(new Condition("orgNo", "%" + orgNo.toLowerCase() + "%", Condition.LIKE));
		return getResultList(Torganization.class, conList, null);
	}

}
