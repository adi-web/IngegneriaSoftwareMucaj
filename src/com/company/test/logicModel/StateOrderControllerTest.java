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
import org.junit.jupiter.api.AfterEach;
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
    
    private Connection con=Database.getConnection();
    OrderController orderController=new OrderController(queryOrderDao);
    private StateOrderController stateOrderController= new StateOrderController(orderController,queryOrderDao);

    public StateOrderControllerTest() throws SQLException, ClassNotFoundException {
    }

    @BeforeEach
    void init() throws SQLException, ClassNotFoundException {

        queryOrderDao=new QueryOrderDao();
        con.prepareStatement("DELETE FROM myorder").executeUpdate();
        con.prepareStatement("DELETE FROM rider").executeUpdate();
        con.prepareStatement("DELETE FROM customer").executeUpdate();
        con.prepareStatement("DELETE FROM user").executeUpdate();
        con.prepareStatement("DELETE FROM address").executeUpdate();
        con.prepareStatement("DELETE FROM Item").executeUpdate();


        con.prepareStatement( "INSERT INTO item(idItem,name,price)VALUES(1,'margherita',6)").executeUpdate();
        con.prepareStatement( "INSERT INTO item(idItem,name,price)VALUES(2,'salamino',7)").executeUpdate();
        con.prepareStatement( "INSERT INTO item(idItem,name,price)VALUES(3,'4 stagioni',8)").executeUpdate();




        java.util.Date utilDate = new java.util.Date();
        Timestamp sqlDate = new Timestamp(utilDate.getTime());

        con.prepareStatement("INSERT INTO address(idAddress,nameAddress,numberAddress,city)VALUES (1,'Via solliccinao ',18,50018)").executeUpdate();
        con.prepareStatement("INSERT INTO address(idAddress,nameAddress,numberAddress,city)VALUES (2,'Via baccio da montelupo ',22,50018)").executeUpdate();

        con.prepareStatement("INSERT INTO user (idUser,name,surname,phone,address,role) VALUES (1,'andrea','petrucci','345680239',1,1)").executeUpdate();
        con.prepareStatement("INSERT INTO user (idUser,name,surname,phone,address,role) VALUES (2,'pietro','bonechi','3457890378',2,1)").executeUpdate();

        con.prepareStatement("INSERT INTO customer (idCustomer,numberOrder,user,discount) VALUES (9,5,1,false )").executeUpdate();
        con.prepareStatement("INSERT INTO customer (idCustomer,numberOrder,user,discount) VALUES (10,3,2,false)").executeUpdate();

        con.prepareStatement("INSERT INTO myorder ( idOrder,stateOrder,departure,delivered,timeTodeliver,dateOrder,orderRider,customerOrder,noteOrder,payment,topay) VALUES (1,'ordered',null,null,current_time,current_date,null,9,'suonare forte','paymentcard',40)").executeUpdate();

        con.prepareStatement("INSERT INTO orderitem ( order_id,item_id,quantity) VALUES (1,1,10)").executeUpdate();
        con.prepareStatement("INSERT INTO orderitem ( order_id,item_id,quantity) VALUES (1,2,3)").executeUpdate();
        con.prepareStatement("INSERT INTO rider ( idRider,plateScooter,startWork,user) VALUES (1,'da645',current_date(),2)").executeUpdate();
    }

    @AfterEach
    void close() throws SQLException, ClassNotFoundException {
        queryOrderDao=new QueryOrderDao();
        con.prepareStatement("DELETE FROM myorder").executeUpdate();
        con.prepareStatement("DELETE FROM rider").executeUpdate();
        con.prepareStatement("DELETE FROM customer").executeUpdate();
        con.prepareStatement("DELETE FROM user").executeUpdate();
        con.prepareStatement("DELETE FROM address").executeUpdate();


        Database.closeConnection(con);
    }



    


    @Test
    void testTakeAndDeliver() throws SQLException, ClassNotFoundException {

        //l'ordine è ordinato ma non ancora presso in carica quindi il rider non può digitarlo per fare la consegna
        Exception exception = assertThrows(Exception.class, () ->  stateOrderController.takeAndDeliver(1,1));
        Assertions.assertEquals("You can not deliver this order yet", exception.getMessage());


        Ready ready=new Ready();
        queryOrderDao.changeState(1,ready);
        stateOrderController.takeAndDeliver(1,1);
        Order o=queryOrderDao.get(1);
        Assertions.assertEquals("delivering",o.getState().getState());


    }

    @Test
    void testOrderDelivered() throws SQLException, ClassNotFoundException {

        Exception exception = assertThrows(Exception.class, () ->stateOrderController.orderDelivered(1));
        Assertions.assertEquals("You have to take the other first", exception.getMessage());

        Delivering delivering=new Delivering();
        queryOrderDao.changeState(1,delivering);
        stateOrderController.orderDelivered(1);
        Order o=queryOrderDao.get(1);
        Assertions.assertEquals("delivered",o.getState().getState());
    }


}
