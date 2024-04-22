package com.company.daoModel;

import com.company.domainModel.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class QueryAddressDao implements AddressDao {


    @Override
    public void insert(Address address) throws ClassNotFoundException, SQLException {


        if(!exist(address)) {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO address(idAddress,nameAddress,numberAddress,city) VALUES (?,?,?,?)"); //VALUES (1,address.name,address.city,");
            ps.setInt(1, address.getId());
            ps.setString(2, address.name);
            ps.setInt(3, address.number);
            ps.setInt(4, address.city);
            ps.executeUpdate();
            //System.out.println("inserito");
            ps.close();
            Database.closeConnection(con);
        }
        else throw new IllegalArgumentException("This address exist");

    }

    public boolean exist(Address address) throws SQLException, ClassNotFoundException {
        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT * FROM address where nameAddress=? and city=? and numberAddress=?");
        ps.setString(1,address.name);
        ps.setInt(2, address.number);
        ps.setInt(3, address.city);
        ResultSet rs=ps.executeQuery();

        if(rs.next())
        {
            return true; // vuol dire che esiste gia questo indirizzo
        }

        return false;



    }

    //funziona
    @Override
    public void delete(int id) throws SQLException, ClassNotFoundException {

        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("delete from address where idAddress=?");
        ps.setInt(1,id);
        ps.executeUpdate();
        ps.close();
        Database.closeConnection(con);

    }


    //funziona
    @Override
    public Address get(int id) throws SQLException, ClassNotFoundException {
        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("select * from address where idAddress=?");

        ps.setInt(1,id);
        ResultSet a=ps.executeQuery();

        Address address=null;
        if (a.next())
        {
            String name=a.getString("nameAddress");
            int number=a.getInt("numberAddress");
            int city=a.getInt("city");
             address=new Address(a.getInt("idAddress"),name,number,city);
        }
        ps.close();
        Database.closeConnection(con);

        return address;
    }

    //non funziona con il max funziona
    public int getLastid() throws SQLException, ClassNotFoundException {


        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("select max(idAddress) from address ");
        ResultSet a=ps.executeQuery();
        int idAddress=0;
        if(a.next())
        idAddress=a.getInt("idAddress");
        ps.close();
        a.close();
        Database.closeConnection(con);
        return idAddress;

    }

    public int getId(Address address) throws SQLException, ClassNotFoundException {
        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("select idAddress from address where nameAddress=? and numberAddress=? and city=?");
        ps.setString(1,address.name);
        ps.setInt(2,address.number);
        ps.setInt(3,address.city);
        ResultSet a=ps.executeQuery();
        int idAddress=0;
        while (a.next())
        {
            idAddress=a.getInt("idAddress");
        }

        ps.close();
        Database.closeConnection(con);

        return idAddress;
    }
}
