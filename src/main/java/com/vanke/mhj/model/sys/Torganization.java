package com.vanke.mhj.model.sys;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.vanke.mhj.model.base.IdEntity;

@Entity
@Table(name = "sys_organization", schema = "")
public class Torganization extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3369636755851781701L;

	/**
	 * 机构名称
	 */
	@Column(name = "NAME", length = 20)
	private String orgName;

	/**
	 * 机构编号
	 */
	@Column(name = "ORG_NO", unique = true, length = 100)
	private String orgNo;

	/**
	 * 机构类型: root, area, company, department(总部，区域，分公司，部门)
	 */
	@Column(name = "ORG_TYPE", length = 10)
	private String orgType;


	/**
	 * 地址
	 */
	@Column(name = "ADDRESS", length = 100)
	private String address;

	/**
	 * 图标
	 */
	@Column(name = "ICONCLS", length = 30)
	private String iconCls;

	/**
	 * 上级
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PID")
	private Torganization organization;

	/**
	 * 排序号
	 */
	@Column(name = "SEQ", length = 3)
	private Integer seq;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
	private Set<Torganization> organizations = new HashSet<Torganization>(0);


	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = this.getOrganization().getOrgNo() + orgNo;
	}


	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Torganization getOrganization() {
		return organization;
	}

	public void setOrganization(Torganization organization) {
		this.organization = organization;
	}

	public Set<Torganization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(Set<Torganization> organizations) {
		this.organizations = organizations;
	}

}
