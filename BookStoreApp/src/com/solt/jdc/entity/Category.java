package com.solt.jdc.entity;

public class Category implements Comparable<Category> {
	private int id;
	private String categoryName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return categoryName;
	}
	@Override
	public int compareTo(Category o) {
		// TODO Auto-generated method stub
		return categoryName.compareTo(o.categoryName);
	}

}
