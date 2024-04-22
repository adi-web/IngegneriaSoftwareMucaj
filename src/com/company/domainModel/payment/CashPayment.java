package com.company.domainModel.payment;

public class CashPayment implements PaymentStrategy{

    private String s="No payed";

    @Override
    public String getPayState() {
        return s;
    }


    @Override
    public void pay(float amount) {
        s="Pay Cash";
    }
}
