package com.company.logicModel;

import com.company.daoModel.OrderDao;
import com.company.domainModel.Customer;
import com.company.domainModel.Item;
import com.company.domainModel.Order;
import com.company.domainModel.Rider;
import com.company.domainModel.payment.PaymentStrategy;
import com.company.domainModel.state.State;

import java.sql.SQLException;
import java.util.List;

public class OrderController {
    OrderDao orderDao;

    public OrderController(OrderDao orderDao)
    {
        this.orderDao=orderDao;
    }

    public void addOrder(Order order) throws SQLException, ClassNotFoundException {
        orderDao.insert(order);

    }


    public Order getOrderById(int idOrder) throws SQLException, ClassNotFoundException {
      return orderDao.get(idOrder);

    }

    public void pay(int idOrder, PaymentStrategy paymentStrategy) throws SQLException, ClassNotFoundException {

        float amount=orderDao.get(idOrder).getTopay();
        paymentStrategy.pay(amount);// chiamo il metodo pay in base a quello che è stato scelto
        orderDao.updatePayment(idOrder,paymentStrategy);
    }


    public void insertItem(int idOrder, List<Item> items) throws SQLException, ClassNotFoundException {

        if(orderDao.get(idOrder)!=null)
        {
            State state=orderDao.get(idOrder).getState();

            if(state.canUpdate()) {
                orderDao.insertOrderItem(idOrder, items);
            }else throw new IllegalArgumentException("you can not update this order");
        }
        else throw new IllegalArgumentException("This order do not exist");
    }


    public void deleteItem(int idOrder,int idItem) throws SQLException, ClassNotFoundException {
        if(orderDao.get(idOrder)!=null)
        {
            State state=orderDao.get(idOrder).getState();

            if(state.canDelete()) {
                orderDao.deleteOrderItem(idOrder, idItem);
            }else throw new IllegalArgumentException("you can not delete this order");
        }
        else throw new IllegalArgumentException("This order do not existinsertI");
    }


    public void updateItem(int idOrder, int idItem,int quantity) throws SQLException, ClassNotFoundException {
        if(orderDao.get(idOrder)!=null)
        {
            State state=orderDao.get(idOrder).getState();

            if(state.canUpdate()) {
                orderDao.change(idOrder, idItem, quantity);

            }else throw new IllegalArgumentException("you can not update item");
        }
        else throw new IllegalArgumentException("This order do not exist");
    }



}
