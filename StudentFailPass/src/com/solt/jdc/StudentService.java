package com.solt.jdc;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



public class StudentService {

	public void add(Student student) {
		String sql = "insert into student (name,roll,mya,eng,math,che,phy,bio,result) values (?,?,?,?,?,?,?,?,?)";
		
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
				stu.setResult(rs.getString("result"));

				list.add(stu);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Student> find(String stringRoll, String stringName, String stringResult) {
		List<Student> list = new ArrayList<Student>();
		List<Object> param = new ArrayList<Object>();
		
		StringBuffer sb = new StringBuffer("Select * from student where id > 0 ");
		if(!stringRoll.isEmpty() && stringRoll != null) {
			sb.append("and roll like ? ");
			param.add(stringRoll.concat("%"));
		}
		
		if(!stringName.isEmpty() && stringName != null) {
			sb.append("and name like ? ");
			param.add(stringName.concat("%"));
		}
		
		if(!stringResult.isEmpty() && stringResult != null) {
			if(!stringResult.equals("Pass & Fail")) {
				sb.append("and result like ? ");
				param.add(stringResult.concat("%"));
			}	
		}
		
		String sql = new String(sb);
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
				for(int i = 0; i < param.size(); i++) {
					stmt.setObject(i+1, param.get(i));
				}
				
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
					stu.setResult(rs.getString("result"));

					list.add(stu);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void delete(Student stu) {
		String sql = "delete from student where roll = ?";
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, stu.getRoll());
			stmt.executeUpdate();
			
		}catch (Exception e) {
				e.printStackTrace();
			}
			
	}

	public void edit(Student student) {
		String sql = "update student set name = ?, mya = ?," 
				+ "eng = ?, math = ? ,che = ? ,phy = ?, bio = ?, result = ?" 
				+" where roll = ? ";
	
	try (Connection con = DatabaseManager.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql)) {
		
		stmt.setString(1, student.getName());
		stmt.setInt(2, student.getMya());
		stmt.setInt(3, student.getEng());
		stmt.setInt(4, student.getMath());
		stmt.setInt(5, student.getChe());
		stmt.setInt(6, student.getPhy());
		stmt.setInt(7, student.getBio());
		stmt.setString(8, student.getResult());
		stmt.setString(9, student.getRoll());
		
		stmt.executeUpdate();
		
	} catch (Exception e) {
		e.printStackTrace();
	}

	}
	
	
	
}
