package com.company.logicModel;

import com.company.daoModel.OrderDao;
import com.company.domainModel.Customer;
import com.company.domainModel.Order;
import com.company.domainModel.Rider;
import com.company.domainModel.payment.PaymentStrategy;

import java.sql.SQLException;

public class OrderController {
    OrderDao orderDao;

    public OrderController(OrderDao orderDao)
    {
        this.orderDao=orderDao;
    }

    public void addOrder(Order order) throws SQLException, ClassNotFoundException {
        orderDao.insert(order);
        //orderDao.insertCustomer(customer,order.getId());// a questo ordine inserisco questo cliente


    }

    //metodo che serve per inserire il rider che prende in carico la conosegna o
    public void assignmentRider(int idRider,Order o) throws SQLException, ClassNotFoundException {
       // orderDao.insertRider(idRider,o);
    }

    public Order getOrderById(int idOrder) throws SQLException, ClassNotFoundException {
      return orderDao.get(idOrder);

    }

    public void pay(int idOrder, PaymentStrategy paymentStrategy) throws SQLException, ClassNotFoundException {

        paymentStrategy.pay();// chiamo il metodo pay in base a quello che è stato scelto
        orderDao.updatePayment(idOrder,paymentStrategy);
    }


}
