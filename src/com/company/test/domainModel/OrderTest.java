package com.company.test.domainModel;
import com.company.domainModel.Item;
import com.company.domainModel.Order;
import com.company.domainModel.state.Ordered;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class OrderTest {
    private Order order;


    @BeforeEach
    void setUp()
    {
        Item i1=new Item(1,1,"margherita",6);
        Item i2=new Item(2,2,"salamino",7);
        Item i3=new Item(3,1,"4 stagioni",8);
        List<Item> items= new ArrayList<>();
        items.add(i1);
        items.add(i2);
        items.add(i3);
        java.util.Date utilDate = new java.util.Date();
        Timestamp sqlDate = new Timestamp(utilDate.getTime());
        order= new Order(1,1,1, sqlDate,sqlDate,sqlDate,items,"niente");
    }


    @Test
    void testGetters(){
        Assertions.assertEquals(1,order.getIdC());
        Assertions.assertEquals(1,order.getId());
        Assertions.assertEquals(1,order.getIdR());
        Assertions.assertNotNull(order.getDateOrder());
        Assertions.assertNotNull(order.getDelivered());
        Assertions.assertNotNull(order.getDateOrder());
        Assertions.assertNotNull(order.getItems());
        Assertions.assertEquals("niente",order.getNote());
    }

    @Test
    void testSetState(){
        Ordered ordered=new Ordered();
        order.setState(ordered);
        Assertions.assertEquals("ordered",order.getState().getState());
    }
}
