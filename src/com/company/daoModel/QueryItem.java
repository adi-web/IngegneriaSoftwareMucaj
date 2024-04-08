package com.company.daoModel;

import com.company.domainModel.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryItem implements ItemDao {
    @Override
    public void insert(Item newInsert) throws ClassNotFoundException, SQLException {
        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("INSERT INTO item(idItem,name,price)VALUES(?,?,?)");
        ps.setInt(1,newInsert.getId());
        ps.setString(2,newInsert.getName());
        ps.setFloat(3,newInsert.getPrice());

        ps.executeUpdate();
        ps.close();
        Database.closeConnection(con);
    }

    @Override
    public void delete(int id) {

    }



    @Override
    public Item get(int id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
