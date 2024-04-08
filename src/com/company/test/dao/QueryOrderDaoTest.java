package com.company.test.dao;

import com.company.daoModel.Database;
import com.company.daoModel.QueryOrderDao;
import com.company.domainModel.Item;
import com.company.domainModel.Order;
import com.company.domainModel.payment.CashPayment;
import com.company.domainModel.payment.PaymentStrategy;
import com.company.domainModel.state.Delivered;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


class QueryOrderDaoTest {

     private QueryOrderDao queryOrderDao;
    private Connection con;
     // controllo insert e insertOrderItem
    @BeforeEach
    void conDatabse() throws SQLException, ClassNotFoundException {
        con=Database.getConnection();
        queryOrderDao=new QueryOrderDao();
        //con.prepareStatement("DELETE FROM orderitem").executeUpdate();
        con.prepareStatement("DELETE FROM myorder").executeUpdate();

        java.util.Date utilDate = new java.util.Date();
        Timestamp sqlDate = new Timestamp(utilDate.getTime());


        con.prepareStatement("DELETE FROM customer").executeUpdate();
        con.prepareStatement("DELETE FROM user").executeUpdate();


        con.prepareStatement("INSERT INTO user (idUser,name,surname,phone,address,role) VALUES (1,'andrea','petrucci','345680239',1,1)").executeUpdate();
        con.prepareStatement("INSERT INTO user (idUser,name,surname,phone,address,role) VALUES (2,'pietro','bonechi','3457890378',2,1)").executeUpdate();

        con.prepareStatement("INSERT INTO customer (idCustomer,numberOrder,user,discount) VALUES (9,5,1,false )").executeUpdate();
        con.prepareStatement("INSERT INTO customer (idCustomer,numberOrder,user,discount) VALUES (10,3,2,false)").executeUpdate();

        con.prepareStatement("INSERT INTO myorder ( idOrder,stateOrder,departure,delivered,timeTodeliver,dateOrder,orderRider,customerOrder,noteOrder,payment) VALUES (1,'preparation',null,null,null,null,null,9,null,'paymentcard')").executeUpdate();



        con.prepareStatement("INSERT INTO orderitem ( order_id,item_id,quantity) VALUES (1,1,10)").executeUpdate();
        con.prepareStatement("INSERT INTO orderitem ( order_id,item_id,quantity) VALUES (1,2,3)").executeUpdate();



    }
    @AfterEach
    void close() throws SQLException, ClassNotFoundException {

        con=Database.getConnection();
        queryOrderDao=new QueryOrderDao();
        //con.prepareStatement("DELETE FROM orderitem").executeUpdate();
        con.prepareStatement("DELETE FROM myorder").executeUpdate();

        con.prepareStatement("DELETE FROM customer").executeUpdate();
        con.prepareStatement("DELETE FROM user").executeUpdate();

        Database.closeConnection(con);
    }

    //Controllo get e getItems
    @Test
    void testgetOrderById() throws SQLException, ClassNotFoundException {
        Order order= queryOrderDao.get(1);

        Assertions.assertNotNull(order);
        Assertions.assertEquals("paymentcard",order.getPaymentStrategy());
        Assertions.assertEquals("ordered",order.getState().getState());
        List<Item> items=order.getItems();
        Assertions.assertEquals("margherita", items.get(0).getName());

    }

    //testo il cambio di stato e il cambio di pagamento
    @Test
    void testChangestateAndPayment() throws SQLException, ClassNotFoundException {
        Delivered delivered=new Delivered();

        queryOrderDao.changeState(1,delivered);
        PaymentStrategy paymentStrategy=new CashPayment();
        queryOrderDao.updatePayment(1,paymentStrategy);

        Order order= queryOrderDao.get(1);
        Assertions.assertEquals("delivered",order.getState().getState());
        Assertions.assertEquals("No payed",order.getPaymentStrategy());
    }



    //test per l'inserimento provando che funziona anche il setdiscount
    @Test
    void testInsertOrder() throws SQLException, ClassNotFoundException {
        Item i1=new Item(1,1,"margherita",6);
        Item i2=new Item(2,2,"salamino",7);
        Item i3=new Item(3,1,"4 stagioni",8);
        List<Item> items= new ArrayList<>();
        items.add(i1);
        items.add(i2);
        items.add(i3);
        Order order=new Order(2,items);
        order.setIdC(9);
        queryOrderDao.insert(order);

        order= queryOrderDao.get(2);
        Assertions.assertNotNull(order);
        Assertions.assertEquals("ordered",order.getState().getState());
        DecimalFormat dec = new DecimalFormat("#0.0");
        Assertions.assertEquals("28,0",dec.format(order.getTopay()));
    }

    // test per eliminare in cascade e eliminare un item
    @Test
    void testDeleteOrder() throws SQLException, ClassNotFoundException {

            queryOrderDao.deleteOrderItem(1,2);
            Order order=queryOrderDao.get(1);
            Assertions.assertEquals(1,order.getItems().size());
            queryOrderDao.delete(1);
            order=queryOrderDao.get(1);
            Assertions.assertNull(order);

    }
}
