/**
 * 
 */
package com.vanke.mhj.dao.basic;

import com.vanke.mhj.model.basic.TCustomMenu;
import org.springframework.stereotype.Repository;

import com.vanke.mhj.dao.BaseDao;

import javax.persistence.Query;
import java.util.List;

/**
 * @author qianwei
 *
 */
@Repository
public class CustomMenuDao extends BaseDao{

    public TCustomMenu findByMenuKey(String key) {
        Query query = em.createQuery("select t from TCustomMenu t where t.key = ?1");
        query.setParameter(1, key);
        List resultList = query.getResultList();
        TCustomMenu tCustomMenu = null;
        if (resultList != null && resultList.size() > 0) {
            tCustomMenu = (TCustomMenu) resultList.get(0);
        }
        return tCustomMenu;
    }
}
