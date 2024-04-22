package com.company.domainModel.state;

public abstract class State {
    protected String state;

    public abstract boolean canUpdate();
    public abstract boolean canDelete();

    public String getState(){
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
