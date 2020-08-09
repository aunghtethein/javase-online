package com.solt.jdc;

public class Student {
	private String name;
	private String roll;
	private int mya;
	private int eng;
	private int math;
	private int che;
	private int phy;
	private int bio;
	private String result;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoll() {
		return roll;
	}
	public void setRoll(String roll) {
		this.roll = roll;
	}
	public int getMya() {
		return mya;
	}
	public void setMya(int mya) {
		this.mya = mya;
	}
	public int getEng() {
		return eng;
	}
	public void setEng(int eng) {
		this.eng = eng;
	}
	public int getMath() {
		return math;
	}
	public void setMath(int math) {
		this.math = math;
	}
	public int getChe() {
		return che;
	}
	public void setChe(int che) {
		this.che = che;
	}
	public int getPhy() {
		return phy;
	}
	public void setPhy(int phy) {
		this.phy = phy;
	}
	public int getBio() {
		return bio;
	}
	public void setBio(int bio) {
		this.bio = bio;
	}
	public int getTotal() {
		return getMya()+getEng()+getMath()+getChe()+getPhy()+getBio();
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
}
