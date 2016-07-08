/**
 * 
 */
package com.vanke.mhj.vo.base;

/**
 * @author Administrator
 *
 */
public class Label {
	private Long id;
	private String text;
	private boolean selected;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
