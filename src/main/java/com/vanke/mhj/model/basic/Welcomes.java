package com.vanke.mhj.model.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vanke.mhj.model.base.IdEntity;
import com.vanke.mhj.model.sys.Torganization;

@Entity
@Table(name="WX_ACCOUNT_WELCOMES")
public class Welcomes extends IdEntity{

	private static final long serialVersionUID = -478362447630880716L;
	
	/**
	 * 机构编号
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_NO", referencedColumnName = "ORG_NO")
	private Torganization organization;
	
	/**
	 * 公众号主键
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID")
	private Account account;
	
	/**
	 * 消息类型
	 */
	@Column(name = "MSGTYPE", length = 10)
	private String msgType;
	
	/**
	 * 消息ID
	 */
	@Column(name = "MSGID", length = 10)
	private Long msgId;
	
	public Torganization getOrganization() {
		return organization;
	}
	public void setOrganization(Torganization organization) {
		this.organization = organization;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
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
	
}
