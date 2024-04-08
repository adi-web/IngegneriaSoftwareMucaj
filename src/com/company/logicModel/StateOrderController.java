package com.company.logicModel;

import com.company.daoModel.OrderDao;
import com.company.domainModel.Order;
import com.company.domainModel.state.Delivered;
import com.company.domainModel.state.Delivering;
import com.company.domainModel.state.Ready;

import java.sql.SQLException;
import java.util.Objects;

//cliente cerca i eliminare un ordine e dobbiamo vedere dal db se è in preparazione o meno e dire poi se si puo eliminare
// cliente puo modificare un oridine dobbiamo vedere se è preso in carica l'ordine o meno se è in consegna non puo farlo
//rider prima di prendere oridine e digitare controlla se esiste l'ordine poi cambia lo stato in consegna
//ridert cambia lo stato dell'ordine qunado viene consegnato
public class StateOrderController {

    private OrderController orderController;
    private OrderDao orderDao;


    public StateOrderController(OrderController orderController, RiderController riderController, OrderDao orderDao) {
        this.orderController = orderController;
        this.orderDao = orderDao;
    }

    //il cliente elimina un suo ordine questo mettodo deve essere fato nel orderDao non qua
    public void deleteOrder(int idOrder) throws SQLException, ClassNotFoundException {
        //Order o=orderDao.get(idOrder);
        Order o=orderController.getOrderById(idOrder);
        //mi controlla se l'ordine esiste altrimenti faccio tronare una exception
        if ( o == null) throw new IllegalArgumentException("This order do not exist in databse");

        if(Objects.equals(o.getState().getState(),"ordered")) // se l'ordine è solo ordinato si puo eliminare
        {
           // Ready cancelled=new Ready();
            //orderDao.changeState(idOrder,cancelled);
            orderDao.delete(idOrder); // mi permette di eliminare ordine da orderitem e myorder

        }
        else throw new IllegalArgumentException("This order can not be deleted");


    }


/*
    public void modifyQuantity(int idOrder,int idItem,int quantity) throws SQLException, ClassNotFoundException {
        Order o=orderDao.get(idOrder);
        //mi controlla se l'ordine esiste altrimenti faccio tronare una exception
        if ( o == null) throw new IllegalArgumentException("This order do not exist in databse");
        if(Objects.equals(o.getState().getState(),"ordered")||Objects.equals(o.getState().getState(),"preparation"))
        {
            orderDao.change(idOrder,idItem,quantity);
        }

    }*/

    public void takeAndDeliver(int idRider,int idOrder) throws SQLException, ClassNotFoundException {


        Order o=orderController.getOrderById(idOrder);
        //mi controlla se l'ordine esiste altrimenti faccio tronare una exception
        if ( o == null) throw new IllegalArgumentException("This order do not exist in databse");
        if(Objects.equals(o.getState().getState(),"ready")) //il rider puo prendere l'ordine solo se è pronto
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
        if ( o == null) throw new IllegalArgumentException("This order do not exist in databse");
        if(Objects.equals(o.getState().getState(),"ordered")) throw new IllegalArgumentException("You have to take the other first");
        {
            Ready ready=new Ready();
            orderDao.changeState(idOrder,ready);
        }
    }





}
