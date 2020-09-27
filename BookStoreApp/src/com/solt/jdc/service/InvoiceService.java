package com.solt.jdc.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.solt.jdc.entity.Invoice;
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
}
