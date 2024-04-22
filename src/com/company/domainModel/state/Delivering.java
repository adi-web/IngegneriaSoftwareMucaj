package com.company.domainModel.state;

import com.company.daoModel.OrderDao;

import java.sql.SQLException;

public class Delivering extends State{

    private OrderDao orderDao;

    public Delivering() {
        this.state="delivering";
    }


    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public boolean canDelete() {
        System.out.println("L'ordine è in consegna non può essere eliminato");
        return false;
    }


    @Override
    public String getState() {
        return super.getState();
    }

    @Override
    public void setState(String state) {
        super.setState(state);
    }
}
