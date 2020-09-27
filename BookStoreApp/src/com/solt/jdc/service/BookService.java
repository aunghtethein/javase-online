package com.solt.jdc.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.solt.jdc.entity.Book;
import com.solt.jdc.util.DatabaseManager;
import com.solt.jdc.util.ImageManager;

public class BookService {
	
	public int addBook(Book book) {
		int rst = 0;
		String sql = "insert into book (name,price,issue_date,stock,image,Category_id,Author_id) values (?,?,?,?,?,?,?)";
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
				stmt.setString(1, book.getName());
				stmt.setDouble(2, book.getPrice());
				stmt.setDate(3, Date.valueOf(book.getIssueDate()));
				stmt.setInt(4, book.getStock());
				stmt.setString(5, book.getImage());
				stmt.setInt(6, book.getCategoryId());
				stmt.setInt(7, book.getAuthorId());
				rst = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rst;
	}

	public List<Book> findAll() {
		List<Book> list = new ArrayList<>();
		//String sql = "select * from book, author, category where book.Category_id = category.id and book.Author_id = author.id";
		  String sql = "select b.id, b.name, b.price, b.issue_date, b.stock, "
		  		+ "b.image, b.Category_id, b.Author_id, a.AuthorName, c.CategoryName "
		  		+ "from book b inner join author a on b.Author_id = a.id "
		  		+ "inner join category c on b.Category_id = c.id";
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
		
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					LocalDate ld = null;
					if(rs.getDate("issue_date") != null ) {
						ld = rs.getDate("issue_date").toLocalDate();
					}
					Book book = new Book();
					book.setId(rs.getInt("id"));
					book.setName(rs.getString("name"));
					book.setPrice(rs.getDouble("price"));
					book.setIssueDate(ld);
					book.setStock(rs.getInt("stock"));
					book.setImage(rs.getString("image"));
					book.setCategoryId(rs.getInt("Category_id"));
					book.setAuthorId(rs.getInt("Author_id"));
					
					book.setCategoryName(rs.getString("CategoryName"));
					book.setAuthorName(rs.getString("AuthorName"));
					book.setImageView(ImageManager.getImageView(book.getImage()));
					
					list.add(book);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Book> find(String searchN, String searchA) {
		List<Book > list  = new ArrayList<Book>();
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer("select b.id, b.name, b.price, b.issue_date, b.stock, "
		  		+ "b.image, b.Category_id, b.Author_id, a.AuthorName, c.CategoryName "
		  		+ "from book b inner join author a on b.Author_id = a.id "
		  		+ "inner join category c on b.Category_id = c.id where 1=1");
		if(searchN != null && !searchN.isEmpty()) {
			sb.append(" and b.name like ?");
			param.add(searchN.concat("%"));
		}
		
		if(searchA != null && !searchA.isEmpty()) {
			sb.append(" and a.AuthorName like ?");
			param.add(searchA.concat("%"));
		}
		String sql = new String(sb);
	
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
				for(int i = 0; i<param.size(); i++) {
					stmt.setObject(i+1, param.get(i));
				}
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					LocalDate ld = null;
					if(rs.getDate("issue_date") != null) {
						ld = rs.getDate("issue_date").toLocalDate();
					}
					Book book = new Book();
					book.setId(rs.getInt("id"));
					book.setName(rs.getString("name"));
					book.setPrice(rs.getDouble("price"));
					book.setIssueDate(ld);
					book.setStock(rs.getInt("stock"));
					book.setImage(rs.getString("image"));
					book.setCategoryId(rs.getInt("Category_id"));
					book.setAuthorId(rs.getInt("Author_id"));
					book.setCategoryName(rs.getString("CategoryName"));
					book.setAuthorName(rs.getString("AuthorName"));
					list.add(book);
				}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int update(Book b) {
		int rst = 0;
		
		String sql = "update book set name = ?,price = ?,issue_date = ?,stock = ?,image =?,category_id =?,author_id = ? where id = ?";
		
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
				stmt.setString(1, b.getName());
				stmt.setDouble(2, b.getPrice());
				if(b.getIssueDate() != null) {
					stmt.setDate(3, Date.valueOf(b.getIssueDate()));
				}else {
					stmt.setDate(3, null);
				}
				
				stmt.setInt(4, b.getStock());
				stmt.setString(5, b.getImage());
				stmt.setInt(6, b.getCategoryId());
				stmt.setInt(7, b.getAuthorId());
				stmt.setInt(8, b.getId());
				
				rst = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rst;
	}


    public Book findById(int book_id) {
		String sql = "select * from book where id = ?";
		try (Connection con = DatabaseManager.getConnection();
			 PreparedStatement stmt = con.prepareStatement(sql)){

			stmt.setInt(1, book_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				LocalDate ld = null;
				if(rs.getDate("issue_date") != null ) {
					ld = rs.getDate("issue_date").toLocalDate();
				}
				Book book = new Book();
				book.setId(rs.getInt("id"));
				book.setName(rs.getString("name"));
				book.setPrice(rs.getDouble("price"));
				book.setIssueDate(ld);
				book.setStock(rs.getInt("stock"));
				book.setImage(rs.getString("image"));
				book.setCategoryId(rs.getInt("Category_id"));
				book.setAuthorId(rs.getInt("Author_id"));
				return book;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return  null;
    }
	public List<Book> find(int id, String searchName) {
		List<Book > list  = new ArrayList<Book>();
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer("select b.id, b.name, b.price, b.issue_date, b.stock, "
				+ "b.image, b.Category_id, b.Author_id, a.AuthorName, c.CategoryName "
				+ "from book b inner join author a on b.Author_id = a.id "
				+ "inner join category c on b.Category_id = c.id where 1=1");
		if(id != 0) {
			sb.append(" and b.id =?");
			param.add(id);
		}
		if(searchName != null && !searchName.isEmpty()) {
			sb.append(" and b.name like ?");
			param.add(searchName.concat("%"));
		}

		String sql = new String(sb);

		try (Connection con = DatabaseManager.getConnection();
			 PreparedStatement stmt = con.prepareStatement(sql)){
			for(int i = 0; i<param.size(); i++) {
				stmt.setObject(i+1, param.get(i));
			}
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				LocalDate ld = null;
				if(rs.getDate("issue_date") != null) {
					ld = rs.getDate("issue_date").toLocalDate();
				}
				Book book = new Book();
				book.setId(rs.getInt("id"));
				book.setName(rs.getString("name"));
				book.setPrice(rs.getDouble("price"));
				book.setIssueDate(ld);
				book.setStock(rs.getInt("stock"));
				book.setImage(rs.getString("image"));
				book.setCategoryId(rs.getInt("Category_id"));
				book.setAuthorId(rs.getInt("Author_id"));
				book.setCategoryName(rs.getString("CategoryName"));
				book.setAuthorName(rs.getString("AuthorName"));
				list.add(book);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
