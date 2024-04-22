package com.company.daoModel;

import com.company.domainModel.Customer;
import com.company.domainModel.Rider;

import java.sql.*;
import java.util.ArrayList;

public class QueryRiderDao implements RiderDao {


    public QueryRiderDao() throws SQLException, ClassNotFoundException {}

    @Override
    public void changePlate(int id, String plate) { }

    @Override
    public void deletePlate(int id) {}

    @Override
    public void insert(Rider newInsert) throws ClassNotFoundException, SQLException
    {

            Connection con=Database.getConnection();
            PreparedStatement userInsert=con.prepareStatement("INSERT INTO user(name,surname,phone,address,role) VALUES (?,?,?,?,2)");

            userInsert.setString(1,newInsert.getName());
            userInsert.setString(2,newInsert.getSurname());
            userInsert.setString(3,newInsert.getPhone());
            userInsert.setInt(4,newInsert.getAddress());
            userInsert.executeUpdate();
            userInsert.close();

            int userId=getLastId(newInsert.getPhone());
            PreparedStatement ps=con.prepareStatement("INSERT INTO rider(idRider,plateScooter,startWork,user) VALUES (?,?,?,?)");
            ps.setInt(1,newInsert.getId());
            ps.setString(2,newInsert.getPlateScooter());
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            ps.setDate (3, sqlDate);
            ps.setInt(4,userId);

            ps.executeUpdate();
            ps.close();
            Database.closeConnection(con);



    }

    public  int getLastId(String phone) throws SQLException, ClassNotFoundException {

        int idU=0;
        Connection con=Database.getConnection();
        PreparedStatement id=con.prepareStatement("SELECT idUser from user where phone=? and role=2");

        id.setString(1,phone);
        ResultSet rs=id.executeQuery();

        if(rs.next())
        {
            idU=rs.getInt("idUser");
        }

        id.close();
        rs.close();
        Database.closeConnection(con);

        return  idU;
    }


    @Override
    public void delete(int id) {}



    @Override
    public Rider get(int id) throws SQLException, ClassNotFoundException
    {



        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("select  * from rider join user on rider.user=user.idUser where idRider=?");
        ps.setInt(1,id);
        ResultSet rs=ps.executeQuery();
        Rider rider=null;
        if(rs.next()) {
            rider = new Rider();
            rider.setName(rs.getString("name"));
            rider.setSurname(rs.getString("surname"));
            rider.setPhone(rs.getString("phone"));
            rider.setPlateScooter(rs.getString("plateScooter"));
        }



        return rider;
    }
}
