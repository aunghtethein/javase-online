package com.solt.jdc.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.solt.jdc.entity.Invoice;
import com.solt.jdc.util.BookStoreException;
import com.solt.jdc.util.DatabaseManager;

public class InvoiceService {
	public int getMaxInvoiceId() {
		int rst = 0;
		String sql = "select max(id) from invoice";
		try(Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
					ResultSet rs = stmt.executeQuery();
					while(rs.next()) {
						rst = rs.getInt("max(id)");
					}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rst;
	}

    public int add(Invoice inv) {
		int rst = 0;
		String sql = "insert into invoice (subTotal, tax, total, saleDate, user_id) values (?,?,?,?,?)";
   		try(Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
   			stmt.setDouble(1, inv.getSubTotal());
   			stmt.setDouble(2, inv.getTax());
   			stmt.setDouble(3, inv.getTotal());
   			stmt.setDate(4,Date.valueOf(inv.getSaleDate()));
   			stmt.setInt(5, inv.getUser_id());
			 rst = stmt.executeUpdate();

		}catch (Exception e ) {
   			e.printStackTrace();
		}
   		return rst;
    }

	public List<Invoice> findAll() {
		String sql = "select i.id, i.saleDate, i.subTotal, i.tax, i.total, i.User_id, u.id, u.name from invoice i inner join user u on i.User_id = u.id";
		List<Invoice> list = new ArrayList<Invoice>();
		try(Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
   			ResultSet rs = stmt.executeQuery();
   			
   			while(rs.next()) {
   				Invoice inv = new Invoice();
   				inv.setId(rs.getInt("id"));
   				inv.setSaleDate(rs.getDate("i.saleDate").toLocalDate());
   				inv.setSubTotal(rs.getDouble("i.subTotal"));
   				inv.setTax(rs.getDouble("i.tax"));
   				inv.setTotal(rs.getDouble("i.total"));
   				inv.setUser_id(rs.getInt("i.User_id"));
   				inv.setUserName(rs.getString("u.name"));
   				list.add(inv);
   			}
		}catch (Exception e ) {
   			e.printStackTrace();
		}

		return list;
	}

	public List<Invoice> find(LocalDate dateFrom, LocalDate dateTo, String name) {
		if(dateFrom != null && dateTo != null && dateFrom.compareTo(dateTo) > 0 ) {
			throw new BookStoreException("Date From Value should before Date to Vale!" );
		}
		
		String sql = "select i.id, i.saleDate, i.subTotal, i.tax, i.total, i.User_id, u.id, u.name from invoice i inner join user u on i.User_id = u.id";
		StringBuffer sb = new StringBuffer(sql);
		
		List<Invoice> list = new ArrayList<Invoice>();
		List<Object> param = new ArrayList<Object>();
		
		if(dateFrom != null ) {
			sb.append(" and saleDate >= ?");
			param.add(Timestamp.valueOf(dateFrom.atStartOfDay()));
		}
		
		if(dateFrom != null && dateTo == null ) {
			sb.append(" and saleDate <= ?");
			param.add(Timestamp.valueOf(dateFrom.plusDays(1).atStartOfDay()));
		}
		if(dateTo != null ) {
			sb.append(" and saleDate <= ?");
			param.add(Timestamp.valueOf(dateTo.plusDays(1).atStartOfDay()));
		}
		if(name != null && !name.isEmpty()) {
			sb.append(" and u.name like ?");
			param.add(name.concat("%"));
		}
		
		try(Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(new String(sb))){
			for(int i = 0; i<param.size() ; i++) {
				stmt.setObject(i+1, param.get(i));
			}
			ResultSet rs = stmt.executeQuery();
   			
   			while(rs.next()) {
   				Invoice inv = new Invoice();
   				inv.setId(rs.getInt("id"));
   				inv.setSaleDate(rs.getDate("i.saleDate").toLocalDate());
   				inv.setSubTotal(rs.getDouble("i.subTotal"));
   				inv.setTax(rs.getDouble("i.tax"));
   				inv.setTotal(rs.getDouble("i.total"));
   				inv.setUser_id(rs.getInt("i.User_id"));
   				inv.setUserName(rs.getString("u.name"));
   				list.add(inv);
   			}
   		
		}catch (Exception e ) {
   			e.printStackTrace();
		}
		
		return list;
	}
}
