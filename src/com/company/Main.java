package com.company;

import com.company.daoModel.*;
import com.company.domainModel.*;
import com.company.domainModel.payment.CashPayment;
import com.company.domainModel.payment.CreditCardPayment;
import com.company.domainModel.payment.PaymentStrategy;
import com.company.logicModel.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {




        AddressDao queryAddressDao=new QueryAddressDao();
        CustomerDao queryCustomerDao=new QueryCustomerDao();
        RiderDao riderDao=new QueryRiderDao();
        OrderDao orderDao=new QueryOrderDao();




        AddressController controllerAddress=new AddressController(queryAddressDao);
        Address address= new Address(1," via solliccinao",18,50018);
        Address address2= new Address(2,"via baccio montelupo",22,50018);
        controllerAddress.addAddress(address);
        controllerAddress.addAddress(address2);







        CustomerController customerController=new CustomerController(queryCustomerDao);
        customerController.addCustomer(1,"Andrea","Petrucci","3456803744",1);
        customerController.addCustomer(2,"Pietro","Bonechi","3498628944",2);


        //System.out.println(customerController.get(8).toString());





       Rider rider=new Rider(1,"admir","mucaj","3456970499","da3kw",2 );

       RiderController riderController=new RiderController( riderDao);
       riderController.insertRider(rider);


        Item i1=new Item(1,1,"margherita",6);
        Item i2=new Item(2,2,"salamino",7);
        Item i3=new Item(3,1,"4 stagioni",8);


        QueryItem queryItem=new QueryItem();
        queryItem.insert(i1);
        queryItem.insert(i2);
        queryItem.insert(i3);


       List<Item> items= new ArrayList<>();
        items.add(i1);
        items.add(i2);
        items.add(i3);


       Order order=new Order(1,items);
       order.setIdC(1);


        OrderController orderController=new OrderController(orderDao);
        orderController.addOrder(order);
        //System.out.println(order.getState());

        //Create a payment strategy (Credit Card)
       PaymentStrategy paymentStrategy = new CreditCardPayment("1234567890123456", "12/25", "123");
       orderController.pay(1,paymentStrategy);

       StateOrderController stateOrderController= new StateOrderController(orderController,orderDao);

       stateOrderController.placeOrder(1);
       stateOrderController.makeReady(1);
       stateOrderController.takeAndDeliver(1,1);
       stateOrderController.orderDelivered(1);


       Order orderInfo=orderController.getOrderById(1);
        Customer customerInfo=customerController.get(orderInfo.getIdC());
        System.out.println("Info ordine numero "+ orderInfo.getId() +" di "+ customerInfo.getName()+" "+ customerInfo.getSurname());
        System.out.println(orderInfo.toString().toString());
        System.out.println("Info del Rider che fa la consegna ");
        Rider riderInfo=riderController.get(orderInfo.getIdR());
        System.out.println(riderInfo.toString());











    }
}
