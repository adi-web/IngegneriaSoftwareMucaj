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
        PreparedStatement ps=con.prepareStatement("INSERT  INTO myOrder(idOrder,stateOrder,dateOrder,customerOrder,topay) VALUES(?,?,?,?,?)");
        java.util.Date utilDate = new java.util.Date();
        Timestamp sqlDate = new Timestamp(utilDate.getTime());
        int idCustomer=newInsert.getIdC();
        ps.setInt(1,newInsert.getId());
        ps.setString(2,"preparation");
        ps.setTimestamp(3,sqlDate);
        ps.setInt(4,idCustomer);
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

        ps.setFloat(5,pay);
        ps.executeUpdate();

        Ordered p=new Ordered();
        newInsert.setState(p);
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

    
//questa funzione mi inseririsce sulla tabella orderitem in base al idorder e id item e viene chiamata da insert
    public void insertOrderItem(int idOrder,List<Item> items) throws SQLException, ClassNotFoundException {
        Connection con= Database.getConnection();
        PreparedStatement ps=con.prepareStatement("INSERT  INTO orderitem(order_id,item_id,quantity) VALUES(?,?,?)");
        Iterator<Item> it=items.iterator();
        while(it.hasNext()){
            Item i = it.next();
            ps.setInt(1,idOrder);// qua si inserisce id del ordine che stiamo inserendo item
            ps.setInt(2,i.getId());
            ps.setInt(3,i.getQuantity());
            ps.addBatch();
        }
        ps.executeBatch();
        ps.close();
        Database.closeConnection(con);
    }

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
        PreparedStatement update=con.prepareStatement("update orderitem set quantity=? where orderid=? and item_id=?");
        update.setInt(1,quantity);
        update.setInt(2,id);
        update.setInt(3,idItem);

        update.executeUpdate();
        update.close();
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
        if (Objects.equals(rs.getString("stateOrder"), "ordinato")){
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
            Ordered ordered=new Ordered();
            o.setState(ordered);
        }



    }

/*
    public void insertCustomer(Customer customer,int id) throws SQLException, ClassNotFoundException //da trovare il cliente e inserirlo nel ordine
    {
        QueryCustomerDao queryCustomerDao=new QueryCustomerDao();

        Connection con= Database.getConnection();
        PreparedStatement ps=con.prepareStatement("INSERT INTO myorder(customerOrder)VALUES(?) ");
        ps.setInt(1,customer.getId());
        ps.executeUpdate();
        ps.close();

        Database.closeConnection(con);
    }*/
/*
    @Override
    public int idOrdine(int idCustomer) throws SQLException, ClassNotFoundException {
        Connection con=Database.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT idOrdine from myorder WHERE customerOrder=?");
        ps.setInt(1,idCustomer);
        ResultSet a=ps.executeQuery();
        int id=0;
        while (a.next())
        {
            id=a.getInt("idOrdine");
        }

        ps.close();
        Database.closeConnection(con);

        return  id;

    }*/

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
