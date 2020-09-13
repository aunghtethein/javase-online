package com.solt.jdc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.solt.jdc.entity.Author;
import com.solt.jdc.util.DatabaseManager;

public class AuthorService {

	public int addAuthor(Author author) {
		String sql = "insert into author (authorName) values (?)";
		int rst = 0;
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
				stmt.setString(1, author.getAuthorName());
				rst = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rst;
	}

	public List<Author> findAll() {
		String sql = "select * from author";
		List<Author> list = new ArrayList<Author>();
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
				ResultSet rs = stmt.executeQuery();
				
				while(rs.next()) {
					Author author = new Author();
					author.setId(rs.getInt("id"));
					author.setAuthorName(rs.getString("authorName"));
					list.add(author);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Author> findByName(String text) {
		String sql = "select * from author where authorName like ?";
		
		List<Author > list  = new ArrayList<Author>();
		
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
				stmt.setString(1, text.concat("%"));
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
				
					Author author = new Author();
					author.setId(rs.getInt("id"));
					author.setAuthorName(rs.getString("authorName"));
					list.add(author);
				}
			
				return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int update(Author author) {
		int num = 0;
		String sql = "update author set authorName = ? where id = ?";
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
				stmt.setString(1, author.getAuthorName());
				stmt.setInt(2, author.getId());
				num = stmt.executeUpdate();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	public Author findById(int id) {
		String sql = "select * from author where id = ?";
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Author author = new Author();
				author.setId(rs.getInt("id"));
				author.setAuthorName(rs.getString("authorName"));
			
				return author;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
