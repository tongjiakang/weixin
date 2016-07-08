package com.common.persistence.pagetable;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author Evan
 * @since 2015年10月22日
 */
public class PageModel implements Serializable {

	private static final long serialVersionUID = -3542668843268550832L;

	public static final String asc = "ASC";

	public static final String desc = "DESC";

	/**
	 * 总记录数
	 */
	private Long total;

	/**
	 * 当前页
	 */
	private int page;

	/**
	 * 每页显示记录数
	 */

	private int pageSize;

	/**
	 * 排序字段
	 */
	private String sort;

	/**
	 * asc/desc
	 */
	private String order;

	/**
	 * 第二排序字段
	 */
	private String secondSort;

	/**
	 * asc/desc
	 */
	private String secondOrder;

	/**
	 * 查询结果集
	 */
	private List<?> rows;

	public PageModel() {
	}

	public PageModel(String sort, String order) {
		this.sort = sort;
		this.order = order;
	}

	public PageModel(int page, int pageSize, String sort, String order) {
		super();
		this.page = page;
		this.pageSize = pageSize;
		this.sort = sort;
		this.order = order;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public List<?> getRows() {
		if (rows == null) {
			rows = Lists.newArrayList();
		}
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public int getStart() {
		if (page == 0) {
			page = 1;
		}
		return (page - 1) * pageSize;
	}

	public void orderBy(String sortField, String orderType) {
		this.sort = sortField;
		this.order = orderType;
	}

	public String getSecondSort() {
		return secondSort;
	}

	public void setSecondSort(String secondSort) {
		this.secondSort = secondSort;
	}

	public String getSecondOrder() {
		return secondOrder;
	}

	public void setSecondOrder(String secondOrder) {
		this.secondOrder = secondOrder;
	}

}
