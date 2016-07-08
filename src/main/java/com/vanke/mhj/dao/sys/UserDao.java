package com.vanke.mhj.dao.sys;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.common.persistence.condition.Condition;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vanke.mhj.dao.BaseDao;
import com.vanke.mhj.model.sys.Tuser;

/**
 * Evan Yang 2016年2月25日
 */
@Repository("userDao")
public class UserDao extends BaseDao {

	public Tuser getUserByNameOrPhone(String str) throws Exception {
		Tuser user = null;
		String ql = "select u from Tuser u where u.loginname = :loginname or u.phone = :phone";
		try {
			Map<String, Object> parameters = Maps.newHashMap();
			parameters.put("loginname", str);
			parameters.put("phone", str);
			user = getSingleObject(ql, parameters);
		} catch (Exception e) {
			throw new Exception("Cannot find the sepecified user [ " + str + " ]");
		}
		return user;
	}

	public Tuser findUserByLoginname(String loginname) throws Exception {
		List<Condition> conditions = Lists.newArrayList();
		conditions.add(new Condition("loginname", loginname, Condition.EQUAL_TO));
		Tuser t = getSingleResult(Tuser.class, conditions);
		return t;
	}

	public Tuser findUserByPhone(String phone) throws Exception {
		List<Condition> conditions = Lists.newArrayList();
		conditions.add(new Condition("phone", phone, Condition.EQUAL_TO));
		Tuser t = getSingleResult(Tuser.class, conditions);
		return t;
	}

}
