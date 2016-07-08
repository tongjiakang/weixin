/**
 * 
 */
package com.vanke.mhj.service.basic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.dao.basic.CustomMenuDao;
import com.vanke.mhj.model.basic.Account;
import com.vanke.mhj.model.basic.TCustomMenu;
import com.vanke.mhj.model.material.MsgLink;
import com.vanke.mhj.service.basic.CustomMenuService;
import com.vanke.mhj.vo.base.Tree;
import com.vanke.mhj.vo.basic.VCustomMenu;

/**
 * @author qianwei
 *
 */
@Service
public class CustomMenuServiceImpl implements CustomMenuService {
	
	@Resource
	private CustomMenuDao customMenuDao;

	@Override
	public void save(VCustomMenu vCustomMenu) {
		TCustomMenu tCustomMenu = new TCustomMenu();
		BeanUtils.copyProperties(vCustomMenu, tCustomMenu);
		 tCustomMenu.setAccount(customMenuDao.find(vCustomMenu.getAccount(), Account.class));
		if (vCustomMenu.getParentMenu()!=null) {
			 tCustomMenu.setParentMenu(customMenuDao.find(vCustomMenu.getParentMenu(), TCustomMenu.class));
		}
		if (vCustomMenu.getMenuType().equals("link")) {
			 tCustomMenu.setUrl(customMenuDao.find(vCustomMenu.getUrl(), MsgLink.class));
			 tCustomMenu.setMsgType(null);
			 tCustomMenu.setMsgId(null);
		}
		customMenuDao.save(tCustomMenu);
	}

	@Override
	public List<VCustomMenu> treeGrid(VCustomMenu vCustomMenuP) {
		String ql  = "select t from TCustomMenu t where t.account.id = :accountId";
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("accountId", vCustomMenuP.getAccount());
		List<TCustomMenu> tCustomMenus = customMenuDao.getObjects(ql, parameters);
		List<VCustomMenu> vCustomMenus = new ArrayList<VCustomMenu>();
		for (TCustomMenu tCustomMenu : tCustomMenus) {
			VCustomMenu vCustomMenu = new VCustomMenu();
			BeanUtils.copyProperties(tCustomMenu, vCustomMenu);
			vCustomMenu.setAccount(tCustomMenu.getAccount().getId());
			if (tCustomMenu.getUrl()!=null) {
				vCustomMenu.setUrl(tCustomMenu.getUrl().getId());
			}
			if (tCustomMenu.getParentMenu() !=null) {
				vCustomMenu.setParentMenu(tCustomMenu.getParentMenu().getId());
				vCustomMenu.setIconCls("tree-file");
			}else{
				vCustomMenu.setIconCls("icon-folder");
			}
			
			vCustomMenus.add(vCustomMenu);
		}
		
		return vCustomMenus;
	}

	@Override
	public List<Tree> tree(VCustomMenu vCustomMenuP) {
		List<VCustomMenu> vCustomMenus = this.treeGrid(vCustomMenuP);
		List<Tree> trees = new ArrayList<Tree>();
		for (VCustomMenu vCustomMenu : vCustomMenus) {
			Tree tree = new Tree();
			tree.setId(vCustomMenu.getId().toString());
			tree.setText(vCustomMenu.getMenuName());
			if (vCustomMenu.getParentMenu()!=null) {
				tree.setPid(vCustomMenu.getParentMenu().toString());
			}
			trees.add(tree);
		}
		return trees;
	}

	@Override
	public VCustomMenu getCustomMenu(Long id) {
		TCustomMenu tCustomMenu = customMenuDao.find(id, TCustomMenu.class);
		VCustomMenu vCustomMenu = new VCustomMenu();
		BeanUtils.copyProperties(tCustomMenu, vCustomMenu);
		BeanUtils.copyProperties(tCustomMenu, vCustomMenu);
		vCustomMenu.setAccount(tCustomMenu.getAccount().getId());
		if (tCustomMenu.getUrl()!=null) {
			vCustomMenu.setUrl(tCustomMenu.getUrl().getId());
			vCustomMenu.setUrlLink(tCustomMenu.getUrl().getUrl());
		}
		if (tCustomMenu.getParentMenu() !=null) {
			vCustomMenu.setParentMenu(tCustomMenu.getParentMenu().getId());
		}
		return vCustomMenu;
	}

