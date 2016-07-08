/**
 * 
 */
package com.vanke.mhj.service.basic;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vanke.mhj.vo.base.Tree;
import com.vanke.mhj.vo.basic.VCustomMenu;

/**
 * @author qianwei
 *
 */
@Service
public interface CustomMenuService {

	/**
	 * @param vCustomMenu
	 */
	void save(VCustomMenu vCustomMenu);

	/**
	 * @return
	 */
	List<VCustomMenu> treeGrid(VCustomMenu vCustomMenuP);

	/**
	 * @return
	 */
	List<Tree> tree(VCustomMenu vCustomMenuP);

	/**
	 * @param id
	 * @return
	 */
	VCustomMenu getCustomMenu(Long id);

	/**
	 * @param vCustomMenu
	 */
	void update(VCustomMenu vCustomMenu);

	/**
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 
	 */
	void syncMenu();

	/**
	 * @param acountId
	 * @return
	 */
	List<VCustomMenu> getFirstMenu(Long acountId);

	/**
	 * @param id
	 * @return
	 */
	List<VCustomMenu> getSecondMenu(Long id);

}
