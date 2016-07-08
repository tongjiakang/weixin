package com.common.persistence.pagetable;

import java.util.List;

import com.common.persistence.util.CollectionsUtil;

/**
 * 分页结果集封装类
 * 
 * @author Evan
 * @since 2015年10月20日
 */

public class QueryResult<T>
{
	
	public static final int DEFAULT_PAGE_SIZE = 100;
	
	/**
	 * 总记录数
	 */
	private long totalCount;

	/**
	 * 总页数
	 */
	private long pageCount;

	/**
	 * 当前页数
	 */
	private long currentPage;

	/**
	 * 每页显示数量
	 */
	private long pageSize;

	/**
	 * 查询结果集
	 */
	private List<T> reultList;
	
	public QueryResult()
	{
		reultList = CollectionsUtil.newArrayList();
	}

	public long getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(long totalCount)
	{
		this.totalCount = totalCount;
	}

	public long getPageCount()
	{
		return pageCount;
	}

	public void setPageCount(long pageCount)
	{
		this.pageCount = pageCount;
	}

	public long getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(long currentPage)
	{
		this.currentPage = currentPage;
	}

	public long getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(long pageSize)
	{
		this.pageSize = pageSize;
	}

	public List<T> getReultList()
	{
		if(reultList == null)
		{
			reultList = CollectionsUtil.newArrayList();
		}
		return reultList;
	}

	public void setReultList(List<T> reultList)
	{
		this.reultList = reultList;
	}

	public void calcuatePageCount(PageModel page)
	{
		if(page == null)
		{
			currentPage = 1;
			pageSize = DEFAULT_PAGE_SIZE;
		}
		else
		{
			currentPage = page.getPage();
			pageSize = page.getPageSize();
		}
		
		if (totalCount > 0)
		{
			pageCount = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize + 1);
		}
	}

}
