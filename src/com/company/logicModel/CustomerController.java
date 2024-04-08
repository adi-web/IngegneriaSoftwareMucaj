package com.company.logicModel;

import com.company.daoModel.CustomerDao;
import com.company.domainModel.Address;
import com.company.domainModel.Customer;
import com.company.domainModel.User;

import java.sql.SQLException;

public class CustomerController extends GeneralController<Customer>  {
    //CustomerDao customerDao;
    public CustomerController(CustomerDao customerDao)
    {
        super(customerDao);

    }

    public void addCustomer(int id,String name, String surname, String phone,int idAddress) throws SQLException, ClassNotFoundException {
        Customer customer=new Customer(id,name,surname,phone,idAddress);
        super.insert(customer);
    }

    public Customer infoCustomer(int id) throws SQLException, ClassNotFoundException {
        return super.get(id);
    }



}
