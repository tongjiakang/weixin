/**
 * 
 */
package com.vanke.mhj.service.basic;

import org.springframework.stereotype.Service;

import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.vo.basic.VKeyWord;

/**
 * @author qianwei
 *
 */
@Service
public interface KeyWordService {

	/**
	 * @param vKeyWord
	 */
	void save(VKeyWord vKeyWord);

	/**
	 * @param vKeyWord
	 * @param ph
	 * @return
	 */
	PageModel dataGrid(VKeyWord vKeyWord, PageModel ph);

	/**
	 * @param id
	 * @return
	 */
	VKeyWord getKeyWord(Long id);

	/**
	 * @param vKeyWord
	 */
	void update(VKeyWord vKeyWord);

	/**
	 * @param id
	 */
	void delete(Long id);

}
