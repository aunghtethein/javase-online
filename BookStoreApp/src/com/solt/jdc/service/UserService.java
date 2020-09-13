package com.solt.jdc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.solt.jdc.entity.User;
import com.solt.jdc.util.DatabaseManager;

public class UserService {

	public User findById(String loginId) {
		String sql = "select * from user where loginId = ?";
		try  (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, loginId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next() ) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setAddress(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setLogin_id(rs.getString("loginId"));
				user.setEmail(rs.getString("email"));
				return user;
			}
			
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int addUser(User user) {
		int num = 0;
		String sql = "insert into user (loginId,name,phone,email,address,password)"
				+ " values (?,?,?,?,?,?)";
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, user.getLogin_id());
			stmt.setString(2, user.getName());
			stmt.setString(3, user.getPhone());
			stmt.setString(4, user.getEmail());
			stmt.setString(5, user.getAddress());
			stmt.setString(6, user.getPassword());
			
			num = stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	public int update(User user) {
		int num = 0;
		String sql = "update user set name = ?,password = ?, address = ?, phone = ?, loginId = ?,email = ? where id = ? ";
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
				
				stmt.setString(1, user.getName());
				stmt.setString(2, user.getPassword());
				stmt.setString(3, user.getAddress());
				stmt.setString(4, user.getPhone());
				stmt.setString(5, user.getLogin_id());
				stmt.setString(6, user.getEmail());
				stmt.setInt(7, user.getId());
				num = stmt.executeUpdate();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
		
	

	public List<User> findAll() {
		String sql = "select * from user";
		List<User >list = new ArrayList<User>();
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
				ResultSet rs = stmt.executeQuery();
				
				while(rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					user.setAddress(rs.getString("address"));
					user.setPhone(rs.getString("phone"));
					user.setLogin_id(rs.getString("loginId"));
					user.setEmail(rs.getString("email"));
					list.add(user);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<User> findByName(String sch) {
		String sql = "select * from user where name like ?";
				
				List<User > list  = new ArrayList<User>();
				
				try (Connection con = DatabaseManager.getConnection();
						PreparedStatement stmt = con.prepareStatement(sql)){
						stmt.setString(1, sch.concat("%"));
						ResultSet rs = stmt.executeQuery();
						while(rs.next()) {
						
							User user = new User();
							user.setId(rs.getInt("id"));
							user.setName(rs.getString("name"));
							user.setPassword(rs.getString("password"));
							user.setAddress(rs.getString("address"));
							user.setPhone(rs.getString("phone"));
							user.setLogin_id(rs.getString("loginId"));
							user.setEmail(rs.getString("email"));
							list.add(user);
						}
						
				} catch (Exception e) {
					e.printStackTrace();
				}
		return list;
	}
	
	public int delete(User user) {
		int rst = 0;
		String sql = "delete from user where id = ?";
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, user.getId());
			stmt.executeUpdate();
			
		}catch (Exception e) {
				e.printStackTrace();
			}
		return rst;
			
	}

}
