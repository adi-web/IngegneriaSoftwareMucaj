package com.company.test.dao;

import com.company.daoModel.Database;
import com.company.daoModel.QueryCustomerDao;
import com.company.daoModel.QueryOrderDao;
import com.company.domainModel.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

public class QueryCustomerDaoTest {

    private QueryCustomerDao  customerDao;
    Connection con;
    @BeforeEach
    void conDatabse() throws SQLException, ClassNotFoundException {
        con= Database.getConnection();
        customerDao=new QueryCustomerDao();

        con.prepareStatement("DELETE FROM customer").executeUpdate();
        con.prepareStatement("DELETE FROM user").executeUpdate();


        con.prepareStatement("INSERT INTO user (idUser,name,surname,phone,address,role) VALUES (1,'andrea','petrucci','345680239',1,1)").executeUpdate();
        con.prepareStatement("INSERT INTO user (idUser,name,surname,phone,address,role) VALUES (2,'pietro','bonechi','3457890378',2,1)").executeUpdate();

        con.prepareStatement("INSERT INTO customer (idCustomer,numberOrder,user,discount) VALUES (9,5,1,false )").executeUpdate();
        con.prepareStatement("INSERT INTO customer (idCustomer,numberOrder,user,discount) VALUES (10,3,2,false)").executeUpdate();




    }
    @AfterEach
    void close() throws SQLException, ClassNotFoundException {
        con=Database.getConnection();
        customerDao=new QueryCustomerDao();;
        //con.prepareStatement("DELETE FROM orderitem").executeUpdate();
        con.prepareStatement("DELETE FROM myorder").executeUpdate();

        con.prepareStatement("DELETE FROM customer").executeUpdate();
        con.prepareStatement("DELETE FROM user").executeUpdate();

        Database.closeConnection(con);
    }

    @Test
    void testInsert() throws SQLException, ClassNotFoundException {
        Customer customer=new Customer(29,"admir","mucaj","34597793434",1);
        customerDao.insert(customer);

        Customer c=customerDao.get(29);
        Assertions.assertNotNull(c);
        Assertions.assertEquals(29,c.getId());
    }

    @Test
    void testDelete() throws SQLException, ClassNotFoundException {

        customerDao.delete(9);
        Customer c=customerDao.get(9);
        Assertions.assertNull(c);
    }
    @Test
    void testChangePhone() throws SQLException, ClassNotFoundException{
        customerDao.changePhone(9,"12345678");
        Customer c=customerDao.get(9);
        Assertions.assertEquals(c.getPhone(),"12345678");

    }

    @Test
    void testChangeAddress() throws SQLException, ClassNotFoundException {
        customerDao.changeAddress(9,2);
        Customer c=customerDao.get(9);
       Assertions.assertEquals(c.getIdAdrress(),2);
    }
}
