/**
 * 
 */
package com.vanke.mhj.dao.basic;

import com.vanke.mhj.dao.BaseDao;
import com.vanke.mhj.model.basic.TUnKnowWord;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author PanJM
 *
 */
@Repository
public class UnKnowWordDao extends BaseDao {

    /**
     * 是否已经为某个公众号设置了未识别回复语
     * @param appid
     * @return
     */
    public boolean hasAccount(String appid) {
        Query query = getEntityManager().createQuery("select t from TUnKnowWord t where t.account.appid = ?1");
        query.setParameter(1, appid);
        List resultList = query.getResultList();
        if (resultList != null && resultList.size() > 0) {
            return true;
        }
        return false;
    }

    public TUnKnowWord getByAppid(String appId) {
        Query query = em.createQuery("select t from TUnKnowWord t where t.account.appid = ?1");
        query.setParameter(1, appId);
        List resultList = query.getResultList();
        TUnKnowWord tUnKnowWord = null;
        if (resultList != null && resultList.size() > 0) {
            tUnKnowWord = (TUnKnowWord) resultList.get(0);
        }
        return tUnKnowWord;
    }
}
