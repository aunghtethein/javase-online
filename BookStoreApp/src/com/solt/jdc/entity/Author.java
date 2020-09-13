package com.solt.jdc.entity;

public class Author implements Comparable<Author> {
	private int id;
	private String authorName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return authorName;
	}
	@Override
	public int compareTo(Author o) {
		// TODO Auto-generated method stub
		return authorName.compareTo(o.authorName);
	}

}
