/**
 * 
 */
package com.vanke.mhj.model.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vanke.mhj.model.base.IdEntity;
import com.vanke.mhj.model.sys.Torganization;

/**
 * @author qianwei
 *
 */
@Entity
@Table(name="wx_account_keyword")
public class TKeyWord extends IdEntity {
	
	@JoinColumn(name="ACCOUNTID")
	@ManyToOne(fetch=FetchType.LAZY)
	private Account account;
	
	@Column(name = "NAME", length = 50)
	private String name;
	@Column(name = "MSGTYPE", length = 10)
	private String msgType;
	@Column(name = "MSGID", length = 10)
	private Long msgId;
	

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@JoinColumn(name="ORG_NO",referencedColumnName="ORG_NO")
	@ManyToOne(fetch=FetchType.LAZY)
	private Torganization  organization;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Torganization getOrganization() {
		return organization;
	}

	public void setOrganization(Torganization organization) {
		this.organization = organization;
	}

}
