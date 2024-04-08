package com.company.test.logicModel;

import com.company.daoModel.Database;
import com.company.daoModel.QueryOrderDao;
import com.company.daoModel.QueryRiderDao;
import com.company.daoModel.RiderDao;
import com.company.domainModel.Order;
import com.company.domainModel.Rider;
import com.company.domainModel.state.Delivering;
import com.company.domainModel.state.Ready;
import com.company.logicModel.OrderController;
import com.company.logicModel.RiderController;
import com.company.logicModel.StateOrderController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class StateOrderControllerTest {
    private QueryOrderDao queryOrderDao= new QueryOrderDao();
    private QueryRiderDao queryRiderDao;
    {
        try {
            queryRiderDao = new QueryRiderDao();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private RiderController riderController=new RiderController(queryRiderDao);
    private Connection con;
    OrderController orderController=new OrderController(queryOrderDao);
    private StateOrderController stateOrderController= new StateOrderController(orderController,riderController,queryOrderDao);

    @BeforeEach
    void init() throws SQLException, ClassNotFoundException {
        con= Database.getConnection();
        queryOrderDao=new QueryOrderDao();
        con.prepareStatement("DELETE FROM myorder").executeUpdate();
        con.prepareStatement("DELETE FROM rider").executeUpdate();

        java.util.Date utilDate = new java.util.Date();
        Timestamp sqlDate = new Timestamp(utilDate.getTime());

        con.prepareStatement("INSERT INTO myorder ( idOrder,stateOrder,departure,delivered,timeTodeliver,dateOrder,orderRider,customerOrder,noteOrder,payment) VALUES (1,'ordered',null,null,null,null,null,9,null,'paymentcard')").executeUpdate();

        con.prepareStatement("INSERT INTO orderitem ( order_id,item_id,quantity) VALUES (1,1,10)").executeUpdate();
        con.prepareStatement("INSERT INTO orderitem ( order_id,item_id,quantity) VALUES (1,2,3)").executeUpdate();
        con.prepareStatement("INSERT INTO rider ( idRider,plateScooter,startWork,user) VALUES (1,'da645',current_date(),2)").executeUpdate();
    }




    
    // test che permette di verificare il metodo delete in base
    @Test
    void testDeleteOrderState() throws SQLException, ClassNotFoundException {
        stateOrderController.deleteOrder(1);
        Assertions.assertNull(queryOrderDao.get(1));

        con.prepareStatement("INSERT INTO myorder ( idOrder,stateOrder,departure,delivered,timeTodeliver,dateOrder,orderRider,customerOrder,noteOrder,payment) VALUES (1,'delivered',null,null,null,null,null,9,null,'paymentcard')").executeUpdate();


        // test che controlla l'eccezione in delete perche l'ordine è in uno stato che non può essere cancellato
        Exception exception = assertThrows(Exception.class, () -> stateOrderController.deleteOrder(1));
        Assertions.assertEquals("This order can not be deleted", exception.getMessage());


    }

    @Test
    void testTakeAndDeliver() throws SQLException, ClassNotFoundException {

        //l'ordine è ordinato ma non ancora presso in carica quindi il rider non può digitarlo per fare la consegna
        Exception exception = assertThrows(Exception.class, () ->  stateOrderController.takeAndDeliver(1,1));
        Assertions.assertEquals("You can not deliver this order yet", exception.getMessage());

        Order o=queryOrderDao.get(1);
        Ready ready=new Ready();
        queryOrderDao.changeState(1,ready);
        stateOrderController.takeAndDeliver(1,1);
        o=queryOrderDao.get(1);
        Assertions.assertEquals("delivering ",o.getState().getState());


    }

    @Test
    void testOrderDelivered() throws SQLException, ClassNotFoundException {


        Exception exception = assertThrows(Exception.class, () ->   stateOrderController.orderDelivered(1));
        Assertions.assertEquals("You have to take the other first", exception.getMessage());

        Order o=queryOrderDao.get(1);

        //test che cambia stato dell'ordine quando viene consegnato
        Delivering delivering=new Delivering();
        queryOrderDao.changeState(1,delivering);
        stateOrderController.orderDelivered(1);
        o=queryOrderDao.get(1);
        Assertions.assertEquals("delivered",o.getState().getState());
    }


}
