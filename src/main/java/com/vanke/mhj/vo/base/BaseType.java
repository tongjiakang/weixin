package com.vanke.mhj.vo.base;

public class BaseType {
	
	private String value;
	
	private String label;
	
	public BaseType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BaseType(String value, String label) {
		super();
		this.value = value;
		this.label = label;
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
	
}
