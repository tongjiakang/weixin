package com.common.persistence.condition;

import com.common.persistence.contants.DBConstants;

/**
 * 封装查询条件
 * 
 * @author Evan
 * @since 2015年10月22日
 */

public class Condition implements DBConstants
{

	/**
	 * 属性名称
	 */
	private String name;

	/**
	 * 属性值
	 */
	private Object value;

	/**
	 * 运算符
	 */
	private String operate;

	public Condition()
	{

	}

	public Condition(String name, Object value, String operate)
	{
		this.name = name;
		this.value = value;
		this.operate = operate;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}

	public String getOperate()
	{
		return operate;
	}

	public void setOperate(String operate)
	{
		this.operate = operate;
	}

	public boolean hasINOperate()
	{
		return this.operate != null && (this.operate.equals(IN) || this.operate.equals(NOT_IN));
	}

	public boolean hasFindInSet()
	{
		return this.operate != null && (this.operate.equals(FIND_IN_SET));
	}
	
	public boolean hasLikeOperate()
	{
		return this.operate != null && (this.operate.equals(LIKE) || this.operate.equals(NOT_LIKE));
	}

}
