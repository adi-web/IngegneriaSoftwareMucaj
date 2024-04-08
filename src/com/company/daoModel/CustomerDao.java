package com.company.daoModel;

import com.company.domainModel.Address;
import com.company.domainModel.Customer;

import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerDao extends DaoGeneral<Customer>
{
    void changeAddress(int idC,int idA) throws SQLException, ClassNotFoundException;

    void insertCustomerId(int idUser) throws SQLException, ClassNotFoundException;

    void changePhone(int idC,String newNumber) throws SQLException, ClassNotFoundException;

    void setDiscount(int idC,Boolean discount) throws SQLException, ClassNotFoundException;

    void setNumberOrder(int id) throws SQLException, ClassNotFoundException;


}
