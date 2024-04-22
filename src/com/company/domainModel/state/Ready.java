package com.company.domainModel.state;

public class Ready extends State {
    public Ready() {
        this.state="ready";
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public boolean canDelete() {
        System.out.println("L'ordine non può essere cancellato perchè è stato già preparato");
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
