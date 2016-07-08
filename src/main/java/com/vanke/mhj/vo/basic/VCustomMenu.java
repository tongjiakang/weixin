/**
 * 
 */
package com.vanke.mhj.vo.basic;

import com.vanke.mhj.vo.base.BaseVo;

/**
 * @author qianwei
 *
 */
public class VCustomMenu extends BaseVo {
	
	private Long account;
	
	private String menuName;
	
	private Long parentMenu;
	
	private String menuType;
	
	private String msgType;
	
	private Long msgId;
	
	private Long url;
	
	private String urlLink;
	
	private String key;
	
	private Integer order;
	
	private String iconCls;

	public Long getAccount() {
		return account;
	}

	public void setAccount(Long account) {
		this.account = account;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Long getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(Long parentMenu) {
		this.parentMenu = parentMenu;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public Long getUrl() {
		return url;
	}

	public void setUrl(Long url) {
		this.url = url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * @return the iconCls
	 */
	public String getIconCls() {
		return iconCls;
	}

	/**
	 * @param iconCls the iconCls to set
	 */
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	/**
	 * @return the urlLink
	 */
	public String getUrlLink() {
		return urlLink;
	}

	/**
	 * @param urlLink the urlLink to set
	 */
	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}

}
