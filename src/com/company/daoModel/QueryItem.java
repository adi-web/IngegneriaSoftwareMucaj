package com.company.daoModel;

import com.company.domainModel.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryItem implements ItemDao {
    @Override
    public void insert(Item newInsert) throws ClassNotFoundException, SQLException {
        if(!searchItem(newInsert)) {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO item(idItem,name,price)VALUES(?,?,?)");
            ps.setInt(1, newInsert.getId());
            ps.setString(2, newInsert.getName());
            ps.setFloat(3, newInsert.getPrice());

            ps.executeUpdate();
            ps.close();
            Database.closeConnection(con);
        }
        else throw new IllegalArgumentException("This item exist");
    }

    @Override
    public void delete(int id) {

    }

    public boolean searchItem(Item item) throws SQLException, ClassNotFoundException {
        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT * from item where name=?");
        ps.setString(1,item.getName());
       ResultSet rs= ps.executeQuery();
       if(rs.next())
       {
           return  true;
       }

       return false;

    }

    @Override
    public Item get(int id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
