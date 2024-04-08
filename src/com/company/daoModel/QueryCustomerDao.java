package com.company.daoModel;

import com.company.domainModel.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;

public class QueryCustomerDao implements CustomerDao {


    @Override
    public void changeAddress(int idCustomer,int idAddress) throws SQLException, ClassNotFoundException {

            Connection con=Database.getConnection();
            PreparedStatement ps=con.prepareStatement( "UPDATE user JOIN customer ON user.idUser =customer.user SET user.address =?  WHERE customer.idCustomer=?");
            ps.setInt(1,idAddress);
            ps.setInt(2,idCustomer);
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(con);
    }


    @Override
    public void insert(Customer newInsert) throws ClassNotFoundException, SQLException {



        Customer c=newInsert;
        Connection con = Database.getConnection();
        PreparedStatement ps=con.prepareStatement("INSERT INTO user(idUser,name,surname,phone,address,role) VALUES (?,?,?,?,?,?)"); //VALUES (1,c.name,c.surname,12323,1,1)");
        ps.setInt(1,newInsert.getId());
        ps.setString(2,c.getName());
        ps.setString(3,c.getSurname());
        ps.setString(4,c.getPhone());
        ps.setInt(5,c.getIdAdrress());
        ps.setInt(6,1);
        // prima di inserire controllare quanti ordini ha fatto il cliente e mettere dicount a false o meno creare il metodo qua
        ps.executeUpdate();
        System.out.println("inserito");
        ps.close();
        Database.closeConnection(con);
        //per inserirlo anche nella tabella customer essendo che c'e una fk
        insertCustomerId(newInsert.getId());



    }

    //inserisce nella tabella custome l'id dell'user
    @Override
    public void insertCustomerId(int idUser) throws SQLException, ClassNotFoundException {
        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("INSERT INTO customer(user)VALUES(?)");
        ps.setInt(1,idUser);
        ps.executeUpdate();
        ps.close();

    }

    @Override
    public void delete(int id) throws SQLException, ClassNotFoundException {

        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("delete from customer where idCustomer=?");
        ps.setInt(1,id);
        ps.executeUpdate();
        ps.close();
        Database.closeConnection(con);

    }


    @Override
    public void changePhone(int idC,String newNumber) throws SQLException, ClassNotFoundException {

        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("UPDATE user JOIN customer ON user.idUser =customer.user SET user.phone =?  WHERE customer.idCustomer=?");
        ps.setString(1,newNumber);
        ps.setInt(2,idC);
        ps.executeUpdate();
    }

    @Override
    public Customer get(int id) throws SQLException, ClassNotFoundException {

        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("select  * from customer join user on customer.user=user.idUser where idCustomer=?");
        ps.setInt(1,id);
        ResultSet rs=ps.executeQuery();

        Customer customer=null;
        if(rs.next()) {
            customer = new Customer();
            customer.setDiscount(rs.getBoolean("discount"));
            customer.setTotOrder(rs.getInt("numberOrder"));
            customer.setId(id);
            customer.setName(rs.getString("name"));
            customer.setSurname(rs.getString("surname"));
            customer.setPhone(rs.getString("phone"));
            customer.setIdAdrress(rs.getInt("address"));
        }
       return customer;
    }


    @Override
    public void setDiscount(int idCustomer,Boolean discount) throws SQLException, ClassNotFoundException {
        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("UPDATE customer SET discount=? WHERE idCustomer=?");
        ps.setBoolean(1,discount);
        ps.setInt(2,idCustomer);
        ps.executeUpdate();
        ps.close();
        Database.closeConnection(con);

    }

    @Override
    public  void setNumberOrder(int id) throws SQLException, ClassNotFoundException {
        Customer c=get(id);
        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("UPDATE customer SET numberOrder=? WHERE idCustomer=?");
        ps.setInt(1,c.getTotOrder()+1);
        ps.setInt(2,id);
        ps.executeUpdate();
        ps.close();
        Database.closeConnection(con);
    }

}
