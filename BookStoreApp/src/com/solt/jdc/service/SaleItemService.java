package com.solt.jdc.service;

import com.solt.jdc.entity.SaleItem;
import com.solt.jdc.util.DatabaseManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class SaleItemService {

    public int add(SaleItem item) {
        int rst = 0;
        String sql = "insert into saleItem (count, unit_price, book_id, invoice_id) values (?,?,?,?)";
        try(Connection con = DatabaseManager.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setInt(1, item.getCount());
            stmt.setDouble(2, item.getUnitPrice());
            stmt.setInt(3, item.getBook_id());
            stmt.setInt(4, item.getInvoice_id());
            rst  = stmt.executeUpdate();
            return rst;

        }catch (Exception e ) {
            e.printStackTrace();
        }
        return rst;
    }
}
