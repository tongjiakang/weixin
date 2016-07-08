package com.vanke.mhj.model.sys;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.vanke.mhj.model.base.IdEntity;

/**
 * 字典Entity
 * 
 * @author CQY
 * @version 2016-01-26
 */
@Entity
@Table(name = "SYS_DICTIONARY")
public class Tdictionary extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5583262207396824596L;

	@Column(name = "VALUE", length = 20)
	private String value; // 数据值

	@Column(name = "LABEL", length = 30)
	private String label; // 标签名

	@Column(name = "TYPE", length = 30)
	private String type; // 类型

	@Column(name = "DESCRIPTION", length = 100)
	private String description;// 描述

	@NotNull
	@Column(name = "SEQ", length = 3)
	private Integer seq = 0; // 排序

	/**
	 * 上级
	 */
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "PID")
	private Tdictionary parentDic;// 父Id

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentDic", cascade = CascadeType.REMOVE)
	private Set<Tdictionary> childrenDic = new HashSet<Tdictionary>(0);

	@NotNull
	@Column(name = "STATUS", length = 2)
	private int status;

	public Tdictionary() {
		super();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Tdictionary getParentDic() {
		return parentDic;
	}

	public void setParentDic(Tdictionary parentDic) {
		this.parentDic = parentDic;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Set<Tdictionary> getChildrenDic() {
		return childrenDic;
	}

	public void setChildrenDic(Set<Tdictionary> childrenDic) {
		this.childrenDic = childrenDic;
	}

}
