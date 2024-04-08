package com.company.daoModel;

import com.company.domainModel.Customer;
import com.company.domainModel.Item;
import com.company.domainModel.Order;
import com.company.domainModel.payment.PaymentStrategy;
import com.company.domainModel.state.State;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao extends DaoGeneral<Order> {


     //void insertCustomer(Customer customer,int id) throws SQLException, ClassNotFoundException;
     void updateRiderDeparture(int idRider,int idOrder ) throws SQLException, ClassNotFoundException;
     void changeState(int idOrder, State newState) throws SQLException, ClassNotFoundException;
     //int idOrdine(int idCustomer) throws SQLException, ClassNotFoundException;
     void change(int id,int idItem,int quantity) throws SQLException, ClassNotFoundException;
     void updateDeliver(int id) throws SQLException, ClassNotFoundException;
     void updatePayment(int idOrder, PaymentStrategy paymentStrategy) throws SQLException, ClassNotFoundException;
     List<Item> getItems(int idOrder, Connection con) throws SQLException, ClassNotFoundException;

}
