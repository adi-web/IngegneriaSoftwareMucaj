package com.company.domainModel.state;

public abstract class State {
    protected String state;

    public String getState(){
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
