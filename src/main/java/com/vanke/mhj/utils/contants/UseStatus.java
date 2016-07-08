package com.vanke.mhj.utils.contants;

import java.util.ArrayList;
import java.util.List;

import com.vanke.mhj.vo.base.BaseType;

/**
 * 启用/停用状态枚举
 * 
 * @author AlawnPang
 *
 * @date2016-02-20
 *
 */
public enum UseStatus {

	/**
	 * 状态-启用
	 */
	ACTIVITY(1, "启用"),

	/**
	 * 状态-停用
	 */
	UNACTIVITY(-1, "停用");

	private Integer value;

	private String label;

	private UseStatus(Integer value, String label) {
		this.value = value;
		this.label = label;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public static String getLabel(int value) {
		if (ACTIVITY.getValue().intValue() == value) {
			return ACTIVITY.getLabel();
		} else if (UNACTIVITY.getValue().intValue() == value) {
			return UNACTIVITY.getLabel();
		}
		return "";
	}

	/**
	 * 使用状态列表
	 * 
	 * @param value
	 * @return
	 */
	public static List<BaseType> getUseStatusList() {
		List<BaseType> list = new ArrayList<BaseType>();
		list.add(new BaseType(ACTIVITY.getValue().toString(), ACTIVITY.getLabel()));
		list.add(new BaseType(UNACTIVITY.getValue().toString(), UNACTIVITY.getLabel()));
		return list;
	}

}