	@Override
	public void update(VCustomMenu vCustomMenu) {
		TCustomMenu  tCustomMenu = customMenuDao.find(vCustomMenu.getId(), TCustomMenu.class);
		
		tCustomMenu.setMenuName(vCustomMenu.getMenuName());
		
		if (vCustomMenu.getParentMenu()!=null) {
			 tCustomMenu.setParentMenu(customMenuDao.find(vCustomMenu.getParentMenu(), TCustomMenu.class));
		}
		
		tCustomMenu.setMenuType(vCustomMenu.getMenuType());
		
		tCustomMenu.setMsgType(vCustomMenu.getMsgType());
		
		tCustomMenu.setMsgId(vCustomMenu.getMsgId());
		
		tCustomMenu.setKey(vCustomMenu.getKey());
		
		tCustomMenu.setOrder(vCustomMenu.getOrder());
		
		
		if (vCustomMenu.getMenuType().equals("link")) {
			 tCustomMenu.setUrl(customMenuDao.find(vCustomMenu.getUrl(), MsgLink.class));
			 tCustomMenu.setMsgType(null);
			 tCustomMenu.setMsgId(null);
		}
		customMenuDao.update(tCustomMenu);
	}

	@Override
	public void delete(Long id) {
		TCustomMenu tCustomMenu = customMenuDao.find(id, TCustomMenu.class);
		del(tCustomMenu);
		
	}
	
	private void del(TCustomMenu tCustomMenu){
		if (tCustomMenu.getChildrenMenu().size()>0) {
			for (TCustomMenu child : tCustomMenu.getChildrenMenu()) {
				del(child);
			}
		}
		customMenuDao.delete(tCustomMenu);
	}

	@Override
	public void syncMenu() {
		
	}

	@Override
	public List<VCustomMenu> getFirstMenu(Long acountId) {
		String ql = "select t from TCustomMenu t where t.account.id= :acountId and parentMenu is null order by t.order asc";
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("acountId", acountId);
		List<TCustomMenu> parentMenus = customMenuDao.getObjects(ql, parameters);
		List<VCustomMenu> vCustomMenus = new ArrayList<VCustomMenu>();
		for (TCustomMenu tCustomMenu : parentMenus) {
			VCustomMenu vCustomMenu = new VCustomMenu();
			BeanUtils.copyProperties(tCustomMenu, vCustomMenu);
			vCustomMenu.setAccount(tCustomMenu.getAccount().getId());
			if (tCustomMenu.getUrl()!=null) {
				vCustomMenu.setUrl(tCustomMenu.getUrl().getId());
				vCustomMenu.setUrlLink(tCustomMenu.getUrl().getUrl());
			}
			if (tCustomMenu.getParentMenu() !=null) {
				vCustomMenu.setParentMenu(tCustomMenu.getParentMenu().getId());
			}
			vCustomMenus.add(vCustomMenu);
		}
		return vCustomMenus;
	}

	@Override
	public List<VCustomMenu> getSecondMenu(Long id) {
		String ql = "select t from TCustomMenu t where t.parentMenu.id= :id order by t.order asc";
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("id", id);
		List<TCustomMenu> parentMenus = customMenuDao.getObjects(ql, parameters);
		List<VCustomMenu> vCustomMenus = new ArrayList<VCustomMenu>();
		for (TCustomMenu tCustomMenu : parentMenus) {
			VCustomMenu vCustomMenu = new VCustomMenu();
			BeanUtils.copyProperties(tCustomMenu, vCustomMenu);
			vCustomMenu.setAccount(tCustomMenu.getAccount().getId());
			if (tCustomMenu.getUrl()!=null) {
				vCustomMenu.setUrl(tCustomMenu.getUrl().getId());
				vCustomMenu.setUrlLink(tCustomMenu.getUrl().getUrl());
			}
			if (tCustomMenu.getParentMenu() !=null) {
				vCustomMenu.setParentMenu(tCustomMenu.getParentMenu().getId());
			}
			vCustomMenus.add(vCustomMenu);
		}
		return vCustomMenus;
	}
}
