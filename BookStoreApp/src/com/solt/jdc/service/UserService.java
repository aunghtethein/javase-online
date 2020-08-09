package com.solt.jdc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.solt.jdc.entity.User;
import com.solt.jdc.util.DatabaseManager;

public class UserService {

	public User findById(String loginId) {
		String sql = "select * from user where loginId = ?";
		try  (Connection con = DatabaseManager.getconConnection();
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

}
