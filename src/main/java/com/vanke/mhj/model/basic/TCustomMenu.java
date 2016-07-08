/**
 * 
 */
package com.vanke.mhj.model.basic;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.vanke.mhj.model.base.IdEntity;
import com.vanke.mhj.model.material.MsgLink;

/**
 * @author qianwei
 *
 */
@Entity
@Table(name = "wx_account_menu")
public class TCustomMenu extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WEIXIN_ACCOUNT")
	private Account account;

	@Column(name = "MENUNAME", length = 50)
	private String menuName;

	@JoinColumn(name = "PARENTMENU")
	@ManyToOne(fetch = FetchType.LAZY)
	private TCustomMenu parentMenu;

	@Column(name = "MENUTYPE", length = 10)
	private String menuType;

	@Column(name = "MSGTYPE", length = 10)
	private String msgType;

	@Column(name = "MSGID", length = 10)
	private Long msgId;

	@JoinColumn(name = "URL")
	@ManyToOne(fetch = FetchType.LAZY)
	private MsgLink url;
	@Column(name = "MENUKEY", length = 10)
	private String key;

	@Column(name = "MENUORDER", length = 3)
	private Integer order;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy="parentMenu")
	private Set<TCustomMenu> childrenMenu = new HashSet<TCustomMenu>(0);

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public TCustomMenu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(TCustomMenu parentMenu) {
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

	public MsgLink getUrl() {
		return url;
	}

	public void setUrl(MsgLink url) {
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

	public Set<TCustomMenu> getChildrenMenu() {
		return childrenMenu;
	}

	public void setChildrenMenu(Set<TCustomMenu> childrenMenu) {
		this.childrenMenu = childrenMenu;
	}

}
