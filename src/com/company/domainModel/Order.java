package com.company.domainModel;

import com.company.domainModel.state.Ordered;
import com.company.domainModel.state.State;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {


    private int idC;
    private int id;
    private int idR;
    private State state;
    private Date departure;
    private Date delivered;
    private Date dateOrder;
    private List<Item> items;
    private String paymentStrategy;
    private float topay;

    public float getTopay() {
        return topay;
    }

    public void setTopay(float topay) {
        this.topay = topay;
    }

    public String getPaymentStrategy() {
        return paymentStrategy;
    }

    public void setPaymentStrategy(String paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public int getIdC() {
        return idC;
    }

    public int getIdR() {
        return idR;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    public Order(int id, List<Item> items) {
        this.id=id;
        this.items = items;
    }

    public Order(int idC, int id, int idR, Date departure, Date delivered, Date dateOrder, List<Item> items, String note) {
        this.idC = idC;
        this.id = id;
        this.idR = idR;
        this.departure = departure;
        this.delivered = delivered;
        this.dateOrder = dateOrder;
        this.items = items;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private String note;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Order()
    {
        items=new ArrayList<>();
        this.state=new Ordered();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public Date getDelivered() {
        return delivered;
    }

    public void setDelivered(Date delivered) {
        this.delivered = delivered;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        dateOrder = dateOrder;
    }
}
