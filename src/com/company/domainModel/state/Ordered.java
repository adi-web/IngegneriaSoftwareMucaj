package com.company.domainModel.state;

public class Ordered extends State {

    public Ordered() {
        this.state="ordered";
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public boolean canDelete() {
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
