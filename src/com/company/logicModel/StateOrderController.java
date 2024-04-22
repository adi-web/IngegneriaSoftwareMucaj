package com.company.logicModel;

import com.company.daoModel.OrderDao;
import com.company.domainModel.Item;
import com.company.domainModel.Order;
import com.company.domainModel.state.Delivered;
import com.company.domainModel.state.Delivering;
import com.company.domainModel.state.Ordered;
import com.company.domainModel.state.Ready;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Timer;




public class StateOrderController {

    private OrderController orderController;
    private OrderDao orderDao;


    public StateOrderController(OrderController orderController, OrderDao orderDao) {
        this.orderController = orderController;
        this.orderDao = orderDao;
    }


    // per inserire un nuovo item devo vedere prima che non abbia pagato
    public void addNewItem(int idOrder, List<Item> items) throws SQLException, ClassNotFoundException {
        Order o=orderController.getOrderById(idOrder);
        //mi controlla se l'ordine esiste altrimenti faccio tronare una exception
        if ( o == null) throw new IllegalArgumentException("This order do not exist in databse");

        if(Objects.equals(o.getState().getState(),"ordered")&&Objects.equals(o.getPaymentStrategy(),"No payed")) // se l'ordine è solo ordinato si puo eliminare
        {

            orderDao.insertOrderItem(idOrder,items);
            orderDao.changePriceToPay(idOrder,items);

        }
        else throw new IllegalArgumentException("This order can not be deleted");

    }



    public void takeAndDeliver(int idRider,int idOrder) throws SQLException, ClassNotFoundException {

        Order o=orderController.getOrderById(idOrder);
        //controlla se l'ordine esiste altrimenti faccio tronare una exception
        if ( o == null) throw new IllegalArgumentException("This order do not exist in databse");
        if(Objects.equals(o.getState().getState(),"ready")&&Objects.equals(o.getIdR(),0)) //il rider puo prendere l'ordine solo se è pronto
        {
            orderDao.updateRiderDeparture(idRider,idOrder);
            Delivering delivering=new Delivering();
            orderDao.changeState(idOrder,delivering);
        }
        else throw new IllegalArgumentException("You can not deliver this order yet");

    }



    public void orderDelivered(int idOrder) throws SQLException, ClassNotFoundException {

        Order o=orderController.getOrderById(idOrder);
        //mi controlla se l'ordine esiste altrimenti faccio tronare una exception
        if ( o == null) throw new IllegalArgumentException("This order do not exist in databse");

        if(!Objects.equals(o.getState().getState(),"delivering")) throw new IllegalArgumentException("You have to take the other first");


        orderDao.updateDeliver(idOrder);
        Delivered delivered=new Delivered();
        orderDao.changeState(idOrder,delivered);

    }

    public void makeReady(int idOrder) throws SQLException, ClassNotFoundException {

        Order o=orderController.getOrderById(idOrder);
        if ( o == null) throw new IllegalArgumentException("This order do not exist in database");
        if(!Objects.equals(o.getState().getState(),"ordered")) throw new IllegalArgumentException("You have to take the other first");
        {
            Ready ready=new Ready();
            orderDao.changeState(idOrder,ready);
        }
    }

    public void placeOrder(int idOrder) throws SQLException, ClassNotFoundException {
        Order o=orderController.getOrderById(idOrder);
        if ( o == null) throw new IllegalArgumentException("This order do not exist in database");
        System.out.println(Time.valueOf(LocalTime.now().plusMinutes(10)).getTime());

        Time t=Time.valueOf(LocalTime.now());

        if(Objects.equals(o.getState().getState(),"saved"))
        {
            Ordered ordered=new Ordered();
            orderDao.changeState(idOrder,ordered);
        }

    }





}
