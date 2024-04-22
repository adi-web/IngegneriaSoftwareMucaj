package com.company.domainModel.state;

public class Delivered extends State{

    public Delivered() {
        this.state="delivered";
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public boolean canDelete() {
        System.out.println("L'ordine non può essere eliminato");
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
