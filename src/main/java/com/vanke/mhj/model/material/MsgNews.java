package com.vanke.mhj.model.material;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vanke.mhj.model.base.IdEntity;
import com.vanke.mhj.model.sys.Torganization;
import com.vanke.mhj.utils.contants.UseStatus;

@Entity
@Table(name="wx_msg_news")
public class MsgNews extends IdEntity{

	private static final long serialVersionUID = -701165110003981205L;

	/**
	 * 机构编号
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_NO", referencedColumnName = "ORG_NO")
	private Torganization organization;
	
	/**
	 * 名称
	 */
	@Column(name="NAME",length=50)
	private String name;
	
	/**
	 * 内容
	 */
	@Column(name="CONTENT",length=500)
	private String content;
	
	@Column(name="USESTATUS",length=2)
	private Integer useStatus = UseStatus.ACTIVITY.getValue();

	public Torganization getOrganization() {
		return organization;
	}

	public void setOrganization(Torganization organization) {
		this.organization = organization;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the userStatus
	 */
	public Integer getUseStatus() {
		return useStatus;
	}

	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}
	
	
}
