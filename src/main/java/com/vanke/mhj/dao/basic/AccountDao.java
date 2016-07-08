package com.vanke.mhj.dao.basic;

import com.vanke.mhj.dao.BaseDao;
import com.vanke.mhj.model.basic.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by PanJm on 2016/6/29.
 */
@Repository
public class AccountDao extends BaseDao{
    public Account getAccountByAppId(String appId) {
        Query query = getEntityManager().createQuery("select t from Account t where t.appid = ?1");
        query.setParameter(1, appId);
        List resultList = query.getResultList();
        if (resultList != null && resultList.size() > 0) {
            return (Account) resultList.get(0);
        }
        return null;
    }

    public List<Account> listAllAccount() {
        Query query = getEntityManager().createQuery("select t from Account t");
        List resultList = query.getResultList();
        return resultList;
    }
}
