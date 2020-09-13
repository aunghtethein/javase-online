package com.solt.jdc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.solt.jdc.entity.Category;
import com.solt.jdc.util.DatabaseManager;

public class CategoryService {

	public int addCategory(Category category) {
		String sql = "insert into category (categoryName) values (?)";
		int rst = 0;
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
				stmt.setString(1, category.getCategoryName());
				rst = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rst;
	}

	public List<Category> findAll() {
		String sql = "select * from category";
		List<Category> list = new ArrayList<Category>();
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
				ResultSet rs = stmt.executeQuery();
				
				while(rs.next()) {
					Category cat = new Category();
					cat.setId(rs.getInt("id"));
					cat.setCategoryName(rs.getString("categoryName"));
					list.add(cat);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Category> findByName(String text) {
		String sql = "select * from category where categoryName like ?";
		
		List<Category > list  = new ArrayList<Category>();
		
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
				stmt.setString(1, text.concat("%"));
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
				
					Category cat = new Category();
					cat.setId(rs.getInt("id"));
					cat.setCategoryName(rs.getString("categoryName"));
					list.add(cat);
				}
			
				return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int update(Category category) {
		int num = 0;
		String sql = "update category set categoryName = ? where id = ?";
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
				stmt.setString(1, category.getCategoryName());
				stmt.setInt(2, category.getId());
				num = stmt.executeUpdate();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	public Category findByid(int id) {
		String sql = "select * from category where id = ?";
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Category category =new Category();
				category.setId(rs.getInt("id"));
				category.setCategoryName(rs.getString("categoryName"));
				return category;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
