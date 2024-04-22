package com.company.test.logicModel;

import com.company.daoModel.Database;
import com.company.daoModel.OrderDao;
import com.company.daoModel.QueryOrderDao;
import com.company.daoModel.QueryRiderDao;
import com.company.domainModel.Item;
import com.company.domainModel.Order;
import com.company.logicModel.OrderController;
import com.company.logicModel.StateOrderController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderControllerTest {
    private Connection con;
    private QueryOrderDao queryOrderDao= new QueryOrderDao();
    private QueryRiderDao queryRiderDao;

    {
        try {
            con = Database.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    OrderController orderController;

    @BeforeEach
    void init() throws SQLException, ClassNotFoundException {

        queryOrderDao=new QueryOrderDao();
        orderController=new OrderController(queryOrderDao);
        con.prepareStatement("DELETE FROM myorder").executeUpdate();

        java.util.Date utilDate = new java.util.Date();
        Timestamp sqlDate = new Timestamp(utilDate.getTime());


        con.prepareStatement("DELETE FROM customer").executeUpdate();
        con.prepareStatement("DELETE FROM user").executeUpdate();
        con.prepareStatement("DELETE FROM address").executeUpdate();
        con.prepareStatement("DELETE FROM Item").executeUpdate();


        con.prepareStatement( "INSERT INTO item(idItem,name,price)VALUES(1,'margherita',6)").executeUpdate();
        con.prepareStatement( "INSERT INTO item(idItem,name,price)VALUES(2,'salamino',7)").executeUpdate();
        con.prepareStatement( "INSERT INTO item(idItem,name,price)VALUES(3,'4 stagioni',8)").executeUpdate();
        con.prepareStatement( "INSERT INTO item(idItem,name,price)VALUES(4,'patate',6)").executeUpdate();
        con.prepareStatement( "INSERT INTO item(idItem,name,price)VALUES(5,'funghi',7)").executeUpdate();


        con.prepareStatement("INSERT INTO address(idAddress,nameAddress,numberAddress,city)VALUES (1,'Via solliccinao ',18,50018)").executeUpdate();
        con.prepareStatement("INSERT INTO address(idAddress,nameAddress,numberAddress,city)VALUES (2,'Via baccio da montelupo ',22,50018)").executeUpdate();

        con.prepareStatement("INSERT INTO user (idUser,name,surname,phone,address,role) VALUES (1,'andrea','petrucci','345680239',1,1)").executeUpdate();
        con.prepareStatement("INSERT INTO user (idUser,name,surname,phone,address,role) VALUES (2,'pietro','bonechi','3457890378',2,1)").executeUpdate();

        con.prepareStatement("INSERT INTO customer (idCustomer,numberOrder,user,discount) VALUES (9,5,1,false )").executeUpdate();
        con.prepareStatement("INSERT INTO customer (idCustomer,numberOrder,user,discount) VALUES (10,3,2,false)").executeUpdate();


        con.prepareStatement("INSERT INTO myorder ( idOrder,stateOrder,departure,delivered,timeTodeliver,dateOrder,orderRider,customerOrder,noteOrder,payment,topay) VALUES (1,'saved',null,null,current_time,current_date,null,9,'suonare forte','paymentcard',40)").executeUpdate();



        con.prepareStatement("INSERT INTO orderitem ( order_id,item_id,quantity) VALUES (1,1,2)").executeUpdate();
        con.prepareStatement("INSERT INTO orderitem ( order_id,item_id,quantity) VALUES (1,2,4)").executeUpdate();

    }

    @AfterEach
    void close() throws SQLException, ClassNotFoundException {
        con=Database.getConnection();
        queryOrderDao=new QueryOrderDao();
        //con.prepareStatement("DELETE FROM orderitem").executeUpdate();
        con.prepareStatement("DELETE FROM myorder").executeUpdate();

        con.prepareStatement("DELETE FROM customer").executeUpdate();
        con.prepareStatement("DELETE FROM user").executeUpdate();
        con.prepareStatement("DELETE FROM address").executeUpdate();
        con.prepareStatement("DELETE FROM Item").executeUpdate();

        Database.closeConnection(con);
    }


    @Test
    void testInsertItem() throws SQLException, ClassNotFoundException {

        Item i1=new Item(4,1,"patate",6);
        Item i2=new Item(5,2,"funghi",7);
        List<Item> items= new ArrayList<>();
        items.add(i1);
        items.add(i2);

        orderController.insertItem(1,items);

        Order o=queryOrderDao.get(1);
        Assertions.assertEquals(4,o.getItems().size());

        StateOrderController stateOrderController= new StateOrderController(orderController,queryOrderDao);
        stateOrderController.placeOrder(1);

        Exception exception = assertThrows(Exception.class, () ->  orderController.insertItem(1,items));
        Assertions.assertEquals("you can not update this order", exception.getMessage());





    }

    @Test
    void testDeleteItem() throws SQLException, ClassNotFoundException {

        orderController.deleteItem(1,1);
        Order o=queryOrderDao.get(1);
        Assertions.assertEquals(1,o.getItems().size());

        StateOrderController stateOrderController= new StateOrderController(orderController,queryOrderDao);
        stateOrderController.placeOrder(1);
        Exception exception = assertThrows(Exception.class, () ->orderController.deleteItem(1,2) );
        Assertions.assertEquals("you can not delete this order", exception.getMessage());


    }
    @Test
    void testUpdateItem() throws SQLException, ClassNotFoundException {
        orderController.updateItem(1,1,5);
        Order o=queryOrderDao.get(1);
        Assertions.assertEquals(5,o.getItems().get(0).getQuantity());
    }

}
