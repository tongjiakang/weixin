/**
 * 
 */
package com.vanke.mhj.dao.basic;

import com.vanke.mhj.model.basic.TKeyWord;
import org.springframework.stereotype.Repository;

import com.vanke.mhj.dao.BaseDao;

import javax.persistence.Query;
import java.util.List;

/**
 * @author PanJm
 *
 */
@Repository
public class KeyWordDao extends BaseDao {

    /**
     * 查询所有关键字
     * @return
     */
    public List<TKeyWord> listAll() {
        Query query = em.createQuery("select t from TKeyWord t");
        List resultList = query.getResultList();
        return resultList;
    }

    /**
     * 根据名字获取关键字
     * @param name
     * @return
     */
    public List<TKeyWord> getKeyWordsByName(String name) {
        Query query = em.createQuery("select t from TKeyWord t where t.name = ?1");
        query.setParameter(1, name);
        List resultList = query.getResultList();
        return resultList;
    }
}
