package com.vanke.mhj.service.basic;

import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.vo.basic.VUnKnowWord;
import org.springframework.stereotype.Service;

/**
 * Created by PanJM on 2016/6/29.
 */
@Service
public interface UnKnowWordService {
    PageModel dataGrid(VUnKnowWord vUnKnowWord, PageModel ph) throws Exception;

    void save(VUnKnowWord vUnKnowWord) throws Exception;

    VUnKnowWord getVUnKnowWord(Long id) throws Exception;

    void edit(VUnKnowWord vUnKnowWord) throws Exception;

    void delete(Long id) throws Exception;
}
