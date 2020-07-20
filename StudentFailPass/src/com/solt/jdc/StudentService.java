package com.solt.jdc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



public class StudentService {

	public void add(Student student) {
		String sql = "insert into student (name,roll,mya,eng,math,che,phy,bio,result,total) values (?,?,?,?,?,?,?,?,?,?)";
		
		try(Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, student.getName());
			stmt.setString(2, student.getRoll());
			stmt.setInt(3, student.getMya());
			stmt.setInt(4, student.getEng());
			stmt.setInt(5, student.getMath());
			stmt.setInt(6, student.getChe());
			stmt.setInt(7, student.getPhy());
			stmt.setInt(8, student.getBio());
			stmt.setString(9, student.getResult());
			stmt.setInt(10, student.getTotal());
			
			
			stmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Student> findAll() {
		List<Student> list = new ArrayList<Student>();
		String sql = "select * from student";
		
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Student stu = new Student();
				stu.setRoll(rs.getString("roll"));
				stu.setName(rs.getString("name"));
				stu.setMya(rs.getInt("mya"));
				stu.setEng(rs.getInt("eng"));
				stu.setMath(rs.getInt("math"));
				stu.setChe(rs.getInt("che"));
				stu.setPhy(rs.getInt("phy"));
				stu.setBio(rs.getInt("bio"));
				stu.setTotal(rs.getInt("total"));
				stu.setResult(rs.getString("result"));

				list.add(stu);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Student> findByName (String text) {
		List<Student> list = new ArrayList<Student>();
		String sql = "select * from student where name like ?";
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
				stmt.setString(1, text.concat("%"));
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					Student	stu	= new Student();
					stu.setName(rs.getString("name"));
					stu.setRoll(rs.getString("roll"));
					stu.setMya(rs.getInt("mya"));
					stu.setEng(rs.getInt("eng"));
					stu.setMath(rs.getInt("math"));
					stu.setChe(rs.getInt("che"));
					stu.setPhy(rs.getInt("phy"));
					stu.setBio(rs.getInt("bio"));
					stu.setResult(rs.getString("result"));
					stu.setTotal(rs.getInt("total"));
					list.add(stu);
					
				}
				return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
		
	}

	public List<Student> findByRoll(String text) {
		List<Student> list = new ArrayList<Student>();
		String sql = "select * from student where roll like ?";
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
				stmt.setString(1, text.concat("%"));
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					Student	stu	= new Student();
					stu.setName(rs.getString("name"));
					stu.setRoll(rs.getString("roll"));
					stu.setMya(rs.getInt("mya"));
					stu.setEng(rs.getInt("eng"));
					stu.setMath(rs.getInt("math"));
					stu.setChe(rs.getInt("che"));
					stu.setPhy(rs.getInt("phy"));
					stu.setBio(rs.getInt("bio"));
					stu.setResult(rs.getString("result"));
					stu.setTotal(rs.getInt("total"));
					list.add(stu);
					
				}
				return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	

}
