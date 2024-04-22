package com.company.daoModel;

import com.company.domainModel.Customer;
import com.company.domainModel.Item;
import com.company.domainModel.Order;
import com.company.domainModel.payment.PaymentStrategy;
import com.company.domainModel.state.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class QueryOrderDao implements OrderDao{

    //funzione che mi serve per inserire un nuovo ordine inserendo l'id del ordine la data fatta e il cliente che ha richiesto l'ordine
    @Override
    public void insert(Order newInsert) throws ClassNotFoundException, SQLException {



        Connection con= Database.getConnection();
        PreparedStatement ps=con.prepareStatement("INSERT  INTO myOrder(idOrder,stateOrder,dateOrder,customerOrder,noteOrder,payment,topay,timeToDeliver) VALUES(?,?,?,?,?,'No payed',?,?)");

        java.util.Date utilDate = new java.util.Date();
        Timestamp sqlDate = new Timestamp(utilDate.getTime());
        int idCustomer=newInsert.getIdC();
        ps.setInt(1,newInsert.getId());
        ps.setString(2,"saved");
        ps.setTimestamp(3,sqlDate);
        ps.setInt(4,idCustomer);
        ps.setString(5,newInsert.getNote());
        Iterator<Item> item=newInsert.getItems().iterator();

        float pay=totToPay(item);

        QueryCustomerDao queryCustomerDao=new QueryCustomerDao();
        Customer c=queryCustomerDao.get(idCustomer);

        if(c.isDiscount()==true)
        {
            pay=(pay*20)/100;
            queryCustomerDao.setDiscount(idCustomer,false);
        }
        else
            getDiscountNextOrder(idCustomer);

        ps.setFloat(6,pay);

        ps.setTime(7, Time.valueOf(java.time.LocalTime.now()));
        ps.executeUpdate();

        Saved s=new Saved();
        newInsert.setState(s);
        insertOrderItem(newInsert.getId(),newInsert.getItems());
        queryCustomerDao.setNumberOrder(idCustomer);

}


    public void getDiscountNextOrder(int idCustomer) throws SQLException, ClassNotFoundException {

        QueryCustomerDao queryCustomerDao=new QueryCustomerDao();
        Customer c=queryCustomerDao.get(idCustomer);
        if(c.getTotOrder()%10==0 && c.getTotOrder()!=0)
        {
            queryCustomerDao.setDiscount(idCustomer,true);
        }




    }

    public float totToPay(Iterator<Item> item)
    {
        float toPay=0;
        while(item.hasNext()){
            Item i = item.next();
            toPay+=i.getPrice()*i.getQuantity();
        }
        return toPay;

    }

    public void changePriceToPay(int idOrder,List<Item> items) throws SQLException, ClassNotFoundException
    {
        float pay=totToPay((Iterator<Item>) items);
        Connection con= Database.getConnection();
        PreparedStatement ps=con.prepareStatement("UPDATE myorder SET topay=? where idOrder=?");
        ps.setFloat(1,get(idOrder).getTopay()+pay);
        ps.setInt(2,idOrder);
        ps.executeUpdate();
        ps.close();


    }


    public void insertOrderItem(int idOrder,List<Item> items) throws SQLException, ClassNotFoundException {

        Connection con= Database.getConnection();
        PreparedStatement ps=con.prepareStatement("INSERT  INTO orderitem(order_id,item_id,quantity) VALUES(?,?,?)");
        Iterator<Item> it=items.iterator();
        while(it.hasNext()){
            Item i = it.next();
            ps.setInt(1,idOrder);
            ps.setInt(2,i.getId());
            ps.setInt(3,i.getQuantity());
            ps.addBatch();
        }
        ps.executeBatch();
        ps.close();
        Database.closeConnection(con);
    }

    @Override
    public void deleteOrderItem(int id,int item_id) throws SQLException, ClassNotFoundException {

        Connection con=Database.getConnection();

        PreparedStatement delete=con.prepareStatement("delete from orderitem where order_id=? and item_id=?");
        delete.setInt(1,id);
        delete.setInt(2,item_id);
        delete.executeUpdate();
        delete.close();


    }

    @Override
    public void delete(int id) throws SQLException, ClassNotFoundException {


        Connection con= Database.getConnection();
        PreparedStatement ps=con.prepareStatement("delete from myorder where idOrder=?");
        ps.setInt(1,id);

       ps.executeUpdate();

        ps.close();
        con.close();



    }

    @Override
    public void change(int id, int idItem,int quantity) throws SQLException, ClassNotFoundException {

        Connection con=Database.getConnection();

        PreparedStatement selectQuantity=con.prepareStatement("select quantity from orderitem where order_id=? and item_id=?");
        selectQuantity.setInt(1,id);
        selectQuantity.setInt(2,idItem);
        ResultSet r=selectQuantity.executeQuery();
        int q=0;
        if(r.next())
        {
            q=r.getInt("quantity");
        }
        selectQuantity.close();
        r.close();
        if(q>quantity)
        {
            q=-1*(q-quantity);
        }
        else
        {
            q=(quantity-q);
        }


        PreparedStatement update=con.prepareStatement("update orderitem set quantity=? where order_id=? and item_id=?");
        update.setInt(1,quantity);
        update.setInt(2,id);
        update.setInt(3,idItem);
        update.executeUpdate();
        update.close();


        PreparedStatement pay=con.prepareStatement("select price from item where idItem=?");
        pay.setInt(1,idItem);
        ResultSet rs=pay.executeQuery();
        float price=0;
        if(rs.next())
        {
            price=rs.getFloat("price");
        }
        PreparedStatement newToPay=con.prepareStatement("update myorder set topay=? where idOrder=?");
        newToPay.setFloat(1,get(id).getTopay()+(price*q));
        newToPay.setInt(2,id);
        newToPay.executeUpdate();
        newToPay.close();
        Database.closeConnection(con);

    }

    @Override
    public Order get(int id) throws SQLException, ClassNotFoundException {
        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("select * from myorder where idOrder=?");
        ps.setInt(1,id);
        ResultSet rs=ps.executeQuery();
        Order o=null;
        if (rs.next())
        {
            o=new Order();
            o.setId(rs.getInt("idOrder"));

            o.setDeparture(rs.getTime("departure"));
            o.setDelivered((rs.getDate("delivered")));
            o.setDateOrder(rs.getDate("dateOrder"));
            o.setIdC(rs.getInt("customerOrder"));
            o.setIdR(rs.getInt("orderRider"));
            o.setTimeToDeliver(rs.getTime("timeToDeliver"));
            o.setPaymentStrategy(rs.getString("payment"));
            o.setNote(rs.getString("noteOrder"));
            this.setState(rs,o); // in modo da poter inserire lo state in order in base allo stato che è
            o.setItems(getItems(id,con));
            o.setTopay(rs.getFloat("topay"));

        }

        ps.close();
        rs.close();
        Database.closeConnection(con);
        return o;

    }

    @Override
    public List<Item> getItems(int idOrder,Connection con) throws SQLException, ClassNotFoundException {
        PreparedStatement ps=con.prepareStatement("select * from orderitem JOIN item ON  orderitem.item_id=item.IdItem  where order_id=?");
        ps.setInt(1,idOrder);
        ResultSet rs=ps.executeQuery();


        List<Item> items= new ArrayList<Item>();
        Item i=null;
        while (rs.next())
        {
            i=new Item();
            i.setId(rs.getInt("idItem"));
            i.setName(rs.getString("name"));
            i.setPrice(rs.getInt("price"));
            i.setQuantity( rs.getInt("quantity"));
            items.add(i);
        }


        return items;
    }


    public void setState(ResultSet rs,Order o) throws SQLException {
        if (Objects.equals(rs.getString("stateOrder"), "ordered")){
            Ordered ordered = new Ordered();
            o.setState(ordered);
        }
        else if(Objects.equals(rs.getString("stateOrder"),"delivering"))
        {
            Delivering delivering=new Delivering();
            o.setState(delivering);
        }
        else if(Objects.equals(rs.getString("stateOrder"),"delivered"))
        {
            Delivered delivering=new Delivered();
            o.setState(delivering);
        }
        else if(Objects.equals(rs.getString("stateOrder"),"ready")){
            Ready ready=new Ready();
            o.setState(ready);

        }
        else
        {
            Saved saved=new Saved();
            o.setState(saved);
        }



    }

    @Override
    public void updateRiderDeparture(int idRider,int idOrder ) throws SQLException, ClassNotFoundException {

        int id=idOrder;

        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("UPDATE myorder set orderRider=?,departure=? where idOrder=?");
        ps.setInt(1,idRider);
        java.util.Date date = new Date();
        java.sql.Time time = new java.sql.Time(date.getTime());
        ps.setTime(2,time);
        ps.setInt(3,id);
        ps.executeUpdate();
        ps.close();
        Database.closeConnection(con);


    }

    @Override
    public void updateDeliver(int id) throws SQLException, ClassNotFoundException {
        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("update myorder set delivered=? where idOrder=?");
        java.util.Date date = new Date();
        java.sql.Time time = new java.sql.Time(date.getTime());
        ps.setTime(1,time);
        ps.setInt(2,id);
        ps.executeUpdate();
        ps.close();
        Database.closeConnection(con);
    }

    @Override
    public void changeState(int idOrder, State newState) throws SQLException, ClassNotFoundException {

        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE myorder SET stateOrder = ? WHERE idOrder = ?");
        ps.setString(1, newState.getState());
        ps.setInt(2, idOrder);

        ps.executeUpdate();
        ps.close();
        Database.closeConnection(con);
    }

    @Override
    public void updatePayment(int idOrder, PaymentStrategy paymentStrategy) throws SQLException, ClassNotFoundException {
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE myorder SET payment = ? WHERE idOrder = ?");
        ps.setString(1, paymentStrategy.getPayState());
        ps.setInt(2, idOrder);
        ps.executeUpdate();

        ps.close();
        Database.closeConnection(con);
    }


}
