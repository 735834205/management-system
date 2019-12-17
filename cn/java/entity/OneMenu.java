package cn.java.entity;

import java.util.List;

public class OneMenu {
	private Long id;
	private String oneName;
	private List<TwoMenu> twoMenuList;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOneName() {
		return oneName;
	}
	public void setOnName(String oneName) {
		this.oneName = oneName;
	}
	public List<TwoMenu> getTwoMenuList() {
		return twoMenuList;
	}
	public void setTwoMenuList(List<TwoMenu> twoMenuList) {
		this.twoMenuList = twoMenuList;
	}
}
