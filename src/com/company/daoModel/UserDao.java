package com.company.daoModel;

import com.company.domainModel.Customer;
import com.company.domainModel.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {

/*
    public void insert(String name,String surname,String phone,int address,int role) throws ClassNotFoundException, SQLException
    {
        if(getIdUser(phone)==0) {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO user(name,surname,phone,address,role)VALUES(?,?,?,?,?)");
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, phone);
            ps.setInt(4, address);
            ps.setInt(5, role);

            ps.executeUpdate();
            ps.close();
            Database.closeConnection(con);
        }
    }


    public int getIdUser(String number) throws SQLException, ClassNotFoundException {
        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("select idUser from user where phone=?");
        ps.setString(1,number);
        ResultSet a=ps.executeQuery();
        int id=0;

        if (a.next())
        {
            id=a.getInt("idUser");
        }
        ps.close();
        Database.closeConnection(con);
        return id;
    }

    public ArrayList getUser(int id) throws SQLException, ClassNotFoundException {
        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("select * from user where idUser=?");
        ps.setInt(1,id);
        ResultSet rs=ps.executeQuery();
      //  Customer user = new Customer();
        ArrayList<String> arrayList=new ArrayList<>();
        if(rs.next())
        {
            arrayList.add(rs.getString("name"));
            arrayList.add(rs.getString("surname"));
            arrayList.add(rs.getString("phone"));
            arrayList.add(String.valueOf(rs.getInt("address")));

        }

        ps.close();
        rs.close();
        Database.closeConnection(con);

        return arrayList;

    }

    public  void updateUserPhone(int idUser,String c) throws SQLException, ClassNotFoundException {
        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("update user set phone=? where idUser=?");
        ps.setString(1,c);
        ps.setInt(2,idUser);
        ps.executeUpdate();
        ps.close();
        Database.closeConnection(con);

    }

   /* public boolean checkUser(String numberPhone) throws SQLException, ClassNotFoundException {

        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("select * from user where phone=?");
        ps.setString(1,numberPhone);
        ResultSet rs=ps.executeQuery();
        Boolean e=false;

        if(rs.next())
        {
            e=true;
        }

        ps.close();
        rs.close();
        Database.closeConnection(con);

        return e;

    }*/


}
